# Toy Language Interpreter

This project is an interpreter for a custom "toy" language designed for educational purposes, aimed at demonstrating fundamental concepts in language interpretation and execution. It includes a stack-based execution model, a symbol table for variable management, and an output stream to capture printed results.

## Table of Contents

- [Language Overview](#language-overview)
- [Key Language Constructs](#key-language-constructs)
    - [Statements](#statements)
    - [Types](#types)
    - [Values](#values)
    - [Expressions](#expressions)
- [Examples](#examples)
- [Interpreter Architecture](#interpreter-architecture)
    - [Execution Stack (ExeStack)](#execution-stack-exestack)
    - [Symbol Table (SymTable)](#symbol-table-symtable)
    - [Output (Out)](#output-out)
- [Evaluation Rules](#evaluation-rules)
    - [Statement Evaluation](#statement-evaluation)
    - [Expression Evaluation](#expression-evaluation)
- [Error Handling](#error-handling)
- [Setup and Usage](#setup-and-usage)

---

## Language Overview

This interpreter executes programs in the Toy Language by parsing and evaluating statements sequentially. Programs are built by combining statements, expressions, and variables to perform operations. The language supports essential data types (int, bool, and string), arithmetic operations, logical operations, and a structured flow control.

### Key Language Constructs

#### Statements

A program (`Prg`) is defined by a single statement (`Stmt`). Each statement may be:
- **Compound Statement**: `Stmt1; Stmt2`
- **Variable Declaration**: `Type Id`
- **Assignment Statement**: `Id = Exp`
- **Print Statement**: `Print(Exp)`
- **Conditional Statement**: `If Exp Then Stmt1 Else Stmt2`
- **No Operation (NOP)**: `nop`
- **File Operations**
  - openRFile: Opens a file for reading.
  - readFile: Opens a file for reading.
  - closeRFile: Opens a file for reading.

#### Types

The language supports the following basic types:
- `int` as `IntType`
- `bool` as `BoolType`
- `String` as `StringType`

#### Values

Values in this language can be:
- **Integer (IntValue)**: a numeric constant
- **Boolean (BoolValue)**: `True` or `False`
- **String (StringValue)**: a string value.


#### Expressions

Expressions (`Exp`) include:
- **Value**: a constant, such as `2` or `True`
- **Variable (Id)**: a named variable
- **Arithmetic Expressions**: e.g., `Exp1 + Exp2`, `Exp1 - Exp2`
- **Logical Expressions**: e.g., `Exp1 and Exp2`, `Exp1 or Exp2`
- **Relational Expressions**: e.g., `Exp1 < Exp2`,`Exp1 == Exp2`

# Interpreter Architecture

The interpreter operates with three primary structures:

### Execution Stack (ExeStack)
The **ExeStack** holds statements awaiting execution. Initially, it contains the original program, and statements are pushed and popped as they execute.

### Symbol Table (SymTable)
**SymTable** maps variable names to their associated values. Initially empty, it stores variables declared during execution with their values and types.

### Output (Out)
**Out** captures the values generated by `Print` statements in the order of their execution.

### File Table (FileTable)
The **FileTable** tracks the opened files during the program's execution. It is represented as a dictionary mapping a StringValue (filename) to a file descriptor (BufferedReader). Files are accessed by their string identifiers, and the file descriptors are used for reading data.



Each program execution initializes its own unique `PrgState` containing these structures.

## Evaluation Rules

### Statement Evaluation
The interpreter processes statements according to predefined rules. Key rules include:

- **Compound Statement**: Splits `Stmt1; Stmt2` into `Stmt1` and `Stmt2` on the `ExeStack`.
- **Assignment Statement**: Evaluates `Exp`, verifies `Id`’s existence and type, then updates `SymTable`.
- **Variable Declaration**: Adds `Id` to `SymTable` with a default value (0 for `int`, `false` for `bool`).
- **Print Statement**: Evaluates `Exp` and appends the result to `Out`.
- **Conditional Statement**: Evaluates `Exp`; if true, executes `Stmt1`, else executes `Stmt2`.
- **NOP Statement**: Simply removes `NOP` from the stack, with no other effect.
- **Open Read Statement:** Opens a file specified by the evaluated expression `exp` and adds the
  file descriptor to the **FileTable.**
- **Read File Statement:** Reads an integer from the file associated with `exp` and stores it in the variable ``var_name`` in the **SymTable**.

- **Close Read Statement:** Closes the file specified by the evaluated string `exp` and removes the entry from the **FileTable**.
### Expression Evaluation
Expressions are recursively evaluated, with type checks for:

- **Arithmetic Operations**: Ensures both operands are `int`.
- **Logical Operations**: Ensures both operands are `bool`.
- **Relational Operations**: Evaluates relational expressions and ensures both operands are of compatible types.

### Error Handling
The interpreter captures errors such as:

- **Type Mismatch**: Between variable declarations and expressions.
- **Undeclared Variables**: If a variable `Id` is used before declaration.
- **Division by Zero**: Raised when dividing an integer by zero.
- **File Not Found**: Raised when attempting to open a file that doesn't exist or is not accessible.

## Setup and Usage

### Prerequisites
- Java (JDK 11+)
- Gradle (optional, if using Gradle for building)

### Build and Run
Clone the repository:

```bash
git clone https://github.com/your-repo/toy-language-interpreter.git
```

Navigate to the repository:

```bash
cd toy-language-interpreter
```
Compile and run the interpreter:
```bash
javac -d bin src/*.java
java -cp bin Main
```





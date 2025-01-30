package toyLanguageInterpreter.model;

import toyLanguageInterpreter.exceptions.*;
import toyLanguageInterpreter.model.adt.*;
import toyLanguageInterpreter.model.adt.dictionary.MyDictionary;
import toyLanguageInterpreter.model.adt.dictionary.MyIDictionary;
import toyLanguageInterpreter.model.adt.heapTable.MyIHeap;
import toyLanguageInterpreter.model.adt.latchTable.MyILatchTable;
import toyLanguageInterpreter.model.adt.list.MyIList;
import toyLanguageInterpreter.model.adt.list.MyList;
import toyLanguageInterpreter.model.adt.lockTable.MyILockTable;
import toyLanguageInterpreter.model.adt.semaphoreTable.MyISemaphoreTable;
import toyLanguageInterpreter.model.adt.stack.MyIStack;
import toyLanguageInterpreter.model.adt.stack.MyStack;
import toyLanguageInterpreter.model.adt.toySemaphoreTable.MyIToySemaphoreTable;
import toyLanguageInterpreter.model.statements.CompStmt;
import toyLanguageInterpreter.model.statements.IStmt;
import toyLanguageInterpreter.model.statements.NopStmt;
import toyLanguageInterpreter.model.types.Type;
import toyLanguageInterpreter.model.values.StringValue;
import toyLanguageInterpreter.model.values.Value;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, Value> symTable;
    private MyIList<Value> out;
    private IStmt originalProgram;
    private MyILatchTable latchTable;
    private MyIToySemaphoreTable toySemaphoreTable;
    private MyISemaphoreTable semaphoreTable;
    private MyILockTable lockTable;
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    private MyIHeap<Integer, Value> heap;
    private int id;
    private static int currentId = 1;

    /*** Constructor ***/
    public PrgState(MyIStack<IStmt> exeStack, MyIDictionary<String, Value> symTable, MyIList<Value> out,
                    IStmt originalProgram, MyILatchTable latchTable, MyIToySemaphoreTable toySemaphoreTable,
                    MyISemaphoreTable semaphoreTable, MyILockTable lockTable,
                    MyIDictionary<StringValue, BufferedReader> fileTable, MyIHeap<Integer, Value> heap) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.originalProgram = originalProgram.deepCopy();
        this.latchTable = latchTable;
        this.semaphoreTable = semaphoreTable;
        this.toySemaphoreTable = toySemaphoreTable;
        this.lockTable = lockTable;
        this.fileTable = fileTable;
        this.heap = heap;
        this.exeStack.push(originalProgram);
        setId();
    }

    /*** Thread ID Management ***/
    private synchronized void setId() {
        this.id = currentId++;
    }

    public int getId_Thread() {
        return this.id;
    }

    /*** Program Execution ***/
    public boolean isNotCompleted() {
        return !this.exeStack.isEmpty();
    }

    public PrgState oneStep() throws MyException, ADTException, StatementException, ExpressionException, InterpreterException, FileNotFoundException {
        if (exeStack.isEmpty()) throw new MyException("PrgState stack is empty.");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    /*** Utility Methods ***/
    public void resetProgramState() {
        this.out = new MyList<>();
        this.exeStack = new MyStack<>();
        this.fileTable = new MyDictionary<>();
        this.symTable = new MyDictionary<>();
        this.exeStack.push(originalProgram);
    }

    public void typeCheck() throws InterpreterException, StatementException, ADTException, ExpressionException {
        MyIDictionary<String, Type> typeEnvironment = new MyDictionary<>();
        originalProgram.typeCheck(typeEnvironment);
    }

    /*** Tree Conversion & Statement Extraction ***/
    public List<IStmt> distinctStatements() {
        MyTree<IStmt> tree = new MyTree<>();
        List<IStmt> inOrderList = new LinkedList<>();
        if (!getExeStack().toListS().isEmpty()) {
            tree.setRoot(toTree(getExeStack().toListS().get(0)));
            tree.inorderTraversal(inOrderList, tree.getRoot());
        }
        return inOrderList;
    }

    private Node<IStmt> toTree(IStmt stmt) {
        if (stmt instanceof CompStmt) {
            CompStmt compStmt = (CompStmt) stmt;
            Node<IStmt> node = new Node<>(new NopStmt());
            node.setLeft(new Node<>(compStmt.getFirst()));
            node.setRight(toTree(compStmt.getSecond()));
            return node;
        }
        return new Node<>(stmt);
    }

    /*** Getters ***/
    public MyIHeap<Integer, Value> getHeap() {
        return heap;
    }

    public MyIToySemaphoreTable getToySemaphoreTable() {
        return toySemaphoreTable;
    }

    public MyILockTable getLockTable() {
        return lockTable;
    }

    public MyISemaphoreTable getSemaphoreTable() {
        return semaphoreTable;
    }

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public MyIDictionary<String, Value> getSymTable() {
        return symTable;
    }

    public MyILatchTable getLatchTable() {
        return latchTable;
    }

    public MyIList<Value> getOut() {
        return out;
    }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    /*** Setters ***/
    public void setHeap(MyIHeap<Integer, Value> heap) {
        this.heap = heap;
    }

    public void setSymbolTable(MyIDictionary<String, Value> symTable) {
        this.symTable = symTable;
    }

    public void setLatchTable(MyILatchTable latchTable) {
        this.latchTable = latchTable;
    }

    public void setLockTable(MyILockTable lockTable) {
        this.lockTable = lockTable;
    }

    public void setSemaphoreTable(MyISemaphoreTable semaphoreTable) {
        this.semaphoreTable = semaphoreTable;
    }

    public void setToySemaphoreTable(MyIToySemaphoreTable toySemaphoreTable) {
        this.toySemaphoreTable = toySemaphoreTable;
    }

    public void setFileTable(MyIDictionary<StringValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public void setExecutionStack(MyIStack<IStmt> stack) {
        this.exeStack = stack;
    }

    public void setOutput(MyIList<Value> out) {
        this.out = out;
    }

    /*** String Representation ***/
    @Override
    public String toString() {
        return "------- Current Program State ------\n" +
               "ID = " + id + "\n" +
               "Execution Stack:\n" + exeStack + "\n" +
               "Symbol Table:\n" + symTable + "\n" +
               "Output:\n" + out + "\n" +
               "File Table:\n" + fileTable + "\n" +
               "Heap:\n" + heap + "\n" +
               "Latch Table:\n" + latchTable + "\n" +
               "Toy Semaphore Table:\n" + toySemaphoreTable + "\n" +
               "Semaphore Table:\n" + semaphoreTable + "\n" +
               "Lock Table:\n" + lockTable + "\n" +
               "Original Program:\n" + originalProgram + "\n";
    }
}

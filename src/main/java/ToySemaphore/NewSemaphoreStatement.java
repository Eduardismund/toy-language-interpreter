//package ToySemaphore;
//
//import javafx.util.Pair;
//import toyLanguageInterpreter.exceptions.*;
//import toyLanguageInterpreter.model.PrgState;
//import toyLanguageInterpreter.model.adt.MyIDictionary;
//import toyLanguageInterpreter.model.expressions.Exp;
//import toyLanguageInterpreter.model.statements.IStmt;
//import toyLanguageInterpreter.model.types.IntType;
//import toyLanguageInterpreter.model.types.Type;
//import toyLanguageInterpreter.model.values.IntValue;
//import toyLanguageInterpreter.model.values.Value;
//
//import java.util.Vector;
//
//public class NewSemaphoreStatement implements IStmt {
//    private String variableName;
//    private Exp expression1;
//    private Exp expression2;
//
//    public NewSemaphoreStatement(String variableName, Exp expression1, Exp expression2) {
//        this.variableName = variableName;
//        this.expression1 = expression1;
//        this.expression2 = expression2;
//    }
//
//    @Override
//    public PrgState execute(PrgState state) throws StatementException, MyException, InterpreterException, ADTException, ExpressionException {
//        // Evaluate the expressions
//        Value number1 = this.expression1.eval(state.getSymTable(), state.getHeap());
//        Value number2 = this.expression2.eval(state.getSymTable(), state.getHeap());
//
//        // Check if the expressions are integers
//        if (!number1.getType().equals(new IntType())) {
//            throw new StatementException("The expression " + expression1.toString() + " is not of type int!");
//        }
//        if (!number2.getType().equals(new IntType())) {
//            throw new StatementException("The expression " + expression2.toString() + " is not of type int!");
//        }
//
//        // Extract the integer values
//        int n1 = ((IntValue) number1).getVal();
//        int n2 = ((IntValue) number2).getVal();
//
//        synchronized (state.getSemaphoreTable()) {
//            // Add the new semaphore entry to the SemaphoreTable and get the new free location
//            int newFreeLocation = state.getSemaphoreTable().put(new Pair<>(n1, new Pair<>(new Vector<>(), n2)));
//
//            // Check if the variable is defined in the symbol table
//            if (!state.getSymTable().isDefined(this.variableName)) {
//                throw new StatementException("The variable " + this.variableName + " is not declared!");
//            }
//
//            // Check if the variable is of type int
//            if (!state.getSymTable().lookup(this.variableName).getType().equals(new IntType())) {
//                throw new StatementException("The variable " + this.variableName + " is not of type int!");
//            }
//
//            // Put the new free location into the symbol table
//            state.getSymTable().put(this.variableName, new IntValue(newFreeLocation));
//        }
//
//        return null;
//    }
//
//    @Override
//    public IStmt deepCopy() {
//        return new NewSemaphoreStatement(this.variableName, this.expression1.deepCopy(), this.expression2.deepCopy());
//    }
//
//    @Override
//    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnvironment) throws ADTException, ExpressionException, StatementException {
//        // Check the types of the expressions
//        if (!this.expression1.typeCheck(typeEnvironment).equals(new IntType())) {
//            throw new StatementException("The expression " + this.expression1.toString() + " is not of type int!");
//        }
//        if (!this.expression2.typeCheck(typeEnvironment).equals(new IntType())) {
//            throw new StatementException("The expression " + this.expression2.toString() + " is not of type int!");
//        }
//
//        if (!typeEnvironment.isDefined(this.variableName)) {
//            throw new StatementException("The variable " + this.variableName + " is not declared!");
//        }
//
//        if (!typeEnvironment.lookup(this.variableName).equals(new IntType())) {
//            throw new StatementException("The variable " + this.variableName + " is not of type int!");
//        }
//
//        return typeEnvironment;
//    }
//
//    @Override
//    public String toString() {
//        return "newSemaphore(" + this.variableName + ", " + this.expression1.toString() + ", " + this.expression2.toString() + ")";
//    }
//}

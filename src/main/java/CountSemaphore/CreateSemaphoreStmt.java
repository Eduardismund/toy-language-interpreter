//package CountSemaphore;
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
//public class CreateSemaphoreStmt implements IStmt {
//    private String variableName;
//    private Exp expression;
//
//    public CreateSemaphoreStmt(String variableName, Exp expression) {
//        this.variableName = variableName;
//        this.expression = expression;
//    }
//
//
//    @Override
//    public PrgState execute(PrgState state) throws StatementException, MyException, InterpreterException, ADTException, ExpressionException {
//        Value expressionValue = this.expression.eval(state.getSymTable(), state.getHeap());
//        if(!expressionValue.getType().equals(new IntType()))
//            throw new InterpreterException("The expression " + expression.toString() + " is not of type int!");
//        int expressionInt = ((IntValue) expressionValue).getVal();
//
//        synchronized (state.getSemaphoreTable()){
//            int newFreeLocation = state.getSemaphoreTable().put(new Pair<>(expressionInt, new Vector<>())); // Vector is synchronized
//
//            if (!state.getSymTable().isDefined( this.variableName))
//                throw new InterpreterException("The variable " + this.variableName + " is not declared!");
//
//            if(!state.getSymTable().lookup(this.variableName).getType().equals(new IntType()))
//                throw new InterpreterException("The variable " + this.variableName + " is not of type int!");
//
//            state.getSymTable().put(this.variableName, new IntValue(newFreeLocation));
//        }
//        return null;
//    }
//
//    @Override
//    public IStmt deepCopy() {
//        return new CreateSemaphoreStmt(this.variableName, this.expression.deepCopy());
//    }
//
//    @Override
//    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnvironment) throws ADTException, ExpressionException, StatementException {
//        if(!this.expression.typeCheck(typeEnvironment).equals(new IntType()))
//            throw new StatementException("The expression " + this.expression.toString() + " is not of type int!");
//
//        if (!typeEnvironment.isDefined( this.variableName))
//            throw new StatementException("The variable " + this.variableName + " is not declared!");
//
//        if(!typeEnvironment.lookup(this.variableName).equals(new IntType()))
//            throw new StatementException("The variable " + this.variableName + " is not of type int!");
//        return typeEnvironment;
//    }
//
//    @Override
//    public String toString() {
//        return "createSemaphore(" + this.variableName + ", " + this.expression.toString() + ")";
//    }
//}
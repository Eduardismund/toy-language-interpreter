//package CountSemaphore;
//
//import javafx.util.Pair;
//import toyLanguageInterpreter.exceptions.ADTException;
//import toyLanguageInterpreter.exceptions.StatementException;
//import toyLanguageInterpreter.model.PrgState;
//import toyLanguageInterpreter.model.adt.MyIDictionary;
//import toyLanguageInterpreter.model.statements.IStmt;
//import toyLanguageInterpreter.model.types.IntType;
//import toyLanguageInterpreter.model.types.Type;
//import toyLanguageInterpreter.model.values.IntValue;
//import toyLanguageInterpreter.model.values.Value;
//
//import java.util.List;
//import java.util.Vector;
//
//public class ReleaseStatement implements IStmt {
//    private String variableName;
//
//    public ReleaseStatement(String variableName) {
//        this.variableName = variableName;
//    }
//
//
//    @Override
//    public IStmt deepCopy() {
//        return new ReleaseStatement(this.variableName);
//    }
//
//    @Override
//    public String toString() {
//        return "releaseSemaphore(" + this.variableName + ")";
//    }
//
//    @Override
//    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnvironment) throws StatementException, ADTException {
//        if (!typeEnvironment.isDefined(this.variableName)) {
//            throw new StatementException("The variable " + this.variableName + " is not defined!");
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
//    public PrgState execute(PrgState state) throws StatementException {
//        try {
//            Value variableValue = state.getSymTable().lookup(this.variableName);
//            if (!variableValue.getType().equals(new IntType())) {
//                throw new StatementException("The variable " + this.variableName + " is not of type int!");
//            }
//
//            int semaphoreLocation = ((IntValue) variableValue).getVal();
//            if (!state.getSemaphoreTable().isDefined(semaphoreLocation)) {
//                throw new StatementException("The semaphore " + this.variableName + " is not defined!");
//            }
//
//            synchronized (state.getSemaphoreTable()) {
//                Pair<Integer, List<Integer>> semaphoreEntry = state.getSemaphoreTable().lookup(semaphoreLocation);
//                int NL = semaphoreEntry.getKey();
//                List<Integer> threads = semaphoreEntry.getValue();
//                if (threads.contains(state.getId_Thread())) {
//                    threads.remove(Integer.valueOf(state.getId_Thread()));
//                }
//            }
//            return null;
//
//        } catch (ADTException e) {
//            throw new StatementException(e.getMessage());
//        }
//
//    }
//}
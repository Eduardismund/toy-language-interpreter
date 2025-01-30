//package ToySemaphore;
//
//import javafx.util.Pair;
//import toyLanguageInterpreter.exceptions.*;
//import toyLanguageInterpreter.model.PrgState;
//import toyLanguageInterpreter.model.adt.MyIDictionary;
//import toyLanguageInterpreter.model.statements.IStmt;
//import toyLanguageInterpreter.model.types.IntType;
//import toyLanguageInterpreter.model.types.Type;
//import toyLanguageInterpreter.model.values.IntValue;
//
//import java.io.FileNotFoundException;
//import java.util.List;
//
//public class AcquireToyStatement implements IStmt
//{
//    private String variableName;
//
//    public AcquireToyStatement(String variableName) {
//        this.variableName = variableName;
//    }
//
//    @Override
//    public PrgState execute(PrgState state) throws StatementException, ADTException {
//        try {
//            // the check has already been made in the typeCheck method
//            int semaphoreLocation = ((IntValue) state.getSymTable().lookup(this.variableName)).getVal();
//
//            if (!state.getSemaphoreTable().isDefined(semaphoreLocation)) {
//                throw new StatementException("AcquireSemaphoreStatement: The semaphore " + this.variableName + " is not defined!");
//            }
//            synchronized (state.getSemaphoreTable()) {
//                final var semaphore = state.getSemaphoreTable().lookup(semaphoreLocation);
//
//                int N1 = semaphore.getKey();
//                final var threads = semaphore.getValue().getKey();
//                int NL = threads.size();
//                int N2 = semaphore.getValue().getValue();
//
//                if ((N1-N2) > NL) { // if the number of threads is smaller than the NL
//                    if (!threads.contains(state.getId_Thread())) { // if the current thread is not in the list
//                        threads.add(state.getId_Thread());
//                    }
//                } else {
//                    state.getExeStack().push(this);
//                }
//            }
//        } catch (StatementException e) {
//            throw new StatementException("AcquireSemaphoreStatement: " + e.getMessage());
//        }
//        return null;
//    }
//
//    @Override
//    public String toString() {
//        return "acquireSemaphore(" + this.variableName + ")";
//    }
//    @Override
//    public IStmt deepCopy() {
//        return new AcquireToyStatement(this.variableName);
//    }
//
//    @Override
//    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnvironment) throws StatementException, ADTException {
//        if (!typeEnvironment.isDefined(this.variableName)) {
//            throw new StatementException("AcquireSemaphoreStatement: The variable " + this.variableName + " is not defined!");
//        }
//
//        if (!typeEnvironment.lookup(this.variableName).equals(new IntType())) {
//            throw new StatementException("AcquireSemaphoreStatement: The variable " + this.variableName + " is not of type int!");
//        }
//
//        return typeEnvironment;
//    }
//}

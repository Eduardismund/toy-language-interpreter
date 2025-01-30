package toyLanguageInterpreter.model.statements.countSemaphoreStatements;

import javafx.util.Pair;
import toyLanguageInterpreter.exceptions.ADTException;
import toyLanguageInterpreter.exceptions.StatementException;
import toyLanguageInterpreter.model.PrgState;
import toyLanguageInterpreter.model.adt.dictionary.MyIDictionary;
import toyLanguageInterpreter.model.statements.IStmt;
import toyLanguageInterpreter.model.types.IntType;
import toyLanguageInterpreter.model.types.Type;
import toyLanguageInterpreter.model.values.IntValue;

import java.util.List;

public class AcquireStatement implements IStmt {
    private String variableName;

    public AcquireStatement(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public IStmt deepCopy() {
        return new AcquireStatement(this.variableName);
    }

    @Override
    public String toString() {
        return "acquireSemaphore(" + this.variableName + ")";
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnvironment) throws StatementException, ADTException {
        if (!typeEnvironment.isDefined(this.variableName)) {
            throw new StatementException("AcquireSemaphoreStatement: The variable " + this.variableName + " is not defined!");
        }

        if (!typeEnvironment.lookup(this.variableName).equals(new IntType())) {
            throw new StatementException("AcquireSemaphoreStatement: The variable " + this.variableName + " is not of type int!");
        }

        return typeEnvironment;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ADTException {
        try {
            int semaphoreLocation = ((IntValue) state.getSymTable().lookup(this.variableName)).getVal();

            if (!state.getSemaphoreTable().isDefined(semaphoreLocation)) {
                throw new StatementException("AcquireSemaphoreStatement: The semaphore " + this.variableName + " is not defined!");
            }
            synchronized (state.getSemaphoreTable()) {
                Pair<Integer, List<Integer>> semaphore = state.getSemaphoreTable().lookup(semaphoreLocation);

                int NL = semaphore.getKey();
                List<Integer> threads = semaphore.getValue();
                if (NL > threads.size()) { // if the number of threads is smaller than the NL
                    if (!threads.contains(state.getId_Thread())) { // if the current thread is not in the list
                        threads.add(state.getId_Thread());
                    }
                } else {
                    state.getExeStack().push(this);
                }
            }
        } catch (StatementException e) {
            throw new StatementException("AcquireSemaphoreStatement: " + e.getMessage());
        }
        return null;
    }
}
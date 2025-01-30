package toyLanguageInterpreter.model.statements.lockStatements;

import toyLanguageInterpreter.exceptions.ADTException;
import toyLanguageInterpreter.exceptions.InterpreterException;
import toyLanguageInterpreter.exceptions.StatementException;
import toyLanguageInterpreter.model.PrgState;
import toyLanguageInterpreter.model.adt.dictionary.MyIDictionary;
import toyLanguageInterpreter.model.statements.IStmt;
import toyLanguageInterpreter.model.types.IntType;
import toyLanguageInterpreter.model.types.Type;
import toyLanguageInterpreter.model.values.IntValue;
import toyLanguageInterpreter.model.values.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLockStatement implements IStmt {
    private final String var;
    private static final Lock lock = new ReentrantLock();

    public NewLockStatement(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ADTException {
        lock.lock();
        final var lockTable = state.getLockTable();
        final var symTable = state.getSymTable();
        int freeAddress = lockTable.getFreeValue();
        lockTable.put(freeAddress, -1);
        if (symTable.isDefined(var) && symTable.lookup(var).getType().equals(new IntType())) {
            symTable.update(var, new IntValue(freeAddress));
        }
        else
            throw new StatementException("Variable not declared!");
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ADTException {
        if (typeEnv.lookup(var).equals(new IntType()))
            return typeEnv;
        else
            throw new StatementException("Var is not of int Types!");
    }

    @Override
    public IStmt deepCopy() {
        return new NewLockStatement(var);
    }

    @Override
    public String toString() {
        return String.format("newLock(%s)", var);
    }
}

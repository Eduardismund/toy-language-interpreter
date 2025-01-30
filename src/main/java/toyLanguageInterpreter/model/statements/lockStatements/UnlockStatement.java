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

public class UnlockStatement implements IStmt {
    private final String var;
    private static final Lock lock = new ReentrantLock();

    public UnlockStatement(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ADTException {
        lock.lock();
        final var symTable = state.getSymTable();
        final var lockTable = state.getLockTable();
        if (symTable.isDefined(var)) {
            if (symTable.lookup(var).getType().equals(new IntType())) {
                IntValue fi = (IntValue) symTable.lookup(var);
                int foundIndex = fi.getVal();
                if (lockTable.containsKey(foundIndex)) {
                    if (lockTable.get(foundIndex) == state.getId_Thread())
                        lockTable.update(foundIndex, -1);
                } else {
                    throw new StatementException("Index not in the lock table!");
                }
            } else {
                throw new StatementException("Var is not of int Types!");
            }
        } else {
            throw new StatementException("Variable is not defined!");
        }
        lock.unlock();
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ADTException {
        if (typeEnv.lookup(var).equals(new IntType()))
            return typeEnv;
        else
            throw new StatementException("Var is not of Types int!");
    }

    @Override
    public IStmt deepCopy() {
        return new UnlockStatement(var);
    }

    @Override
    public String toString() {
        return String.format("unlock(%s)", var);
    }
}

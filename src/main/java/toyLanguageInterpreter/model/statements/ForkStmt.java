package toyLanguageInterpreter.model.statements;

import toyLanguageInterpreter.exceptions.ADTException;
import toyLanguageInterpreter.exceptions.ExpressionException;
import toyLanguageInterpreter.exceptions.InterpreterException;
import toyLanguageInterpreter.exceptions.StatementException;
import toyLanguageInterpreter.exceptions.MyException;
import toyLanguageInterpreter.model.PrgState;
import toyLanguageInterpreter.model.adt.dictionary.MyIDictionary;
import toyLanguageInterpreter.model.adt.stack.MyStack;
import toyLanguageInterpreter.model.types.Type;

import java.io.FileNotFoundException;

public class ForkStmt implements IStmt {
    private IStmt forkStmt;

    public ForkStmt(IStmt iStmt) {
        this.forkStmt = iStmt;
    }

    @Override
    public String toString() {
        return "fork(" + forkStmt.toString() + ")";
    }
    @Override
    public PrgState execute(PrgState state) throws MyException, MyException, StatementException, ExpressionException, InterpreterException, FileNotFoundException, ADTException {
        final var newExeStack = new MyStack<IStmt>();

        final var newSymTable = state.getSymTable().deepCopy();
        return new PrgState(newExeStack, newSymTable, state.getOut(), forkStmt, state.getLatchTable(), state.getToySemaphoreTable(), state.getSemaphoreTable(), state.getLockTable(), state.getFileTable(), state.getHeap());
    }

    @Override
    public IStmt deepCopy() {
        return new ForkStmt(forkStmt.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws ADTException, ExpressionException, StatementException {
        forkStmt.typeCheck(typeEnv.deepCopy());
        return typeEnv;
    }
}

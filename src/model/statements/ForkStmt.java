package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.InterpreterException;
import exceptions.StatementException;
import model.MyException;
import model.PrgState;
import model.adt.MyStack;

import java.io.FileNotFoundException;
import java.util.Stack;

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
        return new PrgState(newExeStack, newSymTable, state.getOut(), forkStmt, state.getFileTable(), state.getHeap());
    }

    @Override
    public IStmt deepCopy() {
        return new ForkStmt(forkStmt.deepCopy());
    }
}

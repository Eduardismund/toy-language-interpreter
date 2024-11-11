package model.statements;

import model.MyException;
import model.PrgState;
import model.adt.MyIStack;

public class CompStmt implements IStmt {

    private IStmt first;
    private IStmt second;

    public IStmt getFirst() {
        return first;
    }

    public IStmt getSecond() {
        return second;
    }

    public CompStmt(IStmt first, IStmt second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString()
    {
        return "(" + first.toString() + "; " + second.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stack = state.getExeStack();
        stack.push(second);
        stack.push(first);
        state.setExecutionStack(stack);
        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new CompStmt(first.deepCopy(), second.deepCopy());
    }
}

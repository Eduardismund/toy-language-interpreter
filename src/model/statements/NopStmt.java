package model.statements;

import model.MyException;
import model.PrgState;

public class NopStmt implements IStmt{

    @Override
    public PrgState execute(PrgState state) throws MyException, MyException {
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NopStmt();
    }

    @Override
    public String toString() {
        return "NopStmt";
    }
}

package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.InterpreterException;
import model.MyException;
import model.PrgState;
import model.expressions.Exp;
import model.values.Value;
import model.adt.MyIList;

public class PrintStmt implements IStmt {
    private Exp exp;

    public PrintStmt(Exp exp){
        this.exp = exp;
    }

    @Override
    public String toString(){
        if(exp != null) {
            return "print(" + exp.toString() + ")";
        }
        return "print()";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, ExpressionException, InterpreterException, ADTException {
        MyIList<Value> out = state.getOut();
        final var heap = state.getHeap();
        Value value = exp.eval(state.getSymTable(), heap);
        out.add(value);
        state.setOutput(out);
        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(exp.deepCopy());
    }
}

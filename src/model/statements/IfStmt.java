package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.InterpreterException;
import exceptions.StatementException;
import model.MyException;
import model.PrgState;
import model.expressions.Exp;
import model.values.BoolValue;
import model.values.Value;
import model.adt.MyIExeStack;

public class IfStmt implements IStmt {
    private IStmt thenStmt;
    private IStmt elseStmt;
    private Exp exp;

    public IfStmt(Exp exp, IStmt thenStmt, IStmt elseStmt) {
        this.exp = exp;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, StatementException, ExpressionException, InterpreterException, ADTException {
        Value v = exp.eval(state.getSymTable());
        MyIExeStack<IStmt> stack = state.getExeStack();
        if (v instanceof BoolValue) {
            if (((BoolValue) v).getVal()) {
                stack.push(thenStmt);
            } else {
                stack.push(elseStmt);
            }
        } else {
            throw new StatementException("Expression does not evaluate to a BoolValue!");
        }
        state.setExecutionStack(stack);
        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(exp.deepCopy(), thenStmt, elseStmt);
    }

    @Override
    public String toString() {
        return "if (" + exp.toString() + ") then (" + thenStmt.toString()  + ") else (" + elseStmt.toString() + ")";
    }
}

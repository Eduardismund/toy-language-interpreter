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

import java.beans.Expression;
import java.io.FileNotFoundException;


public class WhileStmt implements IStmt{
    private Exp expression;
    private IStmt stmt;

    public WhileStmt(Exp exp, IStmt stmt) {
        this.expression = exp;
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, MyException, StatementException, ExpressionException, InterpreterException, FileNotFoundException, ADTException {
        final var exeStack = state.getExeStack();
        final var heap = state.getHeap();
        final var symTable = state.getSymTable();

        Value val = this.expression.eval(symTable, heap);

        if(!(val instanceof BoolValue)){
            throw new StatementException("The expression is not a boolean");
        }

        if(((BoolValue) val).getVal()){
            exeStack.push(this.deepCopy());
            exeStack.push(stmt);
        }

        state.setExecutionStack(exeStack);
        return state;
    }

    @Override
    public String toString() {
        return "while(" + expression.toString() + ") {" + this.stmt.toString() + "}";
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(expression.deepCopy(), stmt.deepCopy());
    }
}

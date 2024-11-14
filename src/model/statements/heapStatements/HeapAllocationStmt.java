package model.statements.heapStatements;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.InterpreterException;
import exceptions.StatementException;
import model.MyException;
import model.PrgState;
import model.expressions.Exp;
import model.statements.IStmt;
import model.types.RefType;
import model.values.RefValue;
import model.values.Value;

import java.io.FileNotFoundException;

public class HeapAllocationStmt implements IStmt {
    private String varName;
    private Exp exp;

    public HeapAllocationStmt(String varName, Exp exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, MyException, StatementException, ExpressionException, InterpreterException, FileNotFoundException, ADTException {
        final var symTale = state.getSymTable();
        final var heap = state.getHeap();

        if(!symTale.isDefined(varName)) {
            throw new StatementException("Variable " + varName + " is not defined");
        }

        final var varType = symTale.lookup(varName);

        if(!(varType.getType() instanceof RefType)) {
            throw new StatementException("Variable " + varName + " is not a Ref type");
        }

        Value val = exp.eval(symTale, heap);
        if(!val.getType().equals(((RefType) varType.getType()).getInner())) {
            throw new StatementException("Variable " + varName + " is not of type " + val.getType());
        }

        int next = heap.getNextFree();
        symTale.update(varName,new RefValue(next, val.getType()));
        heap.put(next,val);
        heap.updateNewFree();

        state.setSymbolTable(symTale);
        state.setHeap(heap);
        return null;
        ///return null;
    }

    @Override
    public String toString() {
        return "new(" + varName + ", " + exp.toString() + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new HeapAllocationStmt(varName, exp.deepCopy());
    }
}

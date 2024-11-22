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

public class HeapWritingStmt implements IStmt {
    private String varName;
    private Exp exp;

    public HeapWritingStmt(String varName, Exp exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, MyException, StatementException, ExpressionException, InterpreterException, FileNotFoundException, ADTException {
        final var symTable = state.getSymTable();
        final var heap = state.getHeap();

        if(!symTable.isDefined(varName)) {
            throw new StatementException("Variable '" + varName + "' not found in the symTable");
        }

        Value val = symTable.lookup(varName);

        if(!(val.getType() instanceof RefType)){
            throw new StatementException("Variable '" + varName + "' is not a reference type");
        }
        final var v = (RefValue)val;
        int address = v.getAddress();

        if(!heap.isDefined(address)) {
            throw new StatementException("Variable '" + varName + "' is not defined");
        }

        Value vi = exp.eval(symTable, heap);

        if(!(vi.getType().equals(heap.lookup(address).getType()))){
            throw new StatementException("Expression is not of variable type");
        }

        heap.put(address, vi);
        state.setHeap(heap);
        state.setSymbolTable(symTable);

        return state;

    }

    @Override
    public String toString() {
        return "writeHeap(" + this.varName + ", " + this.exp.toString() + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new HeapWritingStmt(varName, exp.deepCopy());
    }
}

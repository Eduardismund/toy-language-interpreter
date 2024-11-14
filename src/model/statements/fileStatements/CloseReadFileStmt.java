package model.statements.fileStatements;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.InterpreterException;
import exceptions.StatementException;
import model.MyException;
import model.PrgState;
import model.expressions.Exp;
import model.statements.IStmt;
import model.types.StringType;
import model.values.StringValue;
import model.values.Value;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CloseReadFileStmt implements IStmt {
    private StringType s = new StringType();

    private final Exp expression;

    public CloseReadFileStmt(Exp expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, MyException, StatementException, ExpressionException, InterpreterException, FileNotFoundException, ADTException {
        final var symTable = state.getSymTable();
        final var fileTable = state.getFileTable();
        final var heap = state.getHeap();

        Value value = expression.eval(symTable, heap);

        if (!value.getType().equals(s)) {
            throw new StatementException("CloseFile Expression is not a string");
        }

        StringValue sv = (StringValue) value;

        if (!fileTable.isDefined(sv)) {
            throw new StatementException("CloseFile is not defined in the file table");
        }

        try {
            fileTable.lookup(sv).close();
            fileTable.remove(sv);
        } catch (IOException e) {
            throw new StatementException("File cannot be closed" + e.getMessage());
        }
        state.setSymbolTable(symTable);
        state.setFileTable(fileTable);
        return null;
    }

        @Override
    public IStmt deepCopy() {
        return new CloseReadFileStmt(expression.deepCopy());
    }

    @Override
    public String toString() {
        return "closeFile(" + expression.toString() + ")";
    }
}

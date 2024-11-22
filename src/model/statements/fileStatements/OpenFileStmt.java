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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenFileStmt implements IStmt {

    private Exp expression;

    public OpenFileStmt(Exp exp){
        this.expression = exp;
    }

    @Override
    public String toString(){
        return "open(" + this.expression.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, MyException, StatementException, ExpressionException, InterpreterException, ADTException {
        final var symbolTable = state.getSymTable();
        final var fileTable = state.getFileTable();
        final var heap = state.getHeap();

        Value value = expression.eval(symbolTable, heap);

        if (!value.getType().equals(new StringType())) {
            throw new StatementException("Expression is not a string");
        }

        StringValue sv = (StringValue) value;

        if (fileTable.isDefined(sv)) {
            throw new StatementException("File table is already defined");
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(sv.getValue()));
            fileTable.put(sv, reader);

        } catch (FileNotFoundException e) {
            throw new StatementException("File cannot be opened!");
        } catch (ADTException e) {
            throw new RuntimeException(e);
        }
        state.setSymbolTable(symbolTable);
        state.setFileTable(fileTable);
        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new OpenFileStmt(expression.deepCopy());
    }
}

package toyLanguageInterpreter.model.statements.fileStatements;

import toyLanguageInterpreter.exceptions.ADTException;
import toyLanguageInterpreter.exceptions.ExpressionException;
import toyLanguageInterpreter.exceptions.InterpreterException;
import toyLanguageInterpreter.exceptions.StatementException;
import toyLanguageInterpreter.exceptions.MyException;
import toyLanguageInterpreter.model.PrgState;
import toyLanguageInterpreter.model.adt.MyIDictionary;
import toyLanguageInterpreter.model.expressions.Exp;
import toyLanguageInterpreter.model.statements.IStmt;
import toyLanguageInterpreter.model.types.StringType;
import toyLanguageInterpreter.model.types.Type;
import toyLanguageInterpreter.model.values.StringValue;
import toyLanguageInterpreter.model.values.Value;

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
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws ADTException, ExpressionException, StatementException {
        final var typeCheck = expression.typeCheck(typeEnv);
        if(!typeCheck.equals(s)){
            throw new StatementException("CloseFile Expression is not a string");
        }
        return typeEnv;
    }

    @Override
    public String toString() {
        return "closeFile(" + expression.toString() + ")";
    }
}

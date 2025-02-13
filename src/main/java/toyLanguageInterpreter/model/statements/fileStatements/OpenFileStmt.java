package toyLanguageInterpreter.model.statements.fileStatements;

import toyLanguageInterpreter.exceptions.ADTException;
import toyLanguageInterpreter.exceptions.ExpressionException;
import toyLanguageInterpreter.exceptions.InterpreterException;
import toyLanguageInterpreter.exceptions.StatementException;
import toyLanguageInterpreter.exceptions.MyException;
import toyLanguageInterpreter.model.PrgState;
import toyLanguageInterpreter.model.adt.dictionary.MyIDictionary;
import toyLanguageInterpreter.model.expressions.Exp;
import toyLanguageInterpreter.model.statements.IStmt;
import toyLanguageInterpreter.model.types.StringType;
import toyLanguageInterpreter.model.types.Type;
import toyLanguageInterpreter.model.values.StringValue;
import toyLanguageInterpreter.model.values.Value;

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
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new OpenFileStmt(expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws ADTException, ExpressionException, StatementException {
        final var typeCheck = expression.typeCheck(typeEnv);
        if(!typeCheck.equals(new StringType())){
            throw new StatementException("OpenFileStmt expression is not a string");
        }
        return typeEnv;
    }
}

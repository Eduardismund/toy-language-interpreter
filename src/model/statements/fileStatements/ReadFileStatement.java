package model.statements.fileStatements;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.InterpreterException;
import exceptions.StatementException;
import model.MyException;
import model.PrgState;
import model.expressions.Exp;
import model.statements.IStmt;
import model.types.IntType;
import model.types.StringType;
import model.values.IntValue;
import model.values.StringValue;
import model.values.Value;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ReadFileStatement implements IStmt {

    private final Exp expression;
    private final String varName;
    private StringType type = new StringType();
    private IntType type1 = new IntType();

    public ReadFileStatement(String varName, Exp expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, MyException, StatementException, ExpressionException, InterpreterException, FileNotFoundException, ADTException {

        final var symbolTable = state.getSymTable();
        final var heap = state.getHeap();

        if(!symbolTable.isDefined(varName)){
            throw new StatementException("Variable '" + varName + "' not defined");
        }

        Value value = symbolTable.lookup(varName);

        if(!value.getType().equals(type1)){
            throw new StatementException("ReadFile's value type is not an integer");
        }

        Value valueEval = expression.eval(symbolTable, heap);

        if(!valueEval.getType().equals(type)){
            throw new StatementException("ReadFile's expression value type is not a string");
        }

        StringValue sv = (StringValue)valueEval;

        if(!state.getFileTable().isDefined(sv)){
            throw new StatementException("File is not defined");
        }

        try{
            String line = state.getFileTable().lookup(sv).readLine();

            if(line == null){
                symbolTable.update(this.varName,type1.defaultValue());
            }else {
                symbolTable.update(this.varName,new IntValue(Integer.parseInt(line)));
            }
        } catch (IOException e) {
            throw new StatementException("Could not read from file");
        }
        state.setSymbolTable(symbolTable);
        return state;
    }

    @Override
    public String toString() {
        return "readFile(" + this.expression.toString() + "," + this.varName + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFileStatement(varName, expression.deepCopy());
    }
}

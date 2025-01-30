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
import toyLanguageInterpreter.model.types.IntType;
import toyLanguageInterpreter.model.types.StringType;
import toyLanguageInterpreter.model.types.Type;
import toyLanguageInterpreter.model.values.IntValue;
import toyLanguageInterpreter.model.values.StringValue;
import toyLanguageInterpreter.model.values.Value;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ReadFileStatement implements IStmt {

    private final Exp expression;
    private final String varName;
    private StringType stringType = new StringType();
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

        if(!valueEval.getType().equals(stringType)){
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
        return null;
    }

    @Override
    public String toString() {
        return "readFile(" + this.expression.toString() + "," + this.varName + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFileStatement(varName, expression.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws ADTException, ExpressionException, StatementException {
        final var typeCheck = typeEnv.lookup(varName);
        final var typeVar = expression.typeCheck(typeEnv);

        if(!(typeVar.equals(stringType))){
            throw new StatementException("ReadFile's expression type is not a string");
        }
        if(!(typeCheck.equals(type1))){
            throw new StatementException("ReadFile value is not a string");
        }
        return typeEnv;
    }
}

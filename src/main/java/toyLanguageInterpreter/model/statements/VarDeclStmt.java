package toyLanguageInterpreter.model.statements;

import toyLanguageInterpreter.exceptions.ADTException;
import toyLanguageInterpreter.exceptions.ExpressionException;
import toyLanguageInterpreter.exceptions.InterpreterException;
import toyLanguageInterpreter.exceptions.StatementException;
import toyLanguageInterpreter.exceptions.MyException;
import toyLanguageInterpreter.model.PrgState;
import toyLanguageInterpreter.model.types.Type;
import toyLanguageInterpreter.model.values.Value;
import toyLanguageInterpreter.model.adt.MyIDictionary;

public class VarDeclStmt implements IStmt {

    private String name;
    private Type type;

    public VarDeclStmt(Type type, String name) {
        this.name = name;
        this.type = type;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, StatementException, InterpreterException, ExpressionException, ADTException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        if (symTable.isDefined(name)) {
            throw new StatementException( "Variable is already declared!");
        } else {
            symTable.put(name, type.defaultValue());
        }
        state.setSymbolTable(symTable);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(type, name);
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws ADTException, ExpressionException, StatementException {
        typeEnv.put(name, type);
        return typeEnv;
    }

    @Override
    public String toString() {
        return this.type.toString() + " " + this.name;
    }
}

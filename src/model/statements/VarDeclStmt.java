package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.InterpreterException;
import exceptions.StatementException;
import model.MyException;
import model.PrgState;
import model.types.Type;
import model.values.Value;
import model.adt.MyIDictionary;

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
    public String toString() {
        return this.type.toString() + " " + this.name;
    }
}

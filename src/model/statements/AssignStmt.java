package model.statements;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.InterpreterException;
import exceptions.StatementException;
import model.MyException;
import model.PrgState;
import model.expressions.Exp;
import model.types.Type;
import model.values.Value;
import model.adt.MyIDictionary;

public class AssignStmt implements IStmt {
    private String id;
    private Exp exp;


    public AssignStmt(String id, Exp exp){
        this.id = id;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, StatementException, ExpressionException, InterpreterException, ADTException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        final var heap = state.getHeap();
        if(symTable.isDefined(id)){
            Value val = exp.eval(symTable, heap);
            Type typeId = symTable.lookup(id).getType();

            if(val.getType().equals(typeId)){
                symTable.update(id, val);
            } else{
                throw new StatementException("The types of the assigned variable and the variable " + id + " do not match!");
            }
        } else
        {
            throw new StatementException("the used variable " + id + " was not declared before!");
        }
        state.setSymbolTable(symTable);
        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new AssignStmt(id, exp.deepCopy());
    }

    @Override
    public String toString(){
        return id + " = " + exp.toString();
    }
}

package model.expressions;

import exceptions.ADTException;
import exceptions.ExpressionException;
import model.values.Value;
import model.adt.MyISymTable;

public class VarExp implements Exp{

    private String id;

    public VarExp(String id){
        this.id = id;
    }

    @Override
    public String toString(){
        return id;
    }

    @Override
    public Value eval(MyISymTable<String, Value> dict) throws ExpressionException, ADTException {
        return dict.lookup(this.id);
    }

    @Override
    public Exp deepCopy() {
        return new VarExp(id);
    }
}

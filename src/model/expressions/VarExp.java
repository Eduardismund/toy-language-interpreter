package model.expressions;

import exceptions.ADTException;
import exceptions.ExpressionException;
import model.values.Value;
import model.adt.MyIDictionary;

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
    public Value eval(MyIDictionary<String, Value> dict) throws ExpressionException, ADTException {
        return dict.lookup(this.id);
    }

    @Override
    public Exp deepCopy() {
        return new VarExp(id);
    }
}

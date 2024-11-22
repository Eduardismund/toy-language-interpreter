package model.expressions;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.InterpreterException;
import model.MyException;
import model.adt.MyIHeap;
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
    public Value eval(MyIDictionary<String, Value> dict, MyIHeap<Integer, Value> heap) throws MyException, MyException, ExpressionException, InterpreterException, ADTException {
        return dict.lookup(this.id);
    }

    @Override
    public Exp deepCopy() {
        return new VarExp(id);
    }
}

package model.expressions;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.InterpreterException;
import model.MyException;
import model.adt.MyIHeap;
import model.values.Value;
import model.adt.MyIDictionary;

public class ValueExp implements Exp {
    private Value e;

    public ValueExp(Value e) {
        this.e = e;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> dict, MyIHeap<Integer, Value> heap) throws MyException, MyException, ExpressionException, InterpreterException, ADTException {
        return e;
    }

    @Override
    public Exp deepCopy() {
        return new ValueExp(e);
    }

    @Override
    public String toString() {
        return e.toString();
    }
}

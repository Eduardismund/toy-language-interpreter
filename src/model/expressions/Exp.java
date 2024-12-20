package model.expressions;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.InterpreterException;
import model.MyException;
import model.adt.MyIHeap;
import model.values.Value;
import model.adt.MyIDictionary;

public interface Exp {
    Value eval(MyIDictionary<String, Value> dict, MyIHeap<Integer,Value> heap) throws MyException, MyException, ExpressionException, InterpreterException, ADTException;
    Exp deepCopy();
}

package model.expressions;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.InterpreterException;
import model.MyException;
import model.values.Value;
import model.adt.MyISymTable;

public interface Exp {
    Value eval(MyISymTable<String, Value> dict) throws MyException, MyException, ExpressionException, InterpreterException, ADTException;
    Exp deepCopy();
}

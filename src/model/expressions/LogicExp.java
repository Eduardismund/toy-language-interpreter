package model.expressions;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.InterpreterException;
import model.MyException;
import model.types.BoolType;
import model.values.BoolValue;
import model.values.Value;
import model.adt.MyIDictionary;

public class LogicExp implements Exp{

    private Exp e1;
    private Exp e2;
    private String operator;

    public LogicExp(Exp e1, Exp e2, String operator) {
        this.e1 = e1;
        this.e2 = e2;
        this.operator = operator;
    }


    @Override
    public Value eval(MyIDictionary<String, Value> dict) throws ExpressionException, InterpreterException, MyException, ADTException {
        Value v1 = e1.eval(dict);
        Value v2 = e2.eval(dict);

        if (!(v1.getType() instanceof BoolType) || !(v2.getType() instanceof BoolType)) {
            throw new ExpressionException("The operands are not of type BoolValue!");
        }

        boolean b1 = ((BoolValue) v1).getVal();
        boolean b2 = ((BoolValue) v2).getVal();

        if (operator.equals("&&")) {
            return new BoolValue(b1 && b2);
        } else if (operator.equals("||")) {
            return new BoolValue(b1 || b2);
        } else {
            throw new ExpressionException("Unknown operator: " + operator);
        }
    }

    @Override
    public Exp deepCopy() {
        return new LogicExp(e1.deepCopy(), e2.deepCopy(), operator);
    }

    @Override
    public String toString(){
        return e1.toString() + " " + operator + " " + e2.toString();
    }
}

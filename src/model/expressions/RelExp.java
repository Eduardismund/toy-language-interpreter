package model.expressions;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.InterpreterException;
import model.MyException;
import model.types.IntType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.Value;
import model.adt.MyISymTable;

public class RelExp implements Exp{

    private final String operator;
    private final Exp exp1;
    private final Exp exp2;

    private final IntType classType = new IntType();

    public RelExp(String operator, Exp exp1, Exp exp2) {
        this.operator = operator;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }


    @Override
    public Value eval(MyISymTable<String, Value> dict) throws MyException, MyException, ExpressionException, InterpreterException, ADTException {

        Value value1 = exp1.eval(dict);
        if(!value1.getType().equals(classType)){
            throw new ExpressionException("The type of the first operand is not an integer!");
        }
        Value value2 = exp2.eval(dict);
        if(!value2.getType().equals(classType)){
            throw new ExpressionException("The type of the second operand is not an integer!");
        }

        IntValue operand1 = (IntValue) value1;
        IntValue operand2 = (IntValue) value2;

        int operandInteger1 = operand1.getVal();
        int operandInteger2 = operand2.getVal();

        switch(operator){
            case "<" -> {return new BoolValue(operandInteger1 < operandInteger2);}
            case "<=" -> {return new BoolValue(operandInteger1 <= operandInteger2);}
            case ">" -> {return new BoolValue(operandInteger1 > operandInteger2);}
            case ">=" -> {return new BoolValue(operandInteger1 >= operandInteger2);}
            case "==" -> {return new BoolValue(operandInteger1 == operandInteger2);}
            case "!=" -> {return new BoolValue(operandInteger1 != operandInteger2);}
            default -> {throw new ExpressionException("The operator " + operator + " is not supported!");}
        }
    }

    @Override
    public Exp deepCopy() {
        return new RelExp(operator, exp1.deepCopy(), exp2.deepCopy());
    }

    @Override
    public String toString(){
        return exp1.toString() + " " + operator + " " + exp2.toString();
    }
}

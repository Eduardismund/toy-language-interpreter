package model.expressions;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.InterpreterException;
import model.adt.MyIHeap;
import model.types.IntType;
import model.values.IntValue;
import model.values.Value;
import model.adt.MyIDictionary;
import model.MyException;

public class ArithExp implements Exp{

    private Exp e1;
    private Exp e2;
    private int op; // 1=+, 2=-, 3=*, 4=/
    private IntType classType= new IntType();

    public ArithExp(Exp e1, Exp e2, int op){
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> dict, MyIHeap<Integer, Value> heap) throws MyException, MyException, ExpressionException, InterpreterException, ADTException {
        Value v1 = e1.eval(dict,heap);
        if(v1.getType().equals(classType)){
            Value v2 = e2.eval(dict,heap);
            if(v2.getType().equals(classType)){
                IntValue i1 = (IntValue)v1;
                IntValue i2 = (IntValue)v2;

                int n1 = i1.getVal();
                int n2 = i2.getVal();

                switch(op) {
                    case 1:
                        return new IntValue(n1 + n2);
                    case 2:
                        return new IntValue(n1 - n2);
                    case 3:
                        return new IntValue(n1 * n2);
                    case 4:
                        if (n2 == 0) {
                            throw new ExpressionException("Division by 0!");}
                        return new IntValue(n1 / n2);
                }
            } else {
                throw new ExpressionException("Second operand is not an integer");
            }

        } else {
            throw new ExpressionException("First operand is not an integer!");
        }
        return null;
    }

    @Override
    public Exp deepCopy() {
        return new ArithExp(e1.deepCopy(), e2.deepCopy(), op);
    }

    @Override
    public String toString() {
        switch (op) {
            case 1:
                return this.e1.toString() + " + " + this.e2.toString();
            case 2:
                return this.e1.toString() + " - " + this.e2.toString();
            case 3:
                return this.e1.toString() + " * " + this.e2.toString();
            case 4:
                return this.e1.toString() + " / " + this.e2.toString();
        }
        return "";
    }
}

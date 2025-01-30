package toyLanguageInterpreter.model.expressions;

import toyLanguageInterpreter.exceptions.ADTException;
import toyLanguageInterpreter.exceptions.ExpressionException;
import toyLanguageInterpreter.exceptions.InterpreterException;
import toyLanguageInterpreter.model.adt.heapTable.MyIHeap;
import toyLanguageInterpreter.model.types.IntType;
import toyLanguageInterpreter.model.types.Type;
import toyLanguageInterpreter.model.values.IntValue;
import toyLanguageInterpreter.model.values.Value;
import toyLanguageInterpreter.model.adt.dictionary.MyIDictionary;
import toyLanguageInterpreter.exceptions.MyException;

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


    public Type typeCheck(MyIDictionary<String,Type> typeEnv) throws ADTException, ExpressionException{
        final var typ1 = e1.typeCheck(typeEnv);
        final var typ2 = e2.typeCheck(typeEnv);

        if (!(typ1.equals(classType))) {
            throw new ExpressionException("The first operand is not of Int Type!");
        }
        if (!(typ2.equals(classType))) {
            throw new ExpressionException("The second operand is not of Int Type!");
        }

        return classType;

    }
}

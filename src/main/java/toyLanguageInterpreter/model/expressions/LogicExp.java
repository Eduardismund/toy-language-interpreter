package toyLanguageInterpreter.model.expressions;

import toyLanguageInterpreter.exceptions.ADTException;
import toyLanguageInterpreter.exceptions.ExpressionException;
import toyLanguageInterpreter.exceptions.InterpreterException;
import toyLanguageInterpreter.exceptions.MyException;
import toyLanguageInterpreter.model.adt.heapTable.MyIHeap;
import toyLanguageInterpreter.model.types.BoolType;
import toyLanguageInterpreter.model.types.Type;
import toyLanguageInterpreter.model.values.BoolValue;
import toyLanguageInterpreter.model.values.Value;
import toyLanguageInterpreter.model.adt.dictionary.MyIDictionary;


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
    public Value eval(MyIDictionary<String, Value> dict, MyIHeap<Integer, Value> heap) throws MyException, MyException, ExpressionException, InterpreterException, ADTException {
        Value v1 = e1.eval(dict, heap);
        Value v2 = e2.eval(dict, heap);

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
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException, ADTException {
        final var type1 = e1.typeCheck(typeEnv);
        final var type2 = e2.typeCheck(typeEnv);

        if(!(type1 instanceof BoolType)){
            throw new ExpressionException("Type of " + e1 + " is not of type BoolType!");
        }

        if(!(type2 instanceof BoolType)){
            throw new ExpressionException("Type of " + e2 + " is not of type BoolType!");
        }

        return new BoolType();
    }

    @Override
    public String toString(){
        return e1.toString() + " " + operator + " " + e2.toString();
    }
}

package toyLanguageInterpreter.model.expressions;

import toyLanguageInterpreter.exceptions.ADTException;
import toyLanguageInterpreter.exceptions.ExpressionException;
import toyLanguageInterpreter.exceptions.InterpreterException;
import toyLanguageInterpreter.exceptions.MyException;
import toyLanguageInterpreter.model.adt.MyIHeap;
import toyLanguageInterpreter.model.types.BoolType;
import toyLanguageInterpreter.model.types.IntType;
import toyLanguageInterpreter.model.types.RefType;
import toyLanguageInterpreter.model.types.Type;
import toyLanguageInterpreter.model.values.BoolValue;
import toyLanguageInterpreter.model.values.IntValue;
import toyLanguageInterpreter.model.values.Value;
import toyLanguageInterpreter.model.adt.MyIDictionary;

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
    public Value eval(MyIDictionary<String, Value> dict, MyIHeap<Integer, Value> heap) throws MyException, MyException, ExpressionException, InterpreterException, ADTException {
        Value value1 = exp1.eval(dict, heap);
        if(!value1.getType().equals(classType)){
            throw new ExpressionException("The type of the first operand is not an integer!");
        }
        Value value2 = exp2.eval(dict,heap);
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
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException, ADTException {
        final var typ1 = exp1.typeCheck(typeEnv);
        final var typ2 = exp1.typeCheck(typeEnv);

        if (!(typ1 instanceof IntType)) {
            throw new ExpressionException("The first operand is not an Int Type!");
        }
        if (!(typ2 instanceof IntType)) {
            throw new ExpressionException("The second operand is not an Int Type!");
        }

        return new BoolType();
    }


    @Override
    public String toString(){
        return exp1.toString() + " " + operator + " " + exp2.toString();
    }
}

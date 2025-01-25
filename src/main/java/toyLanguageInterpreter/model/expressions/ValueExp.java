package toyLanguageInterpreter.model.expressions;

import toyLanguageInterpreter.exceptions.ADTException;
import toyLanguageInterpreter.exceptions.ExpressionException;
import toyLanguageInterpreter.exceptions.InterpreterException;
import toyLanguageInterpreter.exceptions.MyException;
import toyLanguageInterpreter.model.adt.MyIHeap;
import toyLanguageInterpreter.model.types.Type;
import toyLanguageInterpreter.model.values.Value;
import toyLanguageInterpreter.model.adt.MyIDictionary;

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
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException {
        return e.getType();
    }

    @Override
    public String toString() {
        return e.toString();
    }
}

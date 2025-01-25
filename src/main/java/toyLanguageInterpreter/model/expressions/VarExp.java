package toyLanguageInterpreter.model.expressions;

import toyLanguageInterpreter.exceptions.ADTException;
import toyLanguageInterpreter.exceptions.ExpressionException;
import toyLanguageInterpreter.exceptions.InterpreterException;
import toyLanguageInterpreter.exceptions.MyException;
import toyLanguageInterpreter.model.adt.MyIHeap;
import toyLanguageInterpreter.model.types.Type;
import toyLanguageInterpreter.model.values.Value;
import toyLanguageInterpreter.model.adt.MyIDictionary;

public class VarExp implements Exp{

    private String id;

    public VarExp(String id){
        this.id = id;
    }

    @Override
    public String toString(){
        return id;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> dict, MyIHeap<Integer, Value> heap) throws MyException, MyException, ExpressionException, InterpreterException, ADTException {
        return dict.lookup(this.id);
    }

    @Override
    public Exp deepCopy() {
        return new VarExp(id);
    }

    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException, ADTException {
        return typeEnv.lookup(id);
    }
}

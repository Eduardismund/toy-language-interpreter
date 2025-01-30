package toyLanguageInterpreter.model.expressions;

import toyLanguageInterpreter.exceptions.ADTException;
import toyLanguageInterpreter.exceptions.ExpressionException;
import toyLanguageInterpreter.exceptions.InterpreterException;
import toyLanguageInterpreter.exceptions.MyException;
import toyLanguageInterpreter.model.adt.MyIDictionary;
import toyLanguageInterpreter.model.adt.MyIHeap;
import toyLanguageInterpreter.model.types.RefType;
import toyLanguageInterpreter.model.types.Type;
import toyLanguageInterpreter.model.values.RefValue;
import toyLanguageInterpreter.model.values.Value;

public class HeapReadingExp implements Exp{
    private Exp exp;

    public HeapReadingExp(Exp exp){
        this.exp = exp;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> dict, MyIHeap<Integer, Value> heap) throws MyException, MyException, ExpressionException, InterpreterException, ADTException {
        Value val = exp.eval(dict, heap);

        if (!(val instanceof RefValue)){
            throw new ExpressionException("HeapReadingExp expects a ref type");

        }

        int address = ((RefValue) val).getAddress();

        if(!heap.isDefined(address)){
            throw new ExpressionException("The Address does not exist in the heap");
        }
        return heap.lookup(address);
    }

    @Override
    public String toString(){
        return "rh(" + exp.toString() + ")";
    }

    @Override
    public Exp deepCopy() {
        return new HeapReadingExp(exp.deepCopy());
    }

    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeEnv) throws ExpressionException, ADTException {
        final var typ = exp.typeCheck(typeEnv);
        if (!(typ instanceof RefType)) {
            throw new ExpressionException("Type check expects a ref type");
        }
        final var reft =(RefType) typ;
        return reft.getInner();
    }
}

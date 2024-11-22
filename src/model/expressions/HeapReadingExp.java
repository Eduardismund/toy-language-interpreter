package model.expressions;

import exceptions.ADTException;
import exceptions.ExpressionException;
import exceptions.InterpreterException;
import model.MyException;
import model.adt.MyIDictionary;
import model.adt.MyIHeap;
import model.types.RefType;
import model.values.RefValue;
import model.values.Value;

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
        System.out.println(heap.lookup(address));
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
}

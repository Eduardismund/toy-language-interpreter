package model.expressions;

import model.values.Value;
import model.adt.MyISymTable;

public class ValueExp implements Exp {
    private Value e;

    public ValueExp(Value e) {
        this.e = e;
    }

    @Override
    public Value eval(MyISymTable<String, Value> dict) {
        return e;
    }

    @Override
    public Exp deepCopy() {
        return new ValueExp(e);
    }

    @Override
    public String toString() {
        return e.toString();
    }
}

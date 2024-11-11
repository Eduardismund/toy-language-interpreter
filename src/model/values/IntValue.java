package model.values;

import model.types.IntType;
import model.types.Type;


public class IntValue implements Value{
    private final int val;

    public IntValue(int val){
        this.val = val;
    }

    public int getVal(){
        return val;
    }

    @Override
    public Value deepCopy() {
        return new IntValue(val);
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public String toString(){
        return String.valueOf(val);
    }

    @Override
    public boolean equals(Object o){
        if (o == this) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        IntValue that = (IntValue) o;
        return this.val == that.val;
    }
}

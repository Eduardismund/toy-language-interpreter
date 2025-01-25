package toyLanguageInterpreter.model.values;

import toyLanguageInterpreter.model.types.BoolType;
import toyLanguageInterpreter.model.types.Type;

public class BoolValue implements Value{
    private final boolean val;

    public boolean getVal(){
        return val;
    }

    public BoolValue(boolean val){
        this.val = val;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public Value deepCopy() {
        return new BoolValue(val);
    }

    @Override
    public String toString(){
        return String.valueOf(val);
    }

    @Override
    public boolean equals(Object o) {
        if( this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        BoolValue that = (BoolValue) o;
        return this.val == that.val;
    }
}

package toyLanguageInterpreter.model.types;

import toyLanguageInterpreter.model.values.RefValue;
import toyLanguageInterpreter.model.values.Value;

public class RefType implements Type{
    private Type inner;
    public RefType(Type inner) {this.inner=inner;}
    public Type getInner() {return inner;}

    @Override
    public boolean equals(Object another){
        if (another instanceof  RefType)
            return inner.equals(((RefType) another).getInner());
        else
            return false;
    }

    @Override
    public String toString() { return "Ref(" +inner.toString()+")";}

    @Override
    public Value defaultValue() { return new RefValue(0,inner);}

    @Override
    public Type deepCopy() {
        return new RefType(inner.deepCopy());
    }
}
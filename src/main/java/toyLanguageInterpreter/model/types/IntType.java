package toyLanguageInterpreter.model.types;

import toyLanguageInterpreter.model.values.IntValue;
import toyLanguageInterpreter.model.values.Value;

public class IntType implements Type {

    @Override
    public boolean equals(Object another){
        return another instanceof IntType;
    }

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public Value defaultValue() {
        return new IntValue(0);
    }

    @Override
    public Type deepCopy() {
        return new IntType();
    }
}

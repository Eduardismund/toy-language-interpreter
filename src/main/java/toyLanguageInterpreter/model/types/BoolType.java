package toyLanguageInterpreter.model.types;

import toyLanguageInterpreter.model.values.BoolValue;
import toyLanguageInterpreter.model.values.Value;

public class BoolType implements Type {

    @Override
    public boolean equals(Object another) {
        return another instanceof BoolType;
    }

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public Value defaultValue() {
        return new BoolValue(false);
    }

    @Override
    public Type deepCopy() {
        return new BoolType();
    }
}

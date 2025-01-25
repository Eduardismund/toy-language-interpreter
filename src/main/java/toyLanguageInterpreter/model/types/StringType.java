package toyLanguageInterpreter.model.types;

import toyLanguageInterpreter.model.values.Value;
import toyLanguageInterpreter.model.values.StringValue;

public class StringType implements Type{

    @Override
    public boolean equals(Object other){
        return other instanceof StringType;
    }

    @Override
    public String toString(){
        return "string";
    }

    @Override
    public Value defaultValue() {
        return new StringValue(" ");
    }

    @Override
    public Type deepCopy() {
        return new StringType();
    }

}

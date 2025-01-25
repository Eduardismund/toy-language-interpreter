package toyLanguageInterpreter.model.values;

import toyLanguageInterpreter.model.types.StringType;
import toyLanguageInterpreter.model.types.Type;

import java.util.Objects;

public class StringValue implements Value{
    private String value;

    public StringValue(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    @Override
    public String toString(){
        return value;
    }

    @Override
    public boolean equals(Object other){
        if (other == this) return true;
        if(other == null || getClass() != other.getClass()) return false;
        StringValue that = (StringValue) other;
        return Objects.equals(this.value, that.value);
    }
    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public Value deepCopy() {
        return new StringValue(value);
    }
}

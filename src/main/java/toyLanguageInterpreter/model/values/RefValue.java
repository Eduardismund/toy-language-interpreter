package toyLanguageInterpreter.model.values;

import toyLanguageInterpreter.model.types.Type;
import toyLanguageInterpreter.model.types.RefType;

public class RefValue implements Value{

    private int address;
    private Type locationType;

    public RefValue(int i, Type inner) {
        this.address = i;
        this.locationType = inner;
    }

    public int getAddress() {return address;}

    @Override
    public Type getType() {
        return new RefType(locationType);}


    @Override
    public Value deepCopy() {
            return new RefValue(address, locationType);
        }
    @Override
    public String toString() {
        return "(" + address + ", " + locationType + ")";
    }
}

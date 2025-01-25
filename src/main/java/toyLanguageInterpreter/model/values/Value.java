package toyLanguageInterpreter.model.values;

import toyLanguageInterpreter.model.types.Type;

public interface Value {
    Type getType();
    Value deepCopy();
}

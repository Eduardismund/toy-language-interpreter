package toyLanguageInterpreter.model.types;


import toyLanguageInterpreter.model.values.Value;

public interface Type {
    Value defaultValue();
    Type deepCopy();
}

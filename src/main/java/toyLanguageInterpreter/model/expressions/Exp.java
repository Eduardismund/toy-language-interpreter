package toyLanguageInterpreter.model.expressions;

import toyLanguageInterpreter.exceptions.ADTException;
import toyLanguageInterpreter.exceptions.ExpressionException;
import toyLanguageInterpreter.exceptions.InterpreterException;
import toyLanguageInterpreter.exceptions.MyException;
import toyLanguageInterpreter.model.adt.heapTable.MyIHeap;
import toyLanguageInterpreter.model.types.Type;
import toyLanguageInterpreter.model.values.Value;
import toyLanguageInterpreter.model.adt.dictionary.MyIDictionary;

public interface Exp {
    Value eval(MyIDictionary<String, Value> dict, MyIHeap<Integer,Value> heap) throws MyException, MyException, ExpressionException, InterpreterException, ADTException;
    Exp deepCopy();
    Type typeCheck(MyIDictionary<String,Type> typeEnv) throws ExpressionException, ADTException;
}

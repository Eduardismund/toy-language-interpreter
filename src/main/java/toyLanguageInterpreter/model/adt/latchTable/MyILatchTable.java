package toyLanguageInterpreter.model.adt.latchTable;
import toyLanguageInterpreter.exceptions.ADTException;
import toyLanguageInterpreter.model.adt.dictionary.MyIDictionary;


public interface MyILatchTable extends MyIDictionary<Integer, Integer> {
    int put(int counter) throws ADTException;
    int getCounter(int counter) throws ADTException;
    void decrementCounter(int latchAddress) throws ADTException;
}





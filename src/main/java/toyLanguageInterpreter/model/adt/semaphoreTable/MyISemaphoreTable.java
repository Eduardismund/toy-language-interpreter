package toyLanguageInterpreter.model.adt.semaphoreTable;

import javafx.util.Pair;
import toyLanguageInterpreter.exceptions.ADTException;
import toyLanguageInterpreter.model.adt.dictionary.MyIDictionary;

import java.util.List;

public interface MyISemaphoreTable extends MyIDictionary<Integer, Pair<Integer, List<Integer>>> {
    int put(Pair<Integer, List<Integer>> value) throws ADTException;
    int getFreeAddress();
}










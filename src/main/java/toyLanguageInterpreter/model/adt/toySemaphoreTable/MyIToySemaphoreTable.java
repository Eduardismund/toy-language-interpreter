package toyLanguageInterpreter.model.adt.toySemaphoreTable;

import javafx.util.Pair;
import toyLanguageInterpreter.exceptions.ADTException;
import toyLanguageInterpreter.model.adt.dictionary.MyIDictionary;

import java.util.List;

public interface MyIToySemaphoreTable extends MyIDictionary<Integer, Pair<Integer, Pair<List<Integer>, Integer>>> {
    int put(Pair<Integer, Pair<List<Integer>, Integer>> value) throws ADTException;
    int getFreeAddress();
}





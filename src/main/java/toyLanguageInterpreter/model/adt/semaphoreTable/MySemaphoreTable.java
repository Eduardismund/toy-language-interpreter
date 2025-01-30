package toyLanguageInterpreter.model.adt.semaphoreTable;

import javafx.util.Pair;
import toyLanguageInterpreter.exceptions.ADTException;
import toyLanguageInterpreter.model.adt.dictionary.MyDictionary;

import java.util.List;

public class MySemaphoreTable extends MyDictionary<Integer, Pair<Integer, List<Integer>>> implements MyISemaphoreTable {
    private int freeAddress = 1;

    // constructor
    public MySemaphoreTable() {
        super();
    }

    @Override
    public int put(Pair<Integer, List<Integer>> pair) throws ADTException {
        synchronized (this) {
            this.put(this.freeAddress, pair);
            this.freeAddress++;
            return this.freeAddress - 1;
        }
    }
    @Override
    public int getFreeAddress() {
        synchronized (this) {
            return this.freeAddress;
        }
    }
}
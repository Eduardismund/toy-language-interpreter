package toyLanguageInterpreter.model.adt.toySemaphoreTable;

import javafx.util.Pair;
import toyLanguageInterpreter.exceptions.ADTException;
import toyLanguageInterpreter.model.adt.dictionary.MyDictionary;

import java.util.List;

public class MyToySemaphoreTable extends MyDictionary<Integer, Pair<Integer, Pair<List<Integer>, Integer>>> implements MyIToySemaphoreTable {
    private int freeAddress = 1;

    public MyToySemaphoreTable() {
        super(); // Initializes the dictionary (Map)
    }

    // Adds a new semaphore entry and returns the allocated address
    @Override
    public int put(Pair<Integer, Pair<List<Integer>, Integer>> value) throws ADTException {
        synchronized (this) {
            int address = freeAddress;
            this.put(address, value);  // Adds the semaphore entry at the current address
            freeAddress++;             // Increments the address for the next entry
            return address;
        }
    }

    // Retrieves the next available address for the semaphore table
    @Override
    public int getFreeAddress() {
        synchronized (this) {
            return freeAddress;
        }
    }
}















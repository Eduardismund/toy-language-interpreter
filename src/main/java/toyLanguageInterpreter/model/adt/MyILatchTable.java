package toyLanguageInterpreter.model.adt;

import toyLanguageInterpreter.exceptions.ADTException;

import java.util.Map;

public interface MyILatchTable {
    int addNewLatch(int counter);
    int getLatchCounter(int latchAddress) throws ADTException;
    void decrementLatchCounter(int latchAddress) throws ADTException;
    boolean isLatchAddressUsed(int latchAddress);
    Map<Integer, Integer> getContent();
}








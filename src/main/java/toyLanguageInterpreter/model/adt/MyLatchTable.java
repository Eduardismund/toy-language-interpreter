package toyLanguageInterpreter.model.adt;

import toyLanguageInterpreter.exceptions.ADTException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MyLatchTable implements MyILatchTable {
    private final HashMap<Integer, Integer> elems;
    private final AtomicInteger nextFreeLocation;

    public MyLatchTable() {
        this.nextFreeLocation = new AtomicInteger(1);
        this.elems = new HashMap<>();
    }

    @Override
    public synchronized int addNewLatch(int counter) {
        int addressUsed = this.nextFreeLocation.getAndIncrement();
        this.elems.put(addressUsed, counter);
        return addressUsed;
    }

    @Override
    public synchronized int getLatchCounter(int latchAddress) throws ADTException {
        if (!this.elems.containsKey(latchAddress)) {
            throw new ADTException("ERROR: The address " + latchAddress + " is not an index in the Latch Table.");
        }
        return this.elems.get(latchAddress);
    }

    @Override
    public synchronized void decrementLatchCounter(int latchAddress) throws ADTException {
        if (!this.elems.containsKey(latchAddress)) {
            throw new ADTException("ERROR: The address " + latchAddress + " is not an index in the Latch Table.");
        }
        if (this.elems.get(latchAddress) == 0) {
            throw new ADTException("ERROR: The counter of the latch at the address " + latchAddress + " already reached zero.");
        }
        this.elems.replace(latchAddress, this.elems.get(latchAddress) - 1);
    }

    @Override
    public synchronized boolean isLatchAddressUsed(int latchAddress) {
        return this.elems.containsKey(latchAddress);
    }

    @Override
    public synchronized Map<Integer, Integer> getContent() {
        return this.elems;
    }

    @Override
    public String toString() {
        String result = "{";
        for(Integer key : this.elems.keySet()){
            result += key.toString() + " -> " + this.elems.get(key).toString() + ", ";
        }
        if(result.length() > 1)
            result = result.substring(0, result.length() - 2);
        result += "}";
        return result;
    }

}

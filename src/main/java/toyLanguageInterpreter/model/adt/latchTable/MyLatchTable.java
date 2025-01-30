package toyLanguageInterpreter.model.adt.latchTable;

import toyLanguageInterpreter.exceptions.ADTException;
import toyLanguageInterpreter.model.adt.dictionary.MyDictionary;

public class MyLatchTable extends MyDictionary<Integer, Integer> implements MyILatchTable{
    private int freeAddress = 1;

    public MyLatchTable() {
        super();
    }

    @Override
    public synchronized int put(int counter) throws ADTException {
        super.put(this.freeAddress, counter);
        this.freeAddress++;
        return this.freeAddress - 1;
    }

    @Override
    public synchronized int getCounter(int counter) throws ADTException {
        if (!this.getMap().containsKey(counter)) {
            throw new ADTException("The address " + counter + " is not an index in the Latch Table.");
        }
        return this.getMap().get(counter);
    }

    @Override
    public synchronized void decrementCounter(int latchAddress) throws ADTException {
        if (!this.getMap().containsKey(latchAddress)) {
            throw new ADTException("The address " + latchAddress + " is not an index in the Latch Table.");
        }
        if (this.getMap().get(latchAddress) == 0) {
            throw new ADTException("The counter of the latch at the address " + latchAddress + " already reached zero.");
        }
        this.getMap().replace(latchAddress, this.getMap().get(latchAddress) - 1);
    }

}

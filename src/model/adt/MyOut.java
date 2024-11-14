package model.adt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyOut<T> implements MyIOut<T> {

    private final List<T> list;

    public MyOut() {
        list = new ArrayList<T>();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public Iterator<T> iterator() {
        synchronized (list) {
            return list.iterator();
        }
    }

    @Override
    public void add(T item) {
        list.add(item);
    }

    @Override
    public List<T> getList() {
        return list;
    }

    @Override
    public String toString() {
        return list.toString();
    }
}

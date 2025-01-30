package toyLanguageInterpreter.model.adt;

import toyLanguageInterpreter.exceptions.ADTException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyList<T> implements MyIList<T> {

    private final List<T> list;

    public MyList() {
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

    public T get(int index) throws ADTException {
        if (index < 0 || index >= list.size())
            throw new ADTException("Index out of bounds!");
        try {
            return list.get(index);
        } catch (Exception exception) {
            throw new ADTException(exception.getMessage());
        }
    }
}

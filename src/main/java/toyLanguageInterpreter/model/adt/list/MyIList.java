package toyLanguageInterpreter.model.adt.list;

import toyLanguageInterpreter.exceptions.ADTException;

import java.util.Iterator;
import java.util.List;

public interface MyIList<T> {
    boolean isEmpty();
    int size();
    Iterator<T> iterator();
    void add(T item);
    List<T> getList();
    String toString();
    <T> T get(int index) throws ADTException;
}

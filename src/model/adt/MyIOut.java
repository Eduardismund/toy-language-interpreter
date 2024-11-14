package model.adt;

import java.util.Iterator;
import java.util.List;

public interface MyIOut<T> {
    boolean isEmpty();
    int size();
    Iterator<T> iterator();
    void add(T item);
    List<T> getList();
    String toString();
}

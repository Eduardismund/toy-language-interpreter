package toyLanguageInterpreter.model.adt.stack;

import toyLanguageInterpreter.exceptions.ADTException;
import toyLanguageInterpreter.exceptions.InterpreterException;

import java.util.List;

public interface MyIStack<T> {
    void push(T item);
    T pop() throws ADTException, InterpreterException;
    T peek() throws ADTException, InterpreterException;
    boolean isEmpty();
    int size();
    String toString();
    List<T> toListS();
    MyIStack<T> deepCopy();
}

package model.adt;

import exceptions.InterpreterException;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {

    private Stack<T> stack;

    public MyStack(){
        stack = new Stack<>();
    }

    @Override
    public void push(T item) {
        stack.push(item);
    }

    @Override
    public T pop() throws InterpreterException {
        if(stack.isEmpty())
        {
            throw new InterpreterException("Stack is empty!");
        }
        return stack.pop();
    }

    @Override
    public T peek() throws InterpreterException {
        if(stack.isEmpty())
        {
            throw new InterpreterException("Stack is empty!");
        }
        return stack.peek();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public int size() {
        return stack.size();
    }


    public List<T> toListS() {
        return  (List<T>) Arrays.asList(stack.toArray());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(T t: stack){
            sb.append(" |").append(t).append("| ").append("\n");
        }
        return sb.toString();
    }
}

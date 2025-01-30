package toyLanguageInterpreter.model.adt;

import toyLanguageInterpreter.exceptions.InterpreterException;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {

    protected Stack<T> stack;

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
    public MyIStack<T> deepCopy() {
        MyIStack<T> newStack = new MyStack<T>();
        for(T element : this.stack){
            newStack.push(element);
        }
        return newStack;

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Iterate through the stack from top to bottom (reverse order)
        for (int i = stack.size() - 1; i >= 0; i--) {
            sb.append("|").append(stack.get(i)).append("|\n");
        }

        return sb.toString();
    }

}

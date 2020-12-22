package Domain.ADTS;

import Domain.Exception.MyException;

import java.util.Stack;

public interface IStack<T> {
    T pop() throws MyException;
    void push(T value);
    T peek() throws MyException;
    boolean isEmpty();
    Stack<T> getContent();
    String toString();
}

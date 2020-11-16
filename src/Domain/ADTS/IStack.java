package Domain.ADTS;

import Domain.Exception.MyException;

import java.util.Stack;

public interface IStack<T> {
    T pop() throws MyException;
    void push(T value);
    boolean isEmpty();
    String toString();
}

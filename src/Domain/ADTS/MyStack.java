package Domain.ADTS;
import Domain.Exception.ADTException;
import Domain.Exception.MyException;

import java.util.Stack;
public class MyStack<T> implements IStack<T>{
    java.util.Stack<T> stack;
    public MyStack() {stack = new Stack<T>();}
    public T pop() throws MyException {
        if(stack.isEmpty()){
            throw new ADTException("Stack is empty! /n");
        }
        return stack.pop();
    }
    public void push(T value){
        stack.add(value);
    }
    public boolean isEmpty(){
        return stack.isEmpty();
    }
    public String toString() {
        StringBuilder stringOut = new StringBuilder();
        Object[] values = stack.toArray();
        for(int i=values.length - 1; i>= 0; i--){
            stringOut.append(values[i]);
            stringOut.append(" || ");
        }
        return stringOut.toString();
    }
}

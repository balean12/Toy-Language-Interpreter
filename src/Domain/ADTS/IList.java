package Domain.ADTS;

import Domain.Exception.MyException;

public interface IList<T> {
    T pop() throws MyException;
    void add(T value);
    boolean isEmpty();
    T getValue(int index);
    int getSize();
    String toString();
}

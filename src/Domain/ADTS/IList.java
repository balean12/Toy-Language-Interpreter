package Domain.ADTS;

import Domain.Exception.MyException;

public interface IList<T> {
    T pop() throws MyException;
    void add(T value);
    boolean isEmpty();
    String toString();
}

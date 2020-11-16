package Domain.ADTS;

import Domain.Exception.MyException;

public interface IDictionary<T1,T2> {
    void add(T1 key, T2 value) throws MyException;
    void update(T1 key, T2 value) throws MyException;
    T2 lookup(T1 key) throws MyException;
    boolean isDefined(T1 key);
    String toString();

}

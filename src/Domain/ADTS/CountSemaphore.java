package Domain.ADTS;

import Domain.Exception.MyException;

import java.util.Map;

public class CountSemaphore<T1,T2> extends MyDictionary<T1,T2> implements ICountSemaphore<T1,T2>{
    public static Integer index=0;
    @Override
    public synchronized int createIndex() {
               return index+=1;
    }
}

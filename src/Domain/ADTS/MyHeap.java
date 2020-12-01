package Domain.ADTS;

import Domain.Exception.MyException;

public class MyHeap<K,V> extends MyDict<K,V> implements IHeap<K,V>{
    private int lastAddress = 1; //default RefValues point to 0


    @Override
    public int getNewAddress() {
        return lastAddress++;
    }
}

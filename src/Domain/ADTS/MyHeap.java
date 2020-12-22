package Domain.ADTS;

public class MyHeap<K,V> extends MyDictionary<K,V> implements IHeap<K,V>{
    private int lastAddress = 1; //default RefValues point to 0


    @Override
    public int getNewAddress() {
        return lastAddress++;
    }
}

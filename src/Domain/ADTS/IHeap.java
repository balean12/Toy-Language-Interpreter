package Domain.ADTS;

public interface IHeap<K, V> extends IDictionary<K,V> {
    int getNewAddress();
}

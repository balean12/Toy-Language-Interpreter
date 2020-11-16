package Domain.ADTS;

import Domain.Exception.ADTException;
import Domain.Exception.MyException;

import java.util.HashMap;

public class MyDict<T1,T2> implements IDictionary<T1,T2> {
    private final HashMap<T1,T2> dictionary;
    public MyDict(){
        dictionary = new HashMap<T1,T2>();
    }
    public void add(T1 key, T2 value) throws MyException {
        if(this.dictionary.containsKey(key)){
            throw new ADTException("Duplicate key in Dictionary!\n");
        }
        dictionary.put(key, value);
    }
    public void update(T1 key, T2 value) throws MyException{
        if(!dictionary.containsKey(key)){
            throw new ADTException("Key non-existent to be updated! \n");
        }
        dictionary.replace(key, value);
    }
    public T2 lookup(T1 key) throws MyException{
        if(!dictionary.containsKey(key)){
            throw new ADTException("Key non-existent!\n");
        }
        return dictionary.get(key);
    }
    public boolean isDefined(T1 id){
        return dictionary.containsKey(id);
    }
    public String toString(){
        StringBuilder stringOut = new StringBuilder();
        for(T1 key : this.dictionary.keySet()){
            stringOut.append(key);
            stringOut.append("->>");
            stringOut.append(this.dictionary.get(key).toString());
            stringOut.append("; ");
        }
        return stringOut.toString();
    }
}

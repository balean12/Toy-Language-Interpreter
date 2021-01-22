package Domain.ADTS;

import Domain.Exception.ADTException;
import Domain.Exception.MyException;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<T1,T2> implements IDictionary<T1,T2> {
    private HashMap<T1,T2> dictionary;
    public MyDictionary(){
        dictionary = new HashMap<>();
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

    @Override
    public Map<T1, T2> getContent() {
        return dictionary;
    }

    @Override
    public void setContent(Map<T1, T2> content) {
        dictionary = (HashMap<T1, T2>) content;
    }

    @Override
    public IDictionary<T1, T2> deepCopy() throws MyException {
        IDictionary<T1, T2> newDictionary = new MyDictionary<>();
        for(T1 key: this.dictionary.keySet())
            newDictionary.add(key, this.dictionary.get(key));
        return newDictionary;
    }
}

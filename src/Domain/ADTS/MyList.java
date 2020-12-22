package Domain.ADTS;

import Domain.Exception.ADTException;
import Domain.Exception.MyException;

import java.util.ArrayList;

public class MyList<T>  implements IList<T>{
    java.util.ArrayList<T> list;
    public MyList(){
        list = new ArrayList<T>();
    }
    public T pop() throws MyException {
        if(list.isEmpty()){
            throw new ADTException("List is empty! \n");
        }
        return list.get(list.size()-1);
    }
    public void add(T value){
        list.add(value);
    }
    public boolean isEmpty(){
        return list.isEmpty();
    }

    @Override
    public T getValue(int index) {
        return list.get(index);
    }

    @Override
    public int getSize() {
        return list.size();
    }

    public String toString(){
        StringBuilder stringOut = new StringBuilder();
        for(T element:list){
            stringOut.append(element);
            stringOut.append(", ");
        }
        return stringOut.toString();
    }
}

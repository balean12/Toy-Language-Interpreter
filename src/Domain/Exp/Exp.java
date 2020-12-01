package Domain.Exp;

import Domain.ADTS.IDictionary;
import Domain.ADTS.IHeap;
import Domain.Exception.MyException;
import Domain.Value.Value;

public interface Exp {
    Value eval(IDictionary<String,Value> tbl, IHeap<Integer, Value> heap) throws MyException;
    String toString();
    Exp deepCopy();
}


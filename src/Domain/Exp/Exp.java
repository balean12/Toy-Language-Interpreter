package Domain.Exp;

import Domain.ADTS.IDictionary;
import Domain.Exception.MyException;
import Domain.Value.Value;

public interface Exp {
    Value eval(IDictionary<String,Value> tbl) throws MyException;
    String toString();
    Exp deepCopy();
}


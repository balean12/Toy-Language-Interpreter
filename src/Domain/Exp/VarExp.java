package Domain.Exp;

import Domain.ADTS.IDictionary;
import Domain.Exception.MyException;
import Domain.Value.Value;

public class VarExp implements Exp {
    String id;

    public VarExp(String value) {
        id = value;
    }

    public Value eval(IDictionary<String, Value> symbolTable) throws MyException {
        return symbolTable.lookup(id);
    }

    public String toString() {
        return this.id;
    }

    public Exp deepCopy() {
        return new VarExp(this.id);
    }
}

package Domain.Exp;

import Domain.ADTS.IDictionary;
import Domain.ADTS.IHeap;
import Domain.Exception.MyException;
import Domain.Value.IntValue;
import Domain.Value.Value;

public class ValueExp implements Exp{
    Value value;

    public ValueExp(Value v) {this.value = v;}

    public Value getValue() {return value;}
    public void setValue(Value newValue) {this.value = newValue;}

    @Override
    public Value eval(IDictionary<String, Value> tbl, IHeap<Integer, Value> heap) throws MyException {
        return value;
    }

    public String toString(){
        return this.value.toString();
    }
    public Exp deepCopy() {return new ValueExp(this.value.deepCopy());}
}

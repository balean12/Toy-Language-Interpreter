package Domain.Exp;

import Domain.ADTS.IDictionary;
import Domain.ADTS.IHeap;
import Domain.Exception.MyException;
import Domain.Types.IType;
import Domain.Value.IValue;

public class ValueExpression implements IExpression {
    IValue value;

    public ValueExpression(IValue v) {this.value = v;}

    public IValue getValue() {return value;}
    public void setValue(IValue newValue) {this.value = newValue;}

    @Override
    public IValue eval(IDictionary<String, IValue> tbl, IHeap<Integer, IValue> heap) throws MyException {
        return value;
    }

    @Override
    public IType typeCheck(IDictionary<String, IType> typeEnvironment) throws MyException {
        return this.value.getType();
    }

    public String toString(){
        return this.value.toString();
    }
    public IExpression deepCopy() {return new ValueExpression(this.value.deepCopy());}
}

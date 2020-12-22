package Domain.Exp;

import Domain.ADTS.IDictionary;
import Domain.ADTS.IHeap;
import Domain.Exception.MyException;
import Domain.Types.IType;
import Domain.Value.IValue;

public class VariableExpression implements IExpression {
    String id;

    public VariableExpression(String value) {
        id = value;
    }

    @Override
    public IValue eval(IDictionary<String, IValue> symbolTable, IHeap<Integer, IValue> heap) throws MyException {
        return symbolTable.lookup(id);
    }

    @Override
    public IType typeCheck(IDictionary<String, IType> typeEnvironment) throws MyException {
        return typeEnvironment.lookup(id);
    }

    public String toString() {
        return this.id;
    }

    public IExpression deepCopy() {
        return new VariableExpression(this.id);
    }
}

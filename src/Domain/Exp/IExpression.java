package Domain.Exp;

import Domain.ADTS.IDictionary;
import Domain.ADTS.IHeap;
import Domain.Exception.MyException;
import Domain.Types.IType;
import Domain.Value.IValue;

public interface IExpression {
    IValue eval(IDictionary<String, IValue> tbl, IHeap<Integer, IValue> heap) throws MyException;
    IType typeCheck(IDictionary<String, IType> typeEnvironment) throws MyException;
    String toString();
    IExpression deepCopy();
}


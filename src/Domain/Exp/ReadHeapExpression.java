package Domain.Exp;

import Domain.ADTS.IDictionary;
import Domain.ADTS.IHeap;
import Domain.Exception.EvaluationException;
import Domain.Exception.MyException;
import Domain.Types.ReferenceType;
import Domain.Types.IType;
import Domain.Value.ReferenceValue;
import Domain.Value.IValue;

public class ReadHeapExpression implements IExpression {
    IExpression expression;

    public ReadHeapExpression(IExpression expression) { this.expression = expression; }

    @Override
    public IValue eval(IDictionary<String, IValue> symbolTable, IHeap<Integer, IValue> heap) throws MyException {
        IValue value = expression.eval(symbolTable, heap);
        if(!(value.getType() instanceof ReferenceType)){
            throw new EvaluationException("Expression is not a ref value!!\n");
        }
        int address = ((ReferenceValue)value).getAddress();
        if(!heap.isDefined(address)){
            throw new EvaluationException("Address is not defined! \n");
        }
        return heap.lookup(address);
    }

    @Override
    public IType typeCheck(IDictionary<String, IType> typeEnvironment) throws MyException {
        IType type = expression.typeCheck(typeEnvironment);
        if(type instanceof ReferenceType){
            ReferenceType refType = (ReferenceType)type;
            return refType.getInner();
        }else
            throw new EvaluationException("The ReadHeap argument is not a RefType! \n");
    }

    @Override
    public IExpression deepCopy() {
        return new ReadHeapExpression(expression.deepCopy());
    }

    public String toString() {
        return "readHeap(" + expression.toString() + ")";
    }
}

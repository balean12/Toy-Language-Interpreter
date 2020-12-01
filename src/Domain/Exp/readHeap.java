package Domain.Exp;

import Domain.ADTS.IDictionary;
import Domain.ADTS.IHeap;
import Domain.Exception.EvaluationException;
import Domain.Exception.MyException;
import Domain.Types.RefType;
import Domain.Value.RefValue;
import Domain.Value.Value;

public class readHeap implements Exp{
    Exp expression;

    public readHeap(Exp expression) { this.expression = expression; }

    @Override
    public Value eval(IDictionary<String, Value> symbolTable, IHeap<Integer, Value> heap) throws MyException {
        Value value = expression.eval(symbolTable, heap);
        if(!(value.getType() instanceof RefType)){
            throw new EvaluationException("Expression is not a ref value!!\n");
        }
        int address = ((RefValue)value).getAddress();
        if(!heap.isDefined(address)){
            throw new EvaluationException("Address is not defined! \n");
        }
        return heap.lookup(address);
    }

    @Override
    public Exp deepCopy() {
        return new readHeap(expression.deepCopy());
    }

    public String toString() {
        return "readHeap(" + expression.toString() + ")";
    }
}

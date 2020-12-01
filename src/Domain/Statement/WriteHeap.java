package Domain.Statement;

import Domain.ADTS.IDictionary;
import Domain.ADTS.IHeap;
import Domain.Exception.EvaluationException;
import Domain.Exception.MyException;
import Domain.Exp.Exp;
import Domain.PrgState;
import Domain.Types.RefType;
import Domain.Value.RefValue;
import Domain.Value.Value;


public class WriteHeap implements IStmt {
    /*
    variableName = heap address
    expression = the new value that is going to be stored into the heap
    */
    String variableName;
    Exp expression;

    public WriteHeap(String variableName, Exp expression) { this.variableName = variableName; this.expression = expression; }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symbolTable = state.getSymTable();
        var heap = state.getHeap();
        if(!symbolTable.isDefined(variableName)){
            throw new EvaluationException("Heap address not defined! \n");
        }
        Value value = symbolTable.lookup(variableName);
        if(!(value.getType() instanceof RefType)){
            throw new EvaluationException("Variable Name type is not ref type! \n");
        }
        int address = ((RefValue)value).getAddress();
        if(!heap.isDefined(address)){
            throw new EvaluationException("Address is not defined! \n");
        }
        Value expressionValue = expression.eval(symbolTable,heap);
        if(!expressionValue.getType().equals(((RefValue)value).getLocationType())){
            throw new EvaluationException("Evaluated expression type and location type do not match! \n");
        }
        heap.update(address, expressionValue);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WriteHeap(variableName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return "writeHeap( " + variableName + "," + expression.toString() + " )";
    }
}

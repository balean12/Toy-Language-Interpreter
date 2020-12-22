package Domain.Statement;

import Domain.ADTS.IDictionary;
import Domain.Exception.EvaluationException;
import Domain.Exception.MyException;
import Domain.Exception.StatementException;
import Domain.Exp.IExpression;
import Domain.ProgramState;
import Domain.Types.ReferenceType;
import Domain.Types.IType;
import Domain.Value.ReferenceValue;
import Domain.Value.IValue;


public class WriteHeapStatement implements IStmt {
    /*
    variableName = heap address
    expression = the new value that is going to be stored into the heap
    */
    String variableName;
    IExpression expression;

    public WriteHeapStatement(String variableName, IExpression expression) { this.variableName = variableName; this.expression = expression; }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var symbolTable = state.getSymTable();
        var heap = state.getHeap();
        if(!symbolTable.isDefined(variableName)){
            throw new EvaluationException("Heap address not defined! \n");
        }
        IValue value = symbolTable.lookup(variableName);
        if(!(value.getType() instanceof ReferenceType)){
            throw new EvaluationException("Variable Name type is not ref type! \n");
        }
        int address = ((ReferenceValue)value).getAddress();
        if(!heap.isDefined(address)){
            throw new EvaluationException("Address is not defined! \n");
        }
        IValue expressionValue = expression.eval(symbolTable,heap);
        if(!expressionValue.getType().equals(((ReferenceValue)value).getLocationType())){
            throw new EvaluationException("Evaluated expression type and location type do not match! \n");
        }
        heap.update(address, expressionValue);
        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws MyException {
        IType typeVariable = typeEnvironment.lookup(variableName);
        IType typeExpression = expression.typeCheck(typeEnvironment);
        if(typeVariable.equals(new ReferenceType(typeExpression)))
            return typeEnvironment;
        else throw new StatementException("WriteHeap: right hand side and left hand side have different types!\n");
    }

    @Override
    public IStmt deepCopy() {
        return new WriteHeapStatement(variableName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return "writeHeap( " + variableName + "," + expression.toString() + " )";
    }
}

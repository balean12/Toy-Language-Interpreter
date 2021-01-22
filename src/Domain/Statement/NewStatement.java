package Domain.Statement;

import Domain.ADTS.IDictionary;
import Domain.Exception.MyException;
import Domain.Exception.StatementException;
import Domain.Exp.IExpression;
import Domain.ProgramState;
import Domain.Types.ReferenceType;
import Domain.Types.IType;
import Domain.Value.ReferenceValue;
import Domain.Value.IValue;

public class NewStatement implements IStmt {
    String variableName;
    IExpression expression;

    public NewStatement(String variableName, IExpression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }
   /* public String getVariableName() {
        return variableName;
    }*/
    public IExpression getExpression(){
        return expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        //var stack = state.getStack();
        var symbolTable = state.getSymTable();
        var heap = state.getHeap();

        if(!symbolTable.isDefined(variableName)){
            throw new StatementException("declared Variable name is undefined!\n");
        }

        IValue variableNameValue = symbolTable.lookup(variableName);
        if(!(variableNameValue.getType() instanceof ReferenceType)){
            throw new StatementException("Invalid type in heap! \n");
        }

        IValue expressionResult = expression.eval(symbolTable, state.getHeap());
        if(!expressionResult.getType().equals(((ReferenceValue)variableNameValue).getLocationType())){
            throw new StatementException("Type of expression and variableName type do not match! \n ");
        }
        int newAddress = heap.getNewAddress();
        heap.add(newAddress, expressionResult);
        symbolTable.update(variableName, new ReferenceValue(newAddress, ((ReferenceValue)variableNameValue).getLocationType()));
        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws MyException {
        IType typeVariable = typeEnvironment.lookup(variableName);
        IType typeExpression = expression.typeCheck(typeEnvironment);
        if(typeVariable.equals(new ReferenceType(typeExpression)))
            return typeEnvironment;
        else
            throw new StatementException("NEW stmt: right hand side and left hand side have different types! \n");
    }

    @Override
    public IStmt deepCopy() {
        return new NewStatement(variableName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return "new(" + variableName + "," + expression.toString() + ")";
    }
}

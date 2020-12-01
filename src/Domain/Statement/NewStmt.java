package Domain.Statement;

import Domain.ADTS.IHeap;
import Domain.ADTS.IStack;
import Domain.Exception.MyException;
import Domain.Exception.StatementException;
import Domain.Exp.Exp;
import Domain.PrgState;
import Domain.Types.RefType;
import Domain.Types.Type;
import Domain.Value.RefValue;
import Domain.Value.Value;

import java.sql.Ref;

public class NewStmt implements IStmt {
    String variableName;
    Exp expression;

    public NewStmt(String variableName, Exp expression) {
        this.variableName = variableName;
        this.expression = expression;
    }
    public String getVariableName() {
        return variableName;
    }
    public Exp getExpression(){
        return expression;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        var stack = state.getStack();
        var symbolTable = state.getSymTable();
        var heap = state.getHeap();

        if(!symbolTable.isDefined(variableName)){
            throw new StatementException("declared Variable name is undefined!\n");
        }

        Value variableNameValue = symbolTable.lookup(variableName);
        if(!(variableNameValue.getType() instanceof RefType)){
            throw new StatementException("Invalid type in heap! \n");
        }

        Value expressionResult = expression.eval(symbolTable, state.getHeap());
        if(!expressionResult.getType().equals(((RefValue)variableNameValue).getLocationType())){
            throw new StatementException("Type of expression and variableName type do not match! \n ");
        }
        int newAddress = heap.getNewAddress();
        heap.add(newAddress, expressionResult);
        symbolTable.update(variableName, new RefValue(newAddress, ((RefValue)variableNameValue).getLocationType()));
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NewStmt(variableName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return "new(" + variableName + "," + expression.toString() + ")";
    }
}

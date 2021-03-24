package Domain.Statement;

import Domain.ADTS.ICountSemaphore;
import Domain.ADTS.IDictionary;
import Domain.Exception.MyException;
import Domain.Exception.StatementException;
import Domain.Exp.IExpression;
import Domain.ProgramState;
import Domain.Types.IType;
import Domain.Types.IntegerType;
import Domain.Value.IValue;
import Domain.Value.IntValue;
import com.sun.jdi.IntegerValue;
import javafx.util.Pair;

import java.util.ArrayList;

public class CreateSemaphoreStatement implements IStmt{
    private final String variableName;
    private final IExpression expression;

    public CreateSemaphoreStatement(String variableName, IExpression expression){
        this.variableName = variableName;
        this.expression = expression;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IValue number1 = this.expression.eval(state.getSymTable(), state.getHeap());
        IDictionary<String, IValue> symbolTable = state.getSymTable();
        Integer number1Value= ((IntValue)number1).getValue();

        if(!number1.getType().equals(new IntegerType())){
            throw new StatementException("SemaphoreError: Expression value is not an int!/n");
        }

        ICountSemaphore<Integer, Pair<Integer, ArrayList<Integer>>> semaphoreTable = state.getSemaphoreTable();
        int index = semaphoreTable.createIndex();
        semaphoreTable.add(index, new Pair<>(number1Value, new ArrayList<>()));
        if(symbolTable.isDefined(this.variableName) && symbolTable.lookup(this.variableName).getType().equals(new IntegerType())){
            symbolTable.update(variableName,new IntValue(index));
        }else{
            throw new StatementException("Error: Variable not found or not an int! \n");
        }

        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws MyException {
        IType typeExpression = this.expression.typeCheck(typeEnvironment);
        IType typeVar = typeEnvironment.lookup(this.variableName);
        if(typeExpression.equals(new IntegerType()) && typeExpression.equals(typeVar)){
            return typeEnvironment;
        }
        else throw new StatementException("Error: Create Semaphore Stmt types do not match!\n");
    }

    @Override
    public IStmt deepCopy() {
        return new CreateSemaphoreStatement(this.variableName, this.expression.deepCopy());
    }

    @Override
    public String toString() {
        return "CreateSemaphore(" + this.variableName +", "+ this.expression.toString() +
                ")";
    }
}

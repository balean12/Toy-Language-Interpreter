package Domain.Statement;

import Domain.ADTS.ICountSemaphore;
import Domain.ADTS.IDictionary;
import Domain.Exception.MyException;
import Domain.Exception.StatementException;
import Domain.ProgramState;
import Domain.Types.IType;
import Domain.Types.IntegerType;
import Domain.Value.IValue;
import Domain.Value.IntValue;
import javafx.util.Pair;

import java.util.ArrayList;

public class Release implements IStmt{
    private String variableName;
    public Release(String variableName){this.variableName = variableName;}
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IDictionary<String,IValue> symbolTable = state.getSymTable();


        if(!symbolTable.isDefined(variableName))
            throw new StatementException("Acquire ERROR: Variable" + this.variableName + "not defined!\n");

        IValue foundIndex = symbolTable.lookup(this.variableName);
        if(!foundIndex.getType().equals(new IntegerType()))
            throw new StatementException("Acquire ERROR: Variable " + this.variableName + "not int!\n");

        ICountSemaphore<Integer, Pair<Integer, ArrayList<Integer>>> semaphoreTable = state.getSemaphoreTable();
        int foundIndexInteger = ((IntValue)foundIndex).getValue();
        if(!semaphoreTable.isDefined(foundIndexInteger)){
            throw new StatementException("Release ERROR: Index " + foundIndexInteger + "not defined! \n");
        }
        Pair<Integer, ArrayList<Integer>> pair = semaphoreTable.lookup(foundIndexInteger);
        if(pair.getValue().contains(state.getId()))
            pair.getValue().remove((Integer)state.getId());
        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws MyException {
        IType typeVar = typeEnvironment.lookup(this.variableName);
        if(!typeVar.equals(new IntegerType()))
            throw new StatementException("Release ERROR: Variable " + this.variableName +"is not int!\n");
        return typeEnvironment;
    }

    @Override
    public IStmt deepCopy() {
        return new Release(this.variableName);
    }

    @Override
    public String toString() {
        return "Release(" + this.variableName +")";
    }
}

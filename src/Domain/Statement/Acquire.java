package Domain.Statement;

import Domain.ADTS.ICountSemaphore;
import Domain.ADTS.IDictionary;
import Domain.ADTS.IStack;
import Domain.Exception.MyException;
import Domain.Exception.StatementException;
import Domain.ProgramState;
import Domain.Types.IType;
import Domain.Types.IntegerType;
import Domain.Value.IValue;
import Domain.Value.IntValue;
import javafx.util.Pair;

import java.util.ArrayList;

public class Acquire implements IStmt{
    private String variableName;

    public Acquire(String variableName){this.variableName = variableName;}

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IStack<IStmt> executionStack = state.getStack();
        IDictionary<String, IValue> symbolTable = state.getSymTable();
        ICountSemaphore<Integer, Pair<Integer, ArrayList<Integer>>> semaphoreTable = state.getSemaphoreTable();

        if(!symbolTable.isDefined(variableName))
            throw new StatementException("Acquire ERROR: Variable " + this.variableName + " not defined!\n");

        IValue foundIndex = symbolTable.lookup(this.variableName);
        if(!foundIndex.getType().equals(new IntegerType())){
            throw new StatementException("Acquire ERROR: Variable " + this.variableName+ " not int!\n");
        }

        int foundIndexInteger = ((IntValue)foundIndex).getValue();
        if(!semaphoreTable.isDefined(foundIndexInteger))
            throw new StatementException("Acquire ERROR: Index not defined! \n");
        Pair<Integer, ArrayList<Integer>> pair = semaphoreTable.lookup(foundIndexInteger);
        int length =pair.getValue().size();
        if(pair.getKey() > length){
            if(!pair.getValue().contains(state.getId())){
                pair.getValue().add(state.getId());
            }
        }else {
            executionStack.push(this.deepCopy());
        }
        return null;

    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws MyException {
        IType typeVar = typeEnvironment.lookup(this.variableName);
        if(!typeVar.equals(new IntegerType()))
            throw new StatementException("Acquire ERROR: Variable is not int!\n");
        return typeEnvironment;
    }

    @Override
    public IStmt deepCopy() {
        return new Acquire(this.variableName);
    }

    @Override
    public String toString() {
        return "Acquire(" + this.variableName +")";
    }
}

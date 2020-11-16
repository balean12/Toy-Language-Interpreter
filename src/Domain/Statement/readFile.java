package Domain.Statement;

import Domain.ADTS.IDictionary;
import Domain.Exception.MyException;
import Domain.Exception.StatementException;
import Domain.Exp.Exp;
import Domain.PrgState;
import Domain.Types.IntType;
import Domain.Types.StringType;
import Domain.Value.IntValue;
import Domain.Value.StringValue;
import Domain.Value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class readFile implements IStmt{
    String variableName;
    Exp expression;

    public readFile(Exp expression,String variableName){
        this.expression = expression;
        this.variableName = variableName;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDictionary<String, Value> symbolTable = state.getSymTable();
        Value value = expression.eval(symbolTable);
        if(!symbolTable.isDefined(variableName))
            throw new StatementException("Variable not defined! \n");
        if(!symbolTable.lookup(variableName).getType().equals(new IntType())){
            throw  new StatementException("Invalid type! \n");
        }
        if(!value.getType().equals(new StringType())){
            throw new StatementException("Invalid type! \n");
        }
        IDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        try
            {
            BufferedReader reader = fileTable.lookup((StringValue)value);
            String line = reader.readLine();
            Value zeroValue;
            if(line == null){
                zeroValue = new IntValue(0);
            }
            else
                {
                    zeroValue = new IntValue(Integer.parseInt(line));
                }
            symbolTable.update(variableName, zeroValue);
        }
        catch (IOException exception){
            throw new StatementException("Value not read! \n");
        }
        catch (MyException exception) {
            throw new StatementException("File name invalid! \n");
        }

        return state;
    }
//    public String toString(){
//        return "Read file: " + expression.toString() + " | " + "Variable Name: " + variableName;
//    }

    @Override
    public String toString() {
        return "readFile{" +
                "variableName='" + variableName + '\'' +
                ", expression=" + expression +
                '}';
    }

    @Override
    public IStmt deepCopy() {
        return new readFile(this.expression.deepCopy(), this.variableName);
    }
}

package Domain.Statement;

import Domain.ADTS.IDictionary;
import Domain.Exception.MyException;
import Domain.Exception.StatementException;
import Domain.Exp.Exp;
import Domain.PrgState;
import Domain.Types.StringType;
import Domain.Value.StringValue;
import Domain.Value.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class closeRFile implements IStmt{
    Exp expression;
    public closeRFile(Exp expression) { this.expression = expression;}
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDictionary<String, Value> symbolTable = state.getSymTable();
        Value value = expression.eval(symbolTable);
        if(!value.getType().equals(new StringType())){
            throw new StatementException("Types do not match! \n");
        }
        try{
            FileReader fileReader = new FileReader(((StringValue)value).getValue());
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            bufferedReader.close();
        }
        catch (IOException exp){
            throw new StatementException("Could not open file! \n");
        }
        return state;
    }

    @Override
    public String toString() {
        return "closeRFile{" +
                "expression=" + expression +
                '}';
    }
    @Override
    public IStmt deepCopy() {
        return new closeRFile(expression.deepCopy());
    }
}

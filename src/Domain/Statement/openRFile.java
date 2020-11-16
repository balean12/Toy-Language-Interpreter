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

public class openRFile implements IStmt{
    private final Exp expression;

    public openRFile(Exp exp) {this.expression = exp;}
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDictionary<String, Value> symbolTable = state.getSymTable();
        IDictionary<StringValue, BufferedReader> fileTable =state.getFileTable();
        Value value = expression.eval(symbolTable);
        if(!value.getType().equals(new StringType())){
            throw new StatementException("Type doesn't match!\n");
        }
        if(fileTable.isDefined((StringValue)value)){
            throw new StatementException("File already exists!\n");
        }
        try {
            FileReader fileReader = new FileReader(((StringValue) value).getValue());
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            fileTable.add((StringValue)value, bufferedReader);
        }
        catch (IOException ioException){
            throw new StatementException("Couldn't open file or file is missing! \n");
        }
        return state;
    }

    @Override
    public String toString() {
        return "openRFile{" +
                "expression=" + expression +
                '}';
    }

    @Override
    public IStmt deepCopy() {
        return new openRFile(this.expression.deepCopy());
    }
}

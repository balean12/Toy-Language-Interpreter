package Domain.Statement;

import Domain.ADTS.IDictionary;
import Domain.Exception.MyException;
import Domain.Exception.StatementException;
import Domain.Exp.IExpression;
import Domain.ProgramState;
import Domain.Types.StringType;
import Domain.Types.IType;
import Domain.Value.StringValue;
import Domain.Value.IValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFileStatement implements IStmt{
    private final IExpression expression;

    public OpenRFileStatement(IExpression exp) {this.expression = exp;}
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IDictionary<String, IValue> symbolTable = state.getSymTable();
        IDictionary<StringValue, BufferedReader> fileTable =state.getFileTable();
        IValue value = expression.eval(symbolTable, state.getHeap());
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
        return null;
    }
    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws MyException {
        IType typeExpression = expression.typeCheck(typeEnvironment);
        if(typeExpression.equals(new StringType()))
            return typeEnvironment;
        else throw new MyException("Close file statement type is not a string! \n");
    }
    @Override
    public String toString() {
        return "openRFile{" +
                "expression=" + expression +
                '}';
    }

    @Override
    public IStmt deepCopy() {
        return new OpenRFileStatement(this.expression.deepCopy());
    }
}

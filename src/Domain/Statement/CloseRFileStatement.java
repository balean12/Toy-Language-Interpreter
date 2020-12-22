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

public class CloseRFileStatement implements IStmt{
    IExpression expression;
    public CloseRFileStatement(IExpression expression) { this.expression = expression;}
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IDictionary<String, IValue> symbolTable = state.getSymTable();
        IValue value = expression.eval(symbolTable, state.getHeap());
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
        return "closeRFile{" +
                "expression=" + expression +
                '}';
    }
    @Override
    public IStmt deepCopy() {
        return new CloseRFileStatement(expression.deepCopy());
    }
}

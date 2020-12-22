package Domain.Statement;

import Domain.ADTS.IDictionary;
import Domain.Exception.MyException;
import Domain.Exception.StatementException;
import Domain.Exp.IExpression;
import Domain.ProgramState;
import Domain.Types.IntegerType;
import Domain.Types.StringType;
import Domain.Types.IType;
import Domain.Value.IntValue;
import Domain.Value.StringValue;
import Domain.Value.IValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements IStmt{
    String variableName;
    IExpression expression;

    public ReadFileStatement(IExpression expression, String variableName){
        this.expression = expression;
        this.variableName = variableName;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IDictionary<String, IValue> symbolTable = state.getSymTable();
        IValue value = expression.eval(symbolTable, state.getHeap());
        if(!symbolTable.isDefined(variableName))
            throw new StatementException("Variable not defined! \n");
        if(!symbolTable.lookup(variableName).getType().equals(new IntegerType())){
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
            IValue zeroValue;
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
        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws MyException {
        IType typeVariable = typeEnvironment.lookup(variableName);
        IType typeExpression = expression.typeCheck(typeEnvironment);
        if(!typeExpression.equals(new StringType()))
            throw new StatementException("ERROR: ReadFile requires a string as expression parameter! \n");
        if(!typeVariable.equals(new IntegerType()))
            throw new StatementException("ERROR: ReadFile requires an int as variable parameter! \n");
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "readFile{" +
                "variableName='" + variableName + '\'' +
                ", expression=" + expression +
                '}';
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFileStatement(this.expression.deepCopy(), this.variableName);
    }
}

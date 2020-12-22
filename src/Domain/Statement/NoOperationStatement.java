package Domain.Statement;

import Domain.ADTS.IDictionary;
import Domain.Exception.MyException;
import Domain.ProgramState;
import Domain.Types.IType;

public class NoOperationStatement implements IStmt{
    public NoOperationStatement(){};
    public ProgramState execute(ProgramState state) throws MyException {
        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws MyException {
        return typeEnvironment;
    }

    public IStmt deepCopy() {return new NoOperationStatement();}
}

package Domain.Statement;

import Domain.ADTS.IDictionary;
import Domain.Exception.MyException;
import Domain.ProgramState;
import Domain.Types.IType;

public interface IStmt {
    ProgramState execute(ProgramState state) throws MyException;
    IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws MyException;
    IStmt deepCopy();
}

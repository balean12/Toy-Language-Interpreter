package Domain.Statement;

import Domain.Exception.MyException;
import Domain.PrgState;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException;
    IStmt deepCopy();
}

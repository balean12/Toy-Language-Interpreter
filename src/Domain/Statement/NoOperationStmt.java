package Domain.Statement;

import Domain.Exception.MyException;
import Domain.PrgState;

public class NoOperationStmt implements IStmt{
    public NoOperationStmt(){};
    public PrgState execute(PrgState state) throws MyException {
        return state;
    }
    public IStmt deepCopy() {return new NoOperationStmt();}
}

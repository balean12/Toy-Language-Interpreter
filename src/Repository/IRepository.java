package Repository;

import Domain.Exception.MyException;
import Domain.PrgState;

import java.io.IOException;

public interface IRepository {
    void addPrg(PrgState newPrg);
    void logProgramStateExecution() throws MyException, IOException;
    PrgState getCrtPrg();
}

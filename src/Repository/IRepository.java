package Repository;

import Domain.ADTS.IList;
import Domain.Exception.MyException;
import Domain.PrgState;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    void logProgramStateExecution(PrgState state) throws MyException, IOException;
    void addProgramState(PrgState program);
    List<PrgState> getAllPrograms();
    void setProgramStateList(List<PrgState> newProgramStateList);
}

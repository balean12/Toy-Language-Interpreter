package Repository;

import Domain.Exception.MyException;
import Domain.ProgramState;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    void logProgramStateExecution(ProgramState state) throws MyException, IOException;
    void addProgramState(ProgramState program);
    List<ProgramState> getAllPrograms();
    void setProgramStateList(List<ProgramState> newProgramStateList);
    void clearLogFile() throws MyException;
}

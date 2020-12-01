package Repository;

import Domain.ADTS.IList;
import Domain.ADTS.MyList;
import Domain.Exception.MyException;
import Domain.PrgState;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{
    List<PrgState> myProgramStates;
    String logFilePath;

    public Repository(String logFilePath){
        this.myProgramStates = new ArrayList<PrgState>();
        this.logFilePath = logFilePath;
    }

    @Override
    public void addProgramState(PrgState program) {
        this.myProgramStates.add(program);
    }

    @Override
    public List<PrgState> getAllPrograms() {
        return this.myProgramStates;
    }

    @Override
    public void setProgramStateList(List<PrgState> newProgramStateList) {
            this.myProgramStates = newProgramStateList;
    }

    public void logProgramStateExecution(PrgState state) throws MyException, IOException {
        PrintWriter logFile;
        logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.println(state.toString());
        logFile.close();
    }

}

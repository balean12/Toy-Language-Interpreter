package Repository;

import Domain.Exception.MyException;
import Domain.ProgramState;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{
    List<ProgramState> myProgramStates;
    String logFilePath;

    public Repository(String logFilePath){
        this.myProgramStates = new ArrayList<>();
        this.logFilePath = logFilePath;
    }

    @Override
    public void addProgramState(ProgramState program) {
        this.myProgramStates.add(program);
    }

    @Override
    public List<ProgramState> getAllPrograms() {
        return this.myProgramStates;
    }

    @Override
    public void setProgramStateList(List<ProgramState> newProgramStateList) {
            this.myProgramStates = newProgramStateList;
    }

    @Override
    public void clearLogFile() throws MyException {
        try {
            PrintWriter printWriter = new PrintWriter(this.logFilePath);
            printWriter.close();
        } catch (IOException e) {
            throw new MyException(e.getMessage());
        }
    }

    public void logProgramStateExecution(ProgramState state) throws IOException {
        PrintWriter logFile;
        logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.println(state.toString());
        logFile.close();
    }

}

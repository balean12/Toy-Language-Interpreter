package Repository;

import Domain.ADTS.IDictionary;
import Domain.ADTS.IList;
import Domain.ADTS.IStack;
import Domain.Exception.MyException;
import Domain.Exp.ValueExp;
import Domain.PrgState;
import Domain.Statement.IStmt;
import Domain.Value.StringValue;
import Domain.Value.Value;

import java.io.*;
import java.util.ArrayList;

public class Repository implements IRepository{
    //ArrayList<PrgState> myPrgStates;
    PrgState program;
    String logFilePath;
    //boolean firstCall;

    public Repository(PrgState program, String logFilePath){
        //myPrgStates = new ArrayList<PrgState>();
        this.program = program;
        this.logFilePath = logFilePath;
    }
    public  PrgState getCrtPrg() { return program;}
    public void logProgramStateExecution() throws MyException, IOException {
        PrintWriter logFile;
        logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.println(this.program.toString());
        logFile.close();
    }

    @Override
    public void addPrg(PrgState newPrg) {
        //myPrgStates.add(newPrg);
    }
}

package Controller;

import Domain.ADTS.IStack;
import Domain.Exception.MyException;
import Domain.Exception.StatementException;
import Domain.PrgState;
import Domain.Statement.IStmt;
import Repository.IRepository;

import java.io.Console;
import java.io.IOException;

public class Controller {
    IRepository repository;
    StringBuilder stringAllSteps;
    public Controller(IRepository repo){
        repository = repo;
        stringAllSteps = new StringBuilder();
    }
    public void addProgram(PrgState programState){
        this.repository.addPrg(programState);
    }

    PrgState oneStep(PrgState state) throws MyException{
        IStack<IStmt> exeStack = state.getStack();
        if(exeStack.isEmpty()) throw new StatementException("Exe Stack is empty");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(state);
        }

        public void allStep() throws MyException, IOException {
            PrgState programState= repository.getCrtPrg();
            repository.logProgramStateExecution();
            while(!programState.getStack().isEmpty()){
                PrgState newPorgramState = this.oneStep(programState);
                this.addStepToOutput(newPorgramState);
                repository.logProgramStateExecution();
            }
    }
    public String getOutput() throws MyException{
        return "Output [" + this.repository.getCrtPrg().getOut().toString()+ "]\n";
    }
    public String getAllSteps(){
        return this.stringAllSteps.toString();
    }
    public void addStepToOutput(PrgState currentProgramState){
        this.stringAllSteps.append("---Next Step---\n");
        this.stringAllSteps.append(currentProgramState.toString());
    }
}

package Controller;

import Domain.ADTS.IDictionary;
import Domain.ADTS.MyDictionary;
import Domain.Exception.MyException;

import Domain.ProgramState;

import Domain.Types.IType;
import Domain.Value.ReferenceValue;
import Domain.Value.IValue;
import Repository.IRepository;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Controller {
    IRepository repository;
    StringBuilder stringAllSteps;
    ExecutorService executor;
    public Controller(IRepository repo){
        repository = repo;
        stringAllSteps = new StringBuilder();
    }

    public IRepository getRepository() {
        return this.repository;
    }

    public void oneStepForAllProgram(List<ProgramState> programList) {
        //before the execution, print the ProgramStateList into the log file
        programList.forEach(program -> {
            try {
                repository.logProgramStateExecution(program);
            } catch (MyException | IOException exception) {
                exception.printStackTrace();
            }
        });



        //prepare the list of callables
        List<Callable<ProgramState>> callList = programList.stream()
                .map((ProgramState program) -> (Callable<ProgramState>) (program::oneStep))
                .collect(Collectors.toList());

        //start the execution of the callables
        //it returns the list of new created ProgramStates(namely threads)
        try {
            List<ProgramState> newProgramList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception exception) {
                            System.out.println(exception.getMessage());
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            //add the new created threads to the list of existing threads
            programList.addAll(newProgramList);
            //after the execution, print the PrgState List into the log file
            programList.forEach(program -> {
                try {
                    repository.logProgramStateExecution(program);
                } catch (MyException | IOException exception) {
                    exception.printStackTrace();
                }
            });
            //Save the current programs in the repository
            repository.setProgramStateList(programList);
        } catch (InterruptedException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void typeCheck() throws MyException {
        for(ProgramState state: repository.getAllPrograms()){
            IDictionary<String, IType> typeEnvironment = new MyDictionary<>();
            state.getStack().peek().typeCheck(typeEnvironment);
        }
    }

    public void allSteps() throws MyException {
        this.typeCheck();
        this.executor = Executors.newFixedThreadPool(2);
        //remove the completed programs
        List<ProgramState> programList = removeCompletedProgram(repository.getAllPrograms());
        while(programList.size() > 0) {
            ProgramState state = programList.get(0);

            state.getHeap().setContent(
                    this.garbageCollector(
                            getSymbolTableAddresses(programList.stream().map(programState ->programState.getSymTable().getContent().values()).collect(Collectors.toList())),
                            getHeapAddresses(state.getHeap().getContent()),
                            state.getHeap().getContent())
                    );
            oneStepForAllProgram(programList);
            //remove the completed programs
            programList = removeCompletedProgram(repository.getAllPrograms());
        }
        executor.shutdownNow();
        //HERE the repository still contains at least one Completed Prg
        // and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method
        // setPrgList of repository in order to change the repository

        //update the repository state
        repository.setProgramStateList(programList);
    }

    public List<ProgramState> removeCompletedProgram(List<ProgramState> inputProgramList){
        return inputProgramList.stream()
                .filter(ProgramState::isNotComplete)
                .collect(Collectors.toList());
    }

    public Map<Integer, IValue> garbageCollector(List<Integer> symbolTableAddresses, List<Integer> heapAddresses, Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(e -> symbolTableAddresses.contains(e.getKey()) || heapAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
    }

    private List<Integer> getSymbolTableAddresses(List<Collection<IValue>> symbolTableValuesList){
        List<Integer> addresses = new ArrayList<>();
        symbolTableValuesList.forEach(symbolTable -> symbolTable.stream()
                .filter(variable -> variable instanceof ReferenceValue)
                .map(variable -> ((ReferenceValue)variable).getAddress())
                .forEach(addresses::add));

        return addresses;

    }

    private List<Integer> getHeapAddresses(Map<Integer, IValue> heap){
        return heap.values().stream()
                .filter(v -> v instanceof ReferenceValue)
                .map(v -> ((ReferenceValue)v).getAddress())
                .collect(Collectors.toList());
    }

    public void addProgram(ProgramState program){
        this.repository.addProgramState(program);
    }

  /*  public String getAllSteps(){
        return this.stringAllSteps.toString();
    }*/

    public void runOneStepGUI() throws MyException{
        List<ProgramState> programList = removeCompletedProgram(repository.getAllPrograms());
        if(programList.size() > 0) {
            //ProgramState state = programList.get(0);
            repository.getAllPrograms().forEach(currentProgram -> currentProgram.getHeap().setContent(
                    this.garbageCollector(
                            getSymbolTableAddresses(programList.stream().map(programState ->programState.getSymTable().getContent().values()).collect(Collectors.toList())),
                            getHeapAddresses(currentProgram.getHeap().getContent()),
                            currentProgram.getHeap().getContent())));
            oneStepForAllProgram(programList);
        }
        else {
            executor.shutdownNow();
            throw new MyException("Program is finished, empty list!\n");
        }
        //HERE the repository still contains at least one Completed Prg
        // and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method
        // setPrgList of repository in order to change the repository
        //update the repository state
        repository.setProgramStateList(programList);
    }

    public void createThreadPool() {
        try{
            this.repository.clearLogFile();
        }
        catch (MyException exception){
            exception.printStackTrace();
        }
        this.executor = Executors.newFixedThreadPool(2);
    }
}

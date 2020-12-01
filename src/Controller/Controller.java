package Controller;

import Domain.Exception.MyException;

import Domain.PrgState;

import Domain.Value.RefValue;
import Domain.Value.Value;
import Repository.IRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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

    public void oneStepForAllProgram(List<PrgState> programList) {
        //before the execution, print the ProgramStateList into the log file
        programList.forEach(program -> {
            try {
                repository.logProgramStateExecution(program);
            } catch (MyException exception) {
                exception.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        //prepare the list of callables
        List<Callable<PrgState>> callList = programList.stream()
                .map((PrgState program) -> (Callable<PrgState>) (program::oneStep))
                .collect(Collectors.toList());

        //start the execution of the callables
        //it returns the list of new created ProgramStates(namely threads)
        try {
            List<PrgState> newProgramList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception exception) {
                            System.out.println(exception.getMessage());
                            return null;
                        }
                    })
                    .filter(program -> program != null)
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

    public void allSteps() throws MyException {
        this.executor = Executors.newFixedThreadPool(2);
        //remove the completed programs
        List<PrgState> programList = removeCompletedProgram(repository.getAllPrograms());
        while(programList.size() > 0) {
            PrgState state = programList.get(0);
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

    public List<PrgState> removeCompletedProgram(List<PrgState> inputProgramList){
        return inputProgramList.stream()
                .filter(PrgState::isNotComplete)
                .collect(Collectors.toList());
    }

    public Map<Integer, Value> garbageCollector(List<Integer> symbolTableAddresses, List<Integer> heapAddresses, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(e -> symbolTableAddresses.contains(e.getKey()) || heapAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
    }

    private List<Integer> getSymbolTableAddresses(List<Collection<Value>> symbolTableValuesList){
        List<Integer> addresses = new ArrayList<Integer>();
        symbolTableValuesList.forEach(symbolTable -> symbolTable.stream()
                .filter(variable -> variable instanceof RefValue)
                .map(variable -> ((RefValue)variable).getAddress())
                .forEach(address -> addresses.add(address)));

        return addresses;

    }

    private List<Integer> getHeapAddresses(Map<Integer, Value> heap){
        return heap.values().stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue)v).getAddress())
                .collect(Collectors.toList());
    }

    public void addProgram(PrgState program){
        this.repository.addProgramState(program);
    }

    public String getAllSteps(){
        return this.stringAllSteps.toString();
    }

    public void addStepToOutput(PrgState currentProgramState){
        this.stringAllSteps.append("---Next Step---\n");
        this.stringAllSteps.append(currentProgramState.toString());
    }
}

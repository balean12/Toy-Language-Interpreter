package Domain;

import Domain.ADTS.*;
import Domain.Exception.MyException;
import Domain.Statement.IStmt;
import Domain.Value.StringValue;
import Domain.Value.IValue;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProgramState {
    IStack<IStmt> exeStack;
    IDictionary<String, IValue> symTable;
    IList<IValue> out;
    IStmt originalProgram;
    IDictionary<StringValue, BufferedReader> fileTable;
    IHeap<Integer, IValue> Heap;
    ICountSemaphore<Integer, Pair<Integer, ArrayList<Integer>>> semaphoreTable;
    private final int programStateID;
    static int lastUsedID=0;

    public IStack<IStmt> getStack(){ return exeStack; }

    public IDictionary<String, IValue> getSymTable(){ return symTable; }

    public IList<IValue> getOut(){ return out; }

    public IDictionary<StringValue, BufferedReader> getFileTable(){return fileTable;}

    //public IStmt getOriginalProgram() {return originalProgram;}

    public IHeap<Integer, IValue> getHeap(){ return Heap; }

    public ICountSemaphore<Integer, Pair<Integer, ArrayList<Integer>>> getSemaphoreTable(){ return semaphoreTable;}

    public ProgramState(IStack<IStmt> stack, IDictionary<String, IValue> symbolTable, IList<IValue> out, IStmt originalProgram,
                        IDictionary<StringValue, BufferedReader> fileTable, IHeap<Integer, IValue> heap,ICountSemaphore<Integer, Pair<Integer, ArrayList<Integer>>> semaphoreTable ){
        exeStack=stack;
        symTable = symbolTable;
        this.out = out;
        this.originalProgram = originalProgram.deepCopy();
        exeStack.push(originalProgram);
        this.fileTable = fileTable;
        this.Heap = heap;
        this.semaphoreTable = semaphoreTable;

        Lock lock = new ReentrantLock();
        lock.lock();
        programStateID = lastUsedID+1;
        lastUsedID+=1;
        lock.unlock();
    }



    public ProgramState oneStep() throws MyException {
        if(exeStack.isEmpty())
            throw new MyException("Program State stack is empty!\n");
        IStmt currentStatement = exeStack.pop();
        return currentStatement.execute(this);
    }

    public boolean isNotComplete(){
        return !this.exeStack.isEmpty();
    }
    //int a; int b;
    public String toString(){
        return  "Program State ID is: " + this.programStateID + "\n\n"
                + "ExeStack is: " + exeStack.toString() + "\n\n"
                + "SymTable is: " + symTable.toString() + "\n\n"
                + "File table: " + this.fileTable.toString() + "\n\n"
                + "Heap: " + Heap.toString() + "\n\n"
                + "Semaphore table: " + this.semaphoreTable.toString() + "\n\n"
                + "Output: " + out.toString() + "\n\n";
    }

    public int getId() {
        return this.programStateID;
    }
}

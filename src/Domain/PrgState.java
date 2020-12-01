package Domain;

import Domain.ADTS.IDictionary;
import Domain.ADTS.IHeap;
import Domain.ADTS.IList;
import Domain.ADTS.IStack;
import Domain.Exception.MyException;
import Domain.Statement.IStmt;
import Domain.Value.StringValue;
import Domain.Value.Value;

import java.io.BufferedReader;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrgState {
    IStack<IStmt> exeStack;
    IDictionary<String, Value> symTable;
    IList<Value> out;
    IStmt originalProgram;
    IDictionary<StringValue, BufferedReader> fileTable;
    IHeap<Integer, Value> Heap;
    private final int programStateID;
    static int lastUsedID=0;

    public IStack<IStmt> getStack(){ return exeStack; }

    public IDictionary<String, Value> getSymTable(){ return symTable; }

    public IList<Value> getOut(){ return out; }

    public IDictionary<StringValue, BufferedReader> getFileTable(){return fileTable;}

    public IStmt getOriginalProgram() {return originalProgram;}

    public IHeap<Integer, Value> getHeap(){ return Heap; }

    public PrgState(IStack<IStmt> stack, IDictionary<String, Value> symbolTable, IList<Value> outt, IStmt originalProgram,
                    IDictionary<StringValue, BufferedReader> fileTable, IHeap<Integer, Value> heap){
        exeStack=stack;
        symTable = symbolTable;
        out = outt;
        originalProgram = originalProgram.deepCopy();
        exeStack.push(originalProgram);
        this.fileTable = fileTable;
        this.Heap = heap;

        Lock lock = new ReentrantLock();
        lock.lock();
        programStateID = lastUsedID+1;
        lastUsedID+=1;
        lock.unlock();
    }



    public PrgState oneStep() throws MyException {
        if(exeStack.isEmpty())
            throw new MyException("Program State stack is empty!\n");
        IStmt currentStatement = exeStack.pop();
        return currentStatement.execute(this);
    }

    public boolean isNotComplete(){
        return !this.exeStack.isEmpty();
    }

    public String toString(){
        return  "Program State ID is: " + this.programStateID + "\n\n"
                + "ExeStack is: " + exeStack.toString() + "\n\n"
                + "SymTable is: " + symTable.toString() + "\n\n"
                + "File table: " + this.fileTable.toString() + "\n\n"
                + "Heap: " + Heap.toString() + "\n\n"
                + "Output: " + out.toString() + "\n\n";
    }
}

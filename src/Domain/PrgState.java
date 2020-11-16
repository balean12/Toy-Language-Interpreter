package Domain;

import Domain.ADTS.IDictionary;
import Domain.ADTS.IList;
import Domain.ADTS.IStack;
import Domain.Statement.IStmt;
import Domain.Value.StringValue;
import Domain.Value.Value;

import java.io.BufferedReader;

public class PrgState {
    IStack<IStmt> exeStack;
    IDictionary<String, Value> symTable;
    IList<Value> out;
    IStmt originalProgram;
    IDictionary<StringValue, BufferedReader> fileTable;

    public IStack<IStmt> getStack(){
        return exeStack;
    }

    public IDictionary<String, Value> getSymTable(){ return symTable; }

    public IList<Value> getOut(){ return out; }

    public IDictionary<StringValue, BufferedReader> getFileTable(){return fileTable;}

    public IStmt getOriginalProgram() {return originalProgram;}

    public PrgState(IStack<IStmt> stack, IDictionary<String, Value> symbolTable, IList<Value> outt, IStmt originalProgram,IDictionary<StringValue, BufferedReader> fileTable){
        exeStack=stack;
        symTable = symbolTable;
        out = outt;
        originalProgram = originalProgram.deepCopy();
        exeStack.push(originalProgram);
        this.fileTable = fileTable;
    }
    public String toString(){
        return "ExeStack is: " + exeStack.toString() + "\n"
                + "SymTable is: " + symTable.toString() + "\n"
                + "Output: " + out.toString() + "\n\n";
    }
}

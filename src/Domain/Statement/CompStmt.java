package Domain.Statement;

import Domain.ADTS.IStack;
import Domain.Exception.MyException;
import Domain.PrgState;

public class CompStmt implements IStmt {
    IStmt first;
    IStmt second;

    public IStmt getFirst() { return first;}
    public IStmt getSecond() {return second;}
    public CompStmt(IStmt frst, IStmt scnd) {
        first = frst;
        second = scnd;
    }

    public String toString(){
        return "(" + first.toString() + "; " + second.toString() +")";
    }
    public PrgState execute(PrgState state) throws MyException {
        IStack<IStmt> stack = state.getStack();
        stack.push(second);
        stack.push(first);
        return null;
    }
    public IStmt deepCopy(){return new CompStmt(first.deepCopy(), second.deepCopy());}
}

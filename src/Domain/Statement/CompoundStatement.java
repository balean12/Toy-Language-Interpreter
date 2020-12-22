package Domain.Statement;

import Domain.ADTS.IDictionary;
import Domain.ADTS.IStack;
import Domain.Exception.MyException;
import Domain.ProgramState;
import Domain.Types.IType;

public class CompoundStatement implements IStmt {
    IStmt first;
    IStmt second;

    public IStmt getFirst() { return first;}
    public IStmt getSecond() {return second;}
    public CompoundStatement(IStmt frst, IStmt scnd) {
        first = frst;
        second = scnd;
    }

    public String toString(){
        return "(" + first.toString() + "; " + second.toString() +")";
    }
    public ProgramState execute(ProgramState state) throws MyException {
        IStack<IStmt> stack = state.getStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws MyException {
        return second.typeCheck(first.typeCheck(typeEnvironment));
    }

    public IStmt deepCopy(){return new CompoundStatement(first.deepCopy(), second.deepCopy());}
}

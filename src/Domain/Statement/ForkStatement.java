package Domain.Statement;

import Domain.ADTS.IDictionary;
import Domain.ADTS.IStack;
import Domain.ADTS.MyStack;
import Domain.Exception.MyException;
import Domain.ProgramState;
import Domain.Types.IType;

public class ForkStatement implements IStmt{
    private final IStmt statement;

    public ForkStatement(IStmt statement) {this.statement = statement;}

    //public IStmt getForkStatement() {return statement;}

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IStack<IStmt> newExeStack = new MyStack<>();
        //newExeStack.push(this.statement);
        return new ProgramState(newExeStack, state.getSymTable().deepCopy(), state.getOut(), this.statement ,state.getFileTable(), state.getHeap(), state.getSemaphoreTable());
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws MyException {
        statement.typeCheck(typeEnvironment.deepCopy());
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "fork(" + this.statement.toString() + ")\n";
    }
    @Override
    public IStmt deepCopy() {
        return new ForkStatement(this.statement.deepCopy());
    }
}

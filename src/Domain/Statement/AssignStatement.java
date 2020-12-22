package Domain.Statement;

import Domain.ADTS.IDictionary;
import Domain.ADTS.IStack;
import Domain.Exception.MyException;
import Domain.Exception.StatementException;
import Domain.Exp.IExpression;
import Domain.ProgramState;
import Domain.Types.IType;
import Domain.Value.IValue;

public class AssignStatement implements IStmt {
    String id;
    IExpression expression;

    public AssignStatement(String v, IExpression valueExp) {
        id = v;
        expression = valueExp;
    }

    public String getId() {return id;}

    public IExpression getExpression() {return expression;}

    public ProgramState execute(ProgramState state) throws MyException {
        IStack<IStmt> stack = state.getStack();
        IDictionary<String, IValue> symTbl = state.getSymTable();

        if(symTbl.isDefined(id)){
            IValue val = expression.eval(symTbl, state.getHeap());
            IType typeId = (symTbl.lookup(id)).getType();
            if((val.getType()).equals(typeId))
                symTbl.update(id,val);
            else throw new StatementException("declared type of variable " +
                 id + "and type of the assigned expression do not match.");
        }
        else throw new StatementException("the used variable "+ id+ " was not declared before");
        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws MyException {
        IType typeVariable = typeEnvironment.lookup(id);
        IType typeExpression =expression.typeCheck(typeEnvironment);
        if(typeVariable.equals(typeExpression))
            return typeEnvironment;
        else
            throw new StatementException("Assignment: right hand side and left hand side have different types \n");
    }

    public IStmt deepCopy() { return new AssignStatement(this.id, this.expression.deepCopy());}

    public String toString() {return id + "=" + expression.toString();}
}

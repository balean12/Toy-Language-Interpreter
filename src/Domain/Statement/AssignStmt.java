package Domain.Statement;

import Domain.ADTS.IDictionary;
import Domain.ADTS.IStack;
import Domain.Exception.MyException;
import Domain.Exception.StatementException;
import Domain.Exp.Exp;
import Domain.Exp.ValueExp;
import Domain.PrgState;
import Domain.Types.Type;
import Domain.Value.Value;

public class AssignStmt implements IStmt {
    String id;
    Exp expression;

    public AssignStmt(String v, Exp valueExp) {
        id = v;
        expression = valueExp;
    }

    public String getId() {return id;}

    public Exp getExpression() {return expression;}

    public PrgState execute(PrgState state) throws MyException {
        IStack<IStmt> stack = state.getStack();
        IDictionary<String, Value> symTbl = state.getSymTable();

        if(symTbl.isDefined(id)){
            Value val = expression.eval(symTbl, state.getHeap());
            Type typeId = (symTbl.lookup(id)).getType();
            if((val.getType()).equals(typeId))
                symTbl.update(id,val);
            else throw new StatementException("declared type of variable " +
                 id + "and type of the assigned expression do not match.");
        }
        else throw new StatementException("the used variable "+ id+ " was not declared before");
        return null;
    }
    public IStmt deepCopy() { return new AssignStmt(this.id, this.expression.deepCopy());}

    public String toString() {return id + "=" + expression.toString();}
}

package Domain.Statement;

import Domain.ADTS.IList;
import Domain.Exception.MyException;
import Domain.Exp.Exp;
import Domain.Exp.VarExp;
import Domain.PrgState;
import Domain.Value.IntValue;
import Domain.Value.Value;

public class PrintStmt implements IStmt{
    Exp expression;

    public Exp getExpression() {return expression;}

    public PrintStmt(Exp value) {
        expression = value;
    }


    public PrgState execute(PrgState state) throws MyException {
        IList<Value> out = state.getOut();
        Value valueExpression = this.expression.eval(state.getSymTable(), state.getHeap());
        out.add(valueExpression);
        return null;
    }
    public String toString() {return "print(" + expression.toString() +")";}

    public IStmt deepCopy() {return new PrintStmt(expression.deepCopy());}
}

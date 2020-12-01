package Domain.Statement;

import Domain.ADTS.IStack;
import Domain.Exception.MyException;
import Domain.Exception.StatementException;
import Domain.Exp.Exp;
import Domain.PrgState;
import Domain.Types.BoolType;
import Domain.Value.BoolValue;
import Domain.Value.Value;

public class IfStmt implements IStmt {
    Exp expression;
    IStmt thenStatement;
    IStmt elseStatement;
    public Exp getExpression(){return expression;}
    public IStmt getThenStatement(){return thenStatement;}
    public IStmt getElseStatement(){return elseStatement;}

    public IfStmt(Exp e, IStmt then, IStmt els) {expression = e; thenStatement = then; elseStatement = els;}
    public PrgState execute(PrgState state) throws MyException {
    Value expressionValue = this.expression.eval(state.getSymTable(), state.getHeap());
    if(!expressionValue.getType().equals(new BoolType())) throw new StatementException("Conditional expression is not a boolean");
        else {
        IStack<IStmt> exeStack = state.getStack();
        BoolValue boolValue = (BoolValue)expressionValue;
        boolean condition = boolValue.getValue();
        if(condition) exeStack.push(thenStatement);
        else exeStack.push(elseStatement);
    }
        return null;
    }
    public String toString() {return "(IF(" + expression.toString()+ ") THEN("
            + thenStatement.toString() + ")ELSE(" + elseStatement.toString()+"))";}
    public IStmt deepCopy() { return new IfStmt(expression.deepCopy(),thenStatement.deepCopy(), elseStatement.deepCopy());}
}

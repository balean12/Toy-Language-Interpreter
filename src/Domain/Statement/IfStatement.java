package Domain.Statement;

import Domain.ADTS.IDictionary;
import Domain.ADTS.IStack;
import Domain.Exception.MyException;
import Domain.Exception.StatementException;
import Domain.Exp.IExpression;
import Domain.ProgramState;
import Domain.Types.BooleanType;
import Domain.Types.IType;
import Domain.Value.BoolValue;
import Domain.Value.IValue;

public class IfStatement implements IStmt {
    IExpression expression;
    IStmt thenStatement;
    IStmt elseStatement;
    public IExpression getExpression(){return expression;}
    //public IStmt getThenStatement(){return thenStatement;}
    //public IStmt getElseStatement(){return elseStatement;}

    public IfStatement(IExpression e, IStmt then, IStmt els) {expression = e; thenStatement = then; elseStatement = els;}
    public ProgramState execute(ProgramState state) throws MyException {
    IValue expressionValue = this.expression.eval(state.getSymTable(), state.getHeap());
    if(!expressionValue.getType().equals(new BooleanType())) throw new StatementException("Conditional expression is not a boolean");
        else {
        IStack<IStmt> exeStack = state.getStack();
        BoolValue boolValue = (BoolValue)expressionValue;
        boolean condition = boolValue.getValue();
        if(condition) exeStack.push(thenStatement);
        else exeStack.push(elseStatement);
    }
        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws MyException {
        IType typeExpression = expression.typeCheck(typeEnvironment);
        if(typeExpression.equals(new BooleanType())){
            thenStatement.typeCheck(typeEnvironment.deepCopy());
            elseStatement.typeCheck(typeEnvironment.deepCopy());
            return typeEnvironment;
        }else
            throw new StatementException("The condition of IF has not the type bool \n");

    }

    public String toString() {return "(IF(" + expression.toString()+ ") THEN("
            + thenStatement.toString() + ")ELSE(" + elseStatement.toString()+"))";}
    public IStmt deepCopy() { return new IfStatement(expression.deepCopy(),thenStatement.deepCopy(), elseStatement.deepCopy());}
}

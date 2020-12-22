package Domain.Statement;

import Domain.ADTS.IDictionary;
import Domain.ADTS.IList;
import Domain.Exception.MyException;
import Domain.Exp.IExpression;
import Domain.ProgramState;
import Domain.Types.IType;
import Domain.Value.IValue;

public class PrintStatement implements IStmt{
    IExpression expression;

    public IExpression getExpression() {return expression;}

    public PrintStatement(IExpression value) {
        expression = value;
    }


    public ProgramState execute(ProgramState state) throws MyException {
        IList<IValue> out = state.getOut();
        IValue valueExpression = this.expression.eval(state.getSymTable(), state.getHeap());
        out.add(valueExpression);
        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws MyException {
        expression.typeCheck(typeEnvironment);
        return typeEnvironment;
    }

    public String toString() {return "print(" + expression.toString() +")";}

    public IStmt deepCopy() {return new PrintStatement(expression.deepCopy());}
}

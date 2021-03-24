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

public class WhileStatement implements IStmt{
    IStmt statement;
    IExpression expression;

    public WhileStatement(IStmt statement, IExpression expression){
        this.statement= statement;
        this.expression= expression;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        var symbolTable = state.getSymTable();
        IValue expressionValue = expression.eval(symbolTable, state.getHeap());
        if(expressionValue.getType().equals(new BooleanType())){
            IStack<IStmt> exeStack = state.getStack();
            BoolValue boolValue = (BoolValue) expressionValue;
            boolean condition = boolValue.getValue();
            if(condition) {
                exeStack.push(this);
                exeStack.push(statement);
            }
        }
        else throw new StatementException("Condition exp is not a boolean! \n");
        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws MyException {
        IType typeExpression = expression.typeCheck(typeEnvironment);
        if(typeExpression.equals(new BooleanType())){
            statement.typeCheck(typeEnvironment.deepCopy());
            return typeEnvironment;
        }else
            throw new StatementException("The condition of WHILE statement is not bool! \n");
    }

    @Override
    public String toString() {
        return "while(" + expression.toString() + ")" + " { " + this.statement.toString() + " }";
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStatement(this.statement.deepCopy(), this.expression.deepCopy());
    }
}

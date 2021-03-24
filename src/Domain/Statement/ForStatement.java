package Domain.Statement;

import Domain.ADTS.IDictionary;
import Domain.Exception.MyException;
import Domain.Exception.StatementException;
import Domain.Exp.IExpression;
import Domain.Exp.RelationalExpression;
import Domain.Exp.VariableExpression;
import Domain.ProgramState;
import Domain.Types.IType;
import Domain.Types.IntegerType;

public class ForStatement implements IStmt{
    private String variableId;
    private IExpression expression1;
    private IExpression expression2;
    private IExpression expression3;
    private IStmt statement;

    public ForStatement(String variableId, IExpression expression1, IExpression expression2, IExpression expression3, IStmt statement) {
        this.variableId = variableId;
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.expression3 = expression3;
        this.statement = statement;
    }
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IStmt compoundStmt = new CompoundStatement(
                new VariableDeclarationStatement(this.variableId, new IntegerType()),
                new CompoundStatement(
                        new AssignStatement(this.variableId,this.expression1),
                        new WhileStatement(
                                new CompoundStatement(
                                        this.statement,
                                        new AssignStatement(this.variableId,this.expression3)
                                ),
                                new RelationalExpression(new VariableExpression(this.variableId), this.expression2,"<")

                        )
                )
        );
        state.getStack().push(compoundStmt);
        return null;
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws MyException {
        typeEnvironment.add(this.variableId, new IntegerType());
        IType typeExpression1 = this.expression1.typeCheck(typeEnvironment);
        IType typeExpression2 = this.expression2.typeCheck(typeEnvironment);
        IType typeExpression3 = this.expression3.typeCheck(typeEnvironment);

        if(typeExpression1.equals(new IntegerType())) {
            if(typeExpression2.equals(new IntegerType())) {
                if(typeExpression3.equals(new IntegerType())) {
                    return typeEnvironment;
                }
                else throw new StatementException("For statement: expression 1 type not int");
            }
            else throw new StatementException("For statement: expression 2 type not int");
        }
        else throw new StatementException("For statement: expression 3 type not int");
    }

    @Override
    public IStmt deepCopy() {
        return new ForStatement(this.variableId, this.expression1.deepCopy(), this.expression2.deepCopy(),
                this.expression3.deepCopy(),this.statement.deepCopy());
    }

    @Override
    public String toString() {
        return "for(" + this.variableId + "=" + this.expression1.toString() + "; " + this.variableId + "<" +
                this.expression2.toString() + "; " + this.variableId + "=" + this.expression3.toString() + ")";
    }
}

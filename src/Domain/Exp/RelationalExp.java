package Domain.Exp;

import Domain.ADTS.IDictionary;
import Domain.Exception.EvaluationException;
import Domain.Exception.MyException;
import Domain.Types.IntType;
import Domain.Value.BoolValue;
import Domain.Value.IntValue;
import Domain.Value.Value;

public class RelationalExp implements Exp{
    Exp firstExpression;
    Exp secondExpression;
    String operation;
    public RelationalExp(Exp exp1, Exp exp2, String op){
        this.firstExpression = exp1;
        this.secondExpression = exp2;
        operation = op;
    }
    @Override
    public Value eval(IDictionary<String, Value> symbolTabel) throws MyException {
        Value value1, value2;
        value1 = firstExpression.eval(symbolTabel);
        if(value1.getType().equals(new IntType())){
            value2 = secondExpression.eval(symbolTabel);
            if(value2.getType().equals(new IntType())){
                IntValue intValue1 = (IntValue) value1;
                IntValue intValue2 = (IntValue) value2;
                int realIntValue1, realIntValue2;
                realIntValue1 = intValue1.getValue();
                realIntValue2 = intValue2.getValue();
                if(operation.equals("<")) return new BoolValue(realIntValue1 < realIntValue2);
                if(operation.equals("<=")) return new BoolValue(realIntValue1 <= realIntValue2);
                if(operation.equals("==")) return new BoolValue(realIntValue1 == realIntValue2);
                if(operation.equals("!=")) return new BoolValue(realIntValue1 != realIntValue2);
                if(operation.equals(">")) return new BoolValue(realIntValue1 > realIntValue2);
                if(operation.equals(">=")) return new BoolValue(realIntValue1 >= realIntValue2);
            }
            else throw new EvaluationException("Second operand is not an integer! \n");
        }
        else throw  new EvaluationException("First operand is not an integer! \n") ;
    return null;
    }

    @Override
    public String toString() {
        return  firstExpression.toString() +  operation + secondExpression.toString() +'\'';
    }

    @Override
    public Exp deepCopy() {
        Exp expression1 = this.firstExpression.deepCopy();
        Exp expression2 = this.secondExpression.deepCopy();
        return new RelationalExp(expression1,expression2,this.operation);
    }
}

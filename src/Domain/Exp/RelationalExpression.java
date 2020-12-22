package Domain.Exp;

import Domain.ADTS.IDictionary;
import Domain.ADTS.IHeap;
import Domain.Exception.EvaluationException;
import Domain.Exception.MyException;
import Domain.Types.BooleanType;
import Domain.Types.IntegerType;
import Domain.Types.IType;
import Domain.Value.BoolValue;
import Domain.Value.IntValue;
import Domain.Value.IValue;

public class RelationalExpression implements IExpression {
    IExpression firstExpression;
    IExpression secondExpression;
    String operation;
    public RelationalExpression(IExpression exp1, IExpression exp2, String op){
        this.firstExpression = exp1;
        this.secondExpression = exp2;
        operation = op;
    }


    @Override
    public IValue eval(IDictionary<String, IValue> symbolTabel, IHeap<Integer, IValue> heap) throws MyException {
        IValue value1, value2;
        value1 = firstExpression.eval(symbolTabel, heap);
        if(value1.getType().equals(new IntegerType())){
            value2 = secondExpression.eval(symbolTabel, heap);
            if(value2.getType().equals(new IntegerType())){
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
        else throw  new EvaluationException("First operand is not an integer!! \n") ;
        return null;
    }

    @Override
    public IType typeCheck(IDictionary<String, IType> typeEnvironment) throws MyException {
        IType type1, type2;
        type1 = this.firstExpression.typeCheck(typeEnvironment);
        type2 = this.secondExpression.typeCheck(typeEnvironment);
        if(type1.equals(new IntegerType())){
            if(type2.equals(new IntegerType())){
                return new BooleanType();
            }
            else throw new EvaluationException("Second operand is not an integer! \n");
        }
        else throw new EvaluationException("First operand is not an integer! \n");
    }

    @Override
    public String toString() {
        return  firstExpression.toString() +  operation + secondExpression.toString() +'\n';
    }

    @Override
    public IExpression deepCopy() {
        IExpression expression1 = this.firstExpression.deepCopy();
        IExpression expression2 = this.secondExpression.deepCopy();
        return new RelationalExpression(expression1,expression2,this.operation);
    }
}

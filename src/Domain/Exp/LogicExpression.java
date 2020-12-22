package Domain.Exp;

import Domain.ADTS.IDictionary;
import Domain.ADTS.IHeap;
import Domain.Exception.EvaluationException;
import Domain.Exception.MyException;
import Domain.Types.BooleanType;
import Domain.Types.IType;
import Domain.Value.BoolValue;
import Domain.Value.IValue;

public class LogicExpression implements IExpression {
    IExpression firstExpression;
    IExpression secondExpression;
    String operator;

    public LogicExpression(IExpression exp1, IExpression exp2, String operator){
        this.firstExpression = exp1;
        this.secondExpression = exp2;
        this.operator = operator;
    }

    public IExpression getFirstExpression() {return firstExpression;}
    public void setFirstExpression(IExpression newExp) {this.firstExpression = newExp ;}

    public IExpression getSecondExpression() {return secondExpression;}
    public void setSecondExpression(IExpression newExp) {this.secondExpression = newExp;}

    public String getOperator() {return this.operator;}
    public void setOperator(String newOp) {this.operator = newOp;}

    @Override
    public IValue eval(IDictionary<String, IValue> symbolTable, IHeap<Integer, IValue> heap) throws MyException {
        IValue value1, value2;
        value1 = this.firstExpression.eval(symbolTable, heap);
        if(value1.getType().equals(new BooleanType())){
            value2 = this.secondExpression.eval(symbolTable, heap);
            if(value2.getType().equals(new BooleanType())){
                BoolValue boolValue1 = (BoolValue)value1;
                BoolValue boolValue2 = (BoolValue)value2;
                boolean realBoolValue1, realBoolValue2;
                realBoolValue1 = boolValue1.getValue();
                realBoolValue2 = boolValue2.getValue();
                if(this.operator.equalsIgnoreCase("and")){
                    return new BoolValue(realBoolValue1 && realBoolValue2);
                }
                else if(this.operator.equalsIgnoreCase("or")){
                    return new BoolValue(realBoolValue1 || realBoolValue2);
                }
                else throw new EvaluationException("Invalid logic operator! \n");
            }
            else throw  new EvaluationException("Operand 2 is not a boolean! \n");
        }
        else throw new EvaluationException("Operand 1 is not a boolean! \n");
    }

    @Override
    public IType typeCheck(IDictionary<String, IType> typeEnvironment) throws MyException {
        IType type1, type2;
        type1 = this.firstExpression.typeCheck(typeEnvironment);
        type2 = this.secondExpression.typeCheck(typeEnvironment);
        if(type1.equals(new BooleanType())){
            if(type2.equals(new BooleanType())){
                return new BooleanType();
            }
            else throw new EvaluationException("Second operand is not a boolean! \n");
        }
        else throw new EvaluationException("First operand is not a boolean! \n");
    }

    public String toString(){
        return this.firstExpression.toString() + " " + operator + " " + this.secondExpression.toString();
    }
    public IExpression deepCopy(){
        IExpression expression1 = this.firstExpression.deepCopy();
        IExpression expression2 = this.secondExpression.deepCopy();
        return new LogicExpression(expression1, expression2, this.operator);
    }
}

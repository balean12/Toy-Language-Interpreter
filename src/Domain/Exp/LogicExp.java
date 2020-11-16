package Domain.Exp;

import Domain.ADTS.IDictionary;
import Domain.Exception.EvaluationException;
import Domain.Exception.MyException;
import Domain.Types.BoolType;
import Domain.Value.BoolValue;
import Domain.Value.Value;

public class LogicExp implements Exp{
    Exp firstExpression;
    Exp secondExpression;
    String operator;

    public LogicExp(Exp exp1, Exp exp2, String operator){
        this.firstExpression = exp1;
        this.secondExpression = exp2;
        this.operator = operator;
    }

    public Exp getFirstExpression() {return firstExpression;}
    public void setFirstExpression(Exp newExp) {this.firstExpression = newExp ;}

    public Exp getSecondExpression() {return secondExpression;}
    public void setSecondExpression(Exp newExp) {this.secondExpression = newExp;}

    public String getOperator() {return this.operator;}
    public void setOperator(String newOp) {this.operator = newOp;}

    @Override
    public Value eval(IDictionary<String, Value> symbolTable) throws MyException {
        Value value1, value2;
        value1 = this.firstExpression.eval(symbolTable);
        if(value1.getType().equals(new BoolType())){
            value2 = this.secondExpression.eval(symbolTable);
            if(value2.getType().equals(new BoolType())){
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
    public String toString(){
        return this.firstExpression.toString() + " " + operator + " " + this.secondExpression.toString();
    }
    public Exp deepCopy(){
        Exp expression1 = this.firstExpression.deepCopy();
        Exp expression2 = this.secondExpression.deepCopy();
        return new LogicExp(expression1, expression2, this.operator);
    }
}

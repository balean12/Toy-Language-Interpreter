package Domain.Exp;

import Domain.ADTS.IDictionary;
import Domain.Exception.EvaluationException;
import Domain.Exception.MyException;
import Domain.Types.IntType;
import Domain.Value.IntValue;
import Domain.Value.Value;

public class ArithExp implements Exp {
    Exp firstExpression;
    Exp secondExpression;
    char operation; //1-plus, 2-minus, 3-star, 4-divide

    public ArithExp(Exp expression1, Exp expression2, char op){
        firstExpression = expression1;
        secondExpression = expression2;
        operation = op;
    }

    public Value eval(IDictionary<String,Value> symbolTable)throws MyException{
        Value value1, value2;
        value1=firstExpression.eval(symbolTable);
        if(value1.getType().equals(new IntType())){
            value2=secondExpression.eval(symbolTable);
            if(value2.getType().equals(new IntType())){
                IntValue intValue1 = (IntValue)value1;
                IntValue intValue2 = (IntValue)value2;
                int realIntValue1, realIntValue2;
                realIntValue1 = intValue1.getValue();
                realIntValue2 = intValue2.getValue();
                if(operation == '+') return new IntValue(realIntValue1+realIntValue2);
                if(operation == '-') return new IntValue(realIntValue1-realIntValue2);
                if(operation == '*') return new IntValue(realIntValue1*realIntValue2);
                if(operation == '/'){
                    if(realIntValue2 == 0) throw new EvaluationException("Division by zero!\n");
                        else return new IntValue(realIntValue1/realIntValue2);
                }
            else throw new EvaluationException("Second operand is not an integer!\n");
            }
        }
        else throw new EvaluationException("First operand is not an integer! \n");
    return null;
    }

    public char getOperation() {return this.operation;}
    public void setOperation(char newOperation) {this.operation = newOperation;}

    public Exp getFirstExpression() {return firstExpression;}
    public void setFirstExpression(Exp newFirstExpression) { firstExpression = newFirstExpression;}

    public Exp getSecondExpression() {return secondExpression;}
    public void setSecondExpression(Exp newSecondExpression) { secondExpression = newSecondExpression;}

    public String toString() {
        return this.firstExpression.toString() + this.operation + this.secondExpression.toString();
    }
    public Exp deepCopy(){
        Exp expression1 = this.firstExpression.deepCopy();
        Exp expression2 = this.secondExpression.deepCopy();
        return new ArithExp(expression1,expression2,this.operation);
    }
}

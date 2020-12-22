package Domain.Exp;

import Domain.ADTS.IDictionary;
import Domain.ADTS.IHeap;
import Domain.Exception.EvaluationException;
import Domain.Exception.MyException;
import Domain.Types.IntegerType;
import Domain.Types.IType;
import Domain.Value.IntValue;
import Domain.Value.IValue;

public class ArithmeticExpression implements IExpression {
    IExpression firstExpression;
    IExpression secondExpression;
    char operation; //1-plus, 2-minus, 3-star, 4-divide

    public ArithmeticExpression(IExpression expression1, IExpression expression2, char op){
        firstExpression = expression1;
        secondExpression = expression2;
        operation = op;
    }

    public char getOperation() {return this.operation;}
    public void setOperation(char newOperation) {this.operation = newOperation;}

    public IExpression getFirstExpression() {return firstExpression;}
    public void setFirstExpression(IExpression newFirstExpression) { firstExpression = newFirstExpression;}

    public IExpression getSecondExpression() {return secondExpression;}
    public void setSecondExpression(IExpression newSecondExpression) { secondExpression = newSecondExpression;}

    @Override
    public IValue eval(IDictionary<String, IValue> symbolTable, IHeap<Integer, IValue> heap) throws MyException {
        IValue value1, value2;
        value1=firstExpression.eval(symbolTable, heap);
        if(value1.getType().equals(new IntegerType())){
            value2=secondExpression.eval(symbolTable, heap);
            if(value2.getType().equals(new IntegerType())){
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

    @Override
    public IType typeCheck(IDictionary<String, IType> typeEnvironment) throws MyException {
        IType type1, type2;
        type1 = this.firstExpression.typeCheck(typeEnvironment);
        type2 = this.secondExpression.typeCheck(typeEnvironment);
        if(type1.equals(new IntegerType())){
            if(type2.equals(new IntegerType())){
                return new IntegerType();
            }
            else throw new EvaluationException("Second operand is not an integer! \n");
        }
        else throw new EvaluationException("First operand is not an integer! \n");
    }

    public String toString() {
        return this.firstExpression.toString() + this.operation + this.secondExpression.toString();
    }
    public IExpression deepCopy(){
        IExpression expression1 = this.firstExpression.deepCopy();
        IExpression expression2 = this.secondExpression.deepCopy();
        return new ArithmeticExpression(expression1,expression2,this.operation);
    }
}

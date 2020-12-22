package Domain.Value;

import Domain.Types.IntegerType;
import Domain.Types.IType;

public class IntValue implements IValue {
    private final int value;

    public IntValue(int v) {value = v;}

    public int getValue() {return value;}

    public String toString() {return String.valueOf(this.value);}

    public IType getType() { return new IntegerType();}

    @Override
    public boolean equals(Object another){
        return another instanceof IntValue && ((IntValue) another).value == value;
    }

    public IValue deepCopy() {
        IntValue copy = new IntValue(this.value);
        return copy;
    }
}

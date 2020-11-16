package Domain.Value;

import Domain.Types.IntType;
import Domain.Types.Type;

public class IntValue implements Value {
    private final int value;

    public IntValue(int v) {value = v;}

    public int getValue() {return value;}

    public String toString() {return String.valueOf(this.value);}

    public Type getType() { return new IntType();}

    @Override
    public boolean equals(Object another){
        return another instanceof IntValue && ((IntValue) another).value == value;
    }

    public Value deepCopy() {
        IntValue copy = new IntValue(this.value);
        return copy;
    }
}

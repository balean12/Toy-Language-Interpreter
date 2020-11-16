package Domain.Value;

import Domain.Types.BoolType;
import Domain.Types.Type;

public class BoolValue implements Value{
    private final boolean value;

    public BoolValue(boolean v) {value = v;}

    public boolean getValue() {return value;}

    public String toString() {return String.valueOf(this.value) ;}

    public Type getType() { return new BoolType();}

    @Override
    public boolean equals(Object another) {
        return another instanceof BoolValue && ((BoolValue) another).value == value;
    }

    public Value deepCopy(){
        BoolValue copy = new BoolValue(this.value);
        return copy;
    }
}


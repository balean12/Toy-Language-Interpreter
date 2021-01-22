package Domain.Value;

import Domain.Types.BooleanType;
import Domain.Types.IType;

public class BoolValue implements IValue {
    private final boolean value;

    public BoolValue(boolean v) {value = v;}

    public boolean getValue() {return value;}

    public String toString() {return String.valueOf(this.value) ;}

    public IType getType() { return new BooleanType();}

    @Override
    public boolean equals(Object another) {
        return another instanceof BoolValue && ((BoolValue) another).value == value;
    }

    public IValue deepCopy(){
        return new BoolValue(this.value);
    }
}


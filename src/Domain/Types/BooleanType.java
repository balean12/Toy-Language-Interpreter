package Domain.Types;

import Domain.Value.BoolValue;
import Domain.Value.IValue;

public class BooleanType implements IType {
    public boolean equals(Object another){
        return another instanceof BooleanType;
    }

    @Override
    public String toString() {
        return "boolean";
    }

    public IValue defaultValue() {return new BoolValue(false);}

    public IType deepCopy() {return new BooleanType();}
}


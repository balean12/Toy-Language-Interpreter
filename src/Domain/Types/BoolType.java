package Domain.Types;

import Domain.Value.BoolValue;
import Domain.Value.Value;

public class BoolType implements Type{
    public boolean equals(Object another){
        return another instanceof BoolType;
    }

    @Override
    public String toString() {
        return "boolean";
    }

    public Value defaultValue() {return new BoolValue(false);}

    public Type deepCopy() {return new BoolType();}
}


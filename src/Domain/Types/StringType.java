package Domain.Types;

import Domain.Value.StringValue;
import Domain.Value.IValue;

public class StringType implements IType {

    public boolean equals(Object another){
        return another instanceof StringType;
    }

    @Override
    public IValue defaultValue() {
        return new StringValue("");
    }

    @Override
    public IType deepCopy() {
        return new StringType();
    }

    @Override
    public String toString() {
        return "string";
    }
}

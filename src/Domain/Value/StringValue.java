package Domain.Value;

import Domain.Types.StringType;
import Domain.Types.IType;

public class StringValue implements IValue {
    private final String value;
    public StringValue(String v) {value = v;};

    public String getValue() {return value;}

    public String toString() {return String.valueOf(this.value);}

    @Override
    public boolean equals(Object another){
        return another instanceof StringValue && ((StringValue) another).value.equals(value);
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public IValue deepCopy() {
        return new StringValue(this.value);
    }
}

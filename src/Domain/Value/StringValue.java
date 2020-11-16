package Domain.Value;

import Domain.Types.StringType;
import Domain.Types.Type;

public class StringValue implements  Value{
    private final String value;
    public StringValue(String v) {value = v;};

    public String getValue() {return value;}

    public String toString() {return String.valueOf(this.value);}

    @Override
    public boolean equals(Object another){
        return another instanceof StringValue && ((StringValue) another).value.equals(value);
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public Value deepCopy() {
        return new StringValue(this.value);
    }
}

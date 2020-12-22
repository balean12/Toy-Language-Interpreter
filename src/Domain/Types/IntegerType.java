package Domain.Types;

import Domain.Value.IntValue;
import Domain.Value.IValue;

public class IntegerType implements IType {

    public boolean equals(Object another){
        return another instanceof IntegerType;
    }
    public String toString() {return "int"; }

    public IValue defaultValue(){ return new IntValue(0);}

    public IType deepCopy() { return new IntegerType();}
}

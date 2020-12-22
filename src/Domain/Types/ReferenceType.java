package Domain.Types;

import Domain.Value.ReferenceValue;
import Domain.Value.IValue;

public class ReferenceType implements IType {
    IType inner;

    public ReferenceType(IType inner) { this.inner = inner; }

    public IType getInner(){
        return this.inner;
    }

    public boolean equals(Object another){
        if(another instanceof ReferenceType){
            return this.inner.equals(((ReferenceType) another).getInner());
        }
        else
            return false;
    }
    @Override
    public IValue defaultValue() {
        return new ReferenceValue(0,inner);
    }

    @Override
    public String toString() {
        return "Ref(" + inner.toString() + ")";
    }

    @Override
    public IType deepCopy() {
        return new ReferenceType(this.inner.deepCopy());
    }
}

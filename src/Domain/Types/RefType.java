package Domain.Types;

import Domain.Value.RefValue;
import Domain.Value.Value;

public class RefType implements Type{
    Type inner;

    public RefType(Type inner) { this.inner = inner; }

    public Type getInner(){
        return this.inner;
    }

    public boolean equals(Object another){
        if(another instanceof RefType){
            return this.inner.equals(((RefType) another).getInner());
        }
        else
            return false;
    }
    @Override
    public Value defaultValue() {
        return new RefValue(0,inner);
    }

    @Override
    public String toString() {
        return "Ref(" + inner.toString() + ")";
    }

    @Override
    public Type deepCopy() {
        return new RefType(this.inner.deepCopy());
    }
}

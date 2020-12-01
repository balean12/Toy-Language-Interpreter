package Domain.Value;

import Domain.Types.RefType;
import Domain.Types.Type;

public class RefValue implements Value {
    int address;
    Type locationType;

    public RefValue(int address, Type locationType){
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress() {
        return address;
    }
    public Type getLocationType() { return this.locationType; }

    @Override
    public Type getType() {
        return new RefType(this.locationType);
    }

    @Override
    public Value deepCopy() {
        return new RefValue(this.address, this.locationType.deepCopy());
    }

    @Override

    public boolean equals(Object another){
        return (another instanceof RefValue && ((RefValue) another).address == address) &&
                ((RefValue)another).locationType.equals(locationType);
    }

    @Override
    public String toString() {
        return "RefValue{" +
                "address=" + address +
                ", locationType=" + locationType.toString() +
                '}';
    }
}

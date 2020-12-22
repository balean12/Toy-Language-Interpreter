package Domain.Value;

import Domain.Types.ReferenceType;
import Domain.Types.IType;

public class ReferenceValue implements IValue {
    int address;
    IType locationType;

    public ReferenceValue(int address, IType locationType){
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress() {
        return address;
    }
    public IType getLocationType() { return this.locationType; }

    @Override
    public IType getType() {
        return new ReferenceType(this.locationType);
    }

    @Override
    public IValue deepCopy() {
        return new ReferenceValue(this.address, this.locationType.deepCopy());
    }

    @Override

    public boolean equals(Object another){
        return (another instanceof ReferenceValue && ((ReferenceValue) another).address == address) &&
                ((ReferenceValue)another).locationType.equals(locationType);
    }

    @Override
    public String toString() {
        return address + " --> " +
                locationType.toString();
                    }
}

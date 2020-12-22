package Domain.Types;

import Domain.Value.IValue;

public interface IType {
    boolean equals(Object otherObject);
    String toString();
    IValue defaultValue();
    IType deepCopy();
}

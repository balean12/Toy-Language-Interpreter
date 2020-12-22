package Domain.Value;

import Domain.Types.IType;

public interface IValue {
    IType getType();
    String toString();
    IValue deepCopy();
    boolean equals(Object another);
}

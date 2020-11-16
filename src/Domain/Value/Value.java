package Domain.Value;

import Domain.Types.Type;

public interface Value {
    Type getType();
    String toString();
    Value deepCopy();
    boolean equals(Object another);
}

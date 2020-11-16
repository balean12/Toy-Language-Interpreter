package Domain.Types;

import Domain.Value.Value;

public interface Type {
    boolean equals(Object otherObject);
    String toString();
    Value defaultValue();
    Type deepCopy();
}

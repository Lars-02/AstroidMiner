package memento;

import java.io.Serializable;

public interface Restorable<State extends Serializable> {
    State serializableState();

    void restore(State state);
}

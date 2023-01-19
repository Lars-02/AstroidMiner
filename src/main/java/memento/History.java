package memento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class History<T extends Restorable<S>, S extends Serializable> {
    private List<Memento<T, S>> history = new ArrayList<>();
    private int virtualSize = 0;

    public void push(Memento<T, S> m) {
        if (virtualSize != history.size() && virtualSize > 0)
            history = history.subList(0, virtualSize - 1);

        history.add(m);
        virtualSize = history.size();
    }

    public void undo() {
        var memento = getUndo();

        if (memento == null)
            return;

        memento.restore();
    }

    public void redo() {
        var memento = getRedo();

        if (memento == null)
            return;

        memento.restore();
    }

    private Memento<T, S> getUndo() {
        if (virtualSize == 0)
            return null;

        virtualSize = Math.max(0, virtualSize - 1);
        return history.get(virtualSize);
    }

    private Memento<T, S> getRedo() {
        if (virtualSize == history.size())
            return null;

        virtualSize = Math.min(history.size(), virtualSize + 1);
        return history.get(virtualSize - 1);
    }
}
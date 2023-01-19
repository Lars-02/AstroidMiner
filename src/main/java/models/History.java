package models;

import java.util.ArrayList;
import java.util.List;

public class History {
    private List<Memento> history = new ArrayList<>();
    private int virtualSize = 0;

    public void push(Memento m) {
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

    private Memento getUndo() {
        if (virtualSize == 0)
            return null;

        virtualSize = Math.max(0, virtualSize - 1);
        return history.get(virtualSize);
    }

    private Memento getRedo() {
        if (virtualSize == history.size())
            return null;

        virtualSize = Math.min(history.size(), virtualSize + 1);
        return history.get(virtualSize - 1);
    }
}
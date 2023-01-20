package models.priority;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class PriorityList<T> {
    private final List<Pair<Priority, T>> list = new ArrayList<>();

    public boolean add(T value, Priority priority) {
        return list.add(new Pair<>(priority, value));
    }

    public boolean remove(T value) {
        return list.removeIf(priorityTPair -> priorityTPair.right() == value);
    }

    public Optional<T> findAny(Predicate<? super T> predicate) {
        return list.stream().map(Pair::right).filter(predicate).findAny();
    }

    public void forEach(Consumer<? super T> action) {
        list
                .stream()
                .sorted(Comparator.comparing(Pair::left))
                .forEach(priorityTPair -> action.accept(priorityTPair.right()));
    }
}

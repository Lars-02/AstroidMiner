package models;

public class Memento {
    private final String backup;
    private final Galaxy galaxy;

    public Memento(Galaxy galaxy) {
        this.galaxy = galaxy;
        this.backup = galaxy.serialize();
    }

    public void restore() {
        galaxy.restore(backup);
    }
}

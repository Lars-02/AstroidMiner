package ui.model;

import commands.Command;
import javafx.scene.input.KeyCode;

public class Keybinding {
    public Command command;
    public KeyCode keyCode;

    public Keybinding(Command command, KeyCode keyCode) {
        this.command = command;
        this.keyCode = keyCode;
    }
}

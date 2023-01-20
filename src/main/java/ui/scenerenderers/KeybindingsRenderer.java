package ui.scenerenderers;

import commands.Command;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import ui.Renderer;

import java.util.Map;

import static ui.model.Config.SCREEN_HEIGHT;
import static ui.model.Config.SCREEN_WIDTH;

public class KeybindingsRenderer extends SceneRenderer {
    private static final Font font = Font.font("Arial", 14);
    private Text keyClicked;
    private final Scene scene;

    public KeybindingsRenderer(Renderer renderer, SceneRenderer parent, Map<KeyCode, Command> keybindings) {
        super(renderer);

        var grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);

        scene = new Scene(grid, SCREEN_WIDTH, SCREEN_HEIGHT, Color.WHITE);

        scene.setOnKeyPressed(event -> {
            if (event.getCode() != KeyCode.ESCAPE)
                return;

            renderer.setSceneRenderer(parent);
        });

        int gridY = 0;
        for (var commandKey : keybindings.entrySet()) {
            final var descriptionText = new Text(commandKey.getValue().name);
            descriptionText.setFont(font);

            grid.add(descriptionText, 0, gridY);

            final var key = new Text(commandKey.getKey().getName());
            key.setFont(font);

            key.setOnMouseEntered(event -> {
                if (keyClicked != null && keyClicked == key)
                    return;
                key.setFill(Color.GRAY);
                scene.setCursor(Cursor.HAND);
            });

            key.setOnMouseExited(event -> {
                scene.setCursor(Cursor.DEFAULT);
                if (keyClicked == null || keyClicked != key)
                    key.setFill(Color.BLACK);
            });

            key.setOnMouseClicked(event -> {
                if (keyClicked != null) {
                    for (var child : grid.getChildren()) {
                        if (child instanceof Text text)
                            text.setFill(Color.BLACK);
                    }
                }

                key.setFill(Color.DARKBLUE);
                keyClicked = key;

                scene.setOnKeyPressed(keyEvent -> {
                    if (keyEvent.getCode() == KeyCode.ESCAPE) {
                        renderer.setSceneRenderer(parent);
                        return;
                    }

                    if (keybindings.containsKey(keyEvent.getCode())) {
                        key.setFill(Color.RED);
                        return;
                    }

                    keybindings.remove(commandKey.getKey());
                    keybindings.put(keyEvent.getCode(), commandKey.getValue());

                    key.setText(keyEvent.getCode().getName());
                    key.setFill(Color.GREEN);
                });
            });

            grid.add(key, 1, gridY);
            gridY++;
        }
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}

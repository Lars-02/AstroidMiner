package ui.scenerenderers;

import exceptions.filereader.FileReaderException;
import exceptions.galaxyparser.GalaxyParserException;
import factories.GalaxyFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import main.FlatGalaxySociety ;

import static ui.model.Config.SCREEN_HEIGHT;
import static ui.model.Config.SCREEN_WIDTH;

public class GalaxyLoaderRenderer extends SceneRenderer {
    private static final Font font = Font.font("Arial", 14);
    private final Scene scene;

    public GalaxyLoaderRenderer(FlatGalaxySociety game) {
        super(game.getRenderer());

        var vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);

        scene = new Scene(vBox, SCREEN_WIDTH, SCREEN_HEIGHT, Color.WHITE);

        var title = new Text("Galaxy Loader");
        title.setFont(font);

        var label = new Label("Path:");
        label.setFont(font);

        var galaxyFilePath = new TextField();
        galaxyFilePath.setText("./src/main/resources/planetsExtended.csv");
        galaxyFilePath.setMaxWidth(SCREEN_WIDTH*0.8);
        galaxyFilePath.setFont(font);

        var submit = new Button("Submit");
        submit.setFont(font);

        EventHandler<ActionEvent> onSubmit = event -> {
            try {
                game.galaxy = GalaxyFactory.fromFile(galaxyFilePath.getText());

                renderer.setSceneRenderer(new GameRenderer(renderer, game));
            } catch (FileReaderException | GalaxyParserException e) {
                throw new RuntimeException(e);
            }
        };

        submit.setOnAction(onSubmit);
        galaxyFilePath.setOnAction(onSubmit);

        vBox.getChildren().addAll(title, label, galaxyFilePath, submit);
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}

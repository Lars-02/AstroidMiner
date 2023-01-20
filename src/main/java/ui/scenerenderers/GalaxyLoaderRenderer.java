package ui.scenerenderers;

import exceptions.filereader.FileReaderException;
import exceptions.galaxyparser.GalaxyParserException;
import factories.GalaxyFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import main.FlatGalaxySociety;

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

        var error = new Text("");
        error.setFont(font);
        error.setFill(Color.RED);

        var title = new Text("Galaxy Loader");
        title.setFont(font);

        var label = new Label("Path:");
        label.setFont(font);

        var defaultGalaxies = new ComboBox<String>();
        defaultGalaxies.getItems().addAll(
                "./src/main/resources/planetsExtended.csv",
                "./src/main/resources/planetsExtended.xml",
                "./src/main/resources/bigPlanets.csv",
                "https://firebasestorage.googleapis.com/v0/b/dpa-files.appspot.com/o/planetsExtended.csv?alt=media",
                "https://firebasestorage.googleapis.com/v0/b/dpa-files.appspot.com/o/planetsExtended.xml?alt=media"
        );

        defaultGalaxies.setOnAction(event -> loadGalaxy(error, game, defaultGalaxies.getValue()));
        defaultGalaxies.setMaxWidth(SCREEN_WIDTH * 0.8);

        var galaxyFilePath = new TextField();
        galaxyFilePath.setMaxWidth(SCREEN_WIDTH * 0.8);
        galaxyFilePath.setFont(font);

        var submit = new Button("Submit");
        submit.setFont(font);

        EventHandler<ActionEvent> onSubmit = event -> loadGalaxy(error, game, galaxyFilePath.getText());

        submit.setOnAction(onSubmit);
        galaxyFilePath.setOnAction(onSubmit);

        vBox.getChildren().addAll(title, label, defaultGalaxies, galaxyFilePath, submit, error);
    }

    private void loadGalaxy(Text error, FlatGalaxySociety game, String path) {
        try {
            game.galaxy = GalaxyFactory.fromFile(path);
            renderer.setSceneRenderer(new GameRenderer(renderer, game));
        } catch (FileReaderException | GalaxyParserException e) {
            e.printStackTrace();
            error.setText("Could not load file!");
        }
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}

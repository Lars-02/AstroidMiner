import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class FlatGalaxySociety extends Application {
    @Override
    public void start(Stage stage) {
        final var canvas = new Canvas(800,600);
        var gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.BLUE);
        gc.fillRect(75,75,100,100);

        var scene = new Scene(new StackPane(canvas), 800, 600, Color.WHITE);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("FlatGalaxySociety");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

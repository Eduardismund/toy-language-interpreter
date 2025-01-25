package toyLanguageInterpreter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        final var scene = new Scene(loader.load(), 1000, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Toy language Interpreter GUI");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

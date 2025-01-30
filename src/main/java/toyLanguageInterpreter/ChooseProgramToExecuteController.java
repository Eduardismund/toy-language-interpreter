package toyLanguageInterpreter;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import toyLanguageInterpreter.exceptions.ADTException;
import toyLanguageInterpreter.model.adt.MyDictionary;
import toyLanguageInterpreter.model.statements.IStmt;
import toyLanguageInterpreter.view.commands.Examples;

import java.io.IOException;

public class ChooseProgramToExecuteController {

    @FXML
    private ListView<IStmt> programsList;

    private JavaFXTableExample executeProgramController;

    @FXML
    public void initialize() {
        programsList.setItems(this.getStatements());

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ExecuteProgram.fxml"));
        Stage stage = new Stage();
        try {
            Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            this.executeProgramController = fxmlLoader.getController();
            stage.setScene(scene);
            stage.getIcons().add(new Image(String.valueOf(getClass().getResource("logo-toy-language.png"))));

            stage.show();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        programsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IStmt>() {
            @Override
            public void changed(ObservableValue<? extends IStmt> observableValue, IStmt oldStatement, IStmt newStatement) {
                String filename = "log" + (programsList.getSelectionModel().getSelectedIndex() + 1) + ".txt";
                executeProgramController.setStatement(newStatement, filename);
            }
        });
    }

    private ObservableList<IStmt> getStatements() {
        ObservableList<IStmt> exampleList = FXCollections.observableArrayList();
        final var examples = FXCollections.observableArrayList(Examples.exampleList());
        for (IStmt example : examples) {
            try {
                example.typeCheck(new MyDictionary<>());
                exampleList.add(example);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Typecheck error");
                alert.setHeaderText("Typecheck error");
                alert.setContentText("Program that did not pass:\n" + example.toString() + "\n due to:\n" + e.getMessage());
                alert.showAndWait();
            }
        }
        return exampleList;
    }
}
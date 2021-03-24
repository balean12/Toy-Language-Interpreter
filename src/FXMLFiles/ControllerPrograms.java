package FXMLFiles;

import Controller.Controller;
import Domain.ADTS.*;
import Domain.Exception.MyException;
import Domain.ProgramState;
import Domain.Statement.IStmt;
import Repository.Repository;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerPrograms {
    @FXML
    private ListView<String> allProgramListView;
    @FXML
    private Label currentProgramLabel;

    public ControllerPrograms(){}


    @FXML
    public void initialize() {
        Programs programs = new Programs();
        for(IStmt program : programs.getAllPrograms())
            allProgramListView.getItems().add(program.toString());

    }
    public void startProgram(){
        int selectedProgramIndex = allProgramListView.getSelectionModel().getSelectedIndex();
        System.out.println("Program: " + selectedProgramIndex);

        Programs programs = new Programs();
        IStmt selectedProgramStatement = programs.getAllPrograms().get(selectedProgramIndex);
        ProgramState programState = new ProgramState(
                new MyStack<>(),
                new MyDictionary<>(),
                new MyList<>(),
                selectedProgramStatement,
                new MyDictionary<>(),
                new MyHeap<>(),
                new CountSemaphore<>());
        Repository repository = new Repository("log" + selectedProgramIndex + 1 + ".txt");
        repository.addProgramState(programState);
        Controller controller = new Controller(repository);

        try{
            controller.typeCheck();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/FXMLFiles/ProgramRunning.fxml"));
            Parent root = loader.load();

            ProgramRunningController runWindowController = loader.getController();
            runWindowController.setControllerForProgram(controller);

            Stage newStage = new Stage();
            newStage.setTitle("Program: ");
            newStage.setScene(new Scene(root, 834,625));
            newStage.show();
        }catch (MyException | IOException exception){
            Alert alert = new Alert(Alert.AlertType.ERROR, exception.getMessage(), ButtonType.CLOSE);
            alert.showAndWait();
        }
    }

}


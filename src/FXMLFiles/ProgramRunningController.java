package FXMLFiles;

import Controller.Controller;
import Domain.ADTS.IList;
import Domain.Exception.MyException;
import Domain.ProgramState;
import Domain.Statement.IStmt;
import Domain.TableViewData.HeapData;
import Domain.TableViewData.SymbolData;
import Domain.TableViewData.SemaphoreData;
import Domain.Value.IValue;
import Domain.Value.StringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.net.URL;
import java.util.*;

public class ProgramRunningController implements Initializable {
    private Controller controller;
    private URL location;
    ResourceBundle resourceBundle;
    private List<ProgramState> programStates;
    private ProgramState currentProgramState;
    @FXML
    private TextField numberOfProgramStatesTextField;
    @FXML
    private TextField currentProgramTextField;
    @FXML
    private ListView<String> outTableList;
    @FXML
    private ListView<String> fileTableList;
    @FXML
    private TableView<SymbolData> symbolTableList;
    @FXML
    private TableView<HeapData> heapTableList;
    @FXML
    private ListView<String> programStateIDsList;
    @FXML
    private TableView<SemaphoreData> semaphoreTable;
    @FXML
    private ListView<String> executionStackList;
    @FXML
    private Button runOneStepButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button backButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        if(this.controller == null){
            this.location = url;
            this.resourceBundle = resourceBundle;
        }
        else{
            this.programStates = this.controller.getRepository().getAllPrograms();
            this.currentProgramState = this.programStates.get(0);
            this.controller.createThreadPool();
            initializeGUITableHeaders();

            this.numberOfProgramStatesTextField.setText(Integer.toString(this.programStates.size()));
            this.currentProgramTextField.setText(this.currentProgramState.toString());
            setTextUneditable(this.numberOfProgramStatesTextField);
            setTextUneditable(this.currentProgramTextField);

        }
    }

    private void setTextUneditable(TextInputControl text){
        text.setEditable(false);
        text.setMouseTransparent(true);
        text.setFocusTraversable(false);
    }

    private void populateExecutionStack(ProgramState programState) //throws MyException
    {
        this.executionStackList.getItems().clear();
        Stack<IStmt> executionStack = programState.getStack().getContent();
        Stack<?> cloneStack = (Stack<?>) executionStack.clone();
        while(! cloneStack.empty()) {
            this.executionStackList.getItems().add(cloneStack.pop().toString());
        }
    }

    private void populateOutputList()// throws MyException
    {
        this.outTableList.getItems().clear();
        IList<IValue> output = this.currentProgramState.getOut();
        for(int i=0; i<output.getSize(); i++){
            IValue element = output.getValue(i);
            this.outTableList.getItems().add(element.toString());
        }
    }

    private void populateFileTableList() //throws MyException
    {
        this.fileTableList.getItems().clear();
        Map<StringValue, BufferedReader> fileTable = this.currentProgramState.getFileTable().getContent();
        for(StringValue key : fileTable.keySet()){
            BufferedReader value = fileTable.get(key);
            this.fileTableList.getItems().add(key.toString() + "-->" + value.toString());
        }
    }

    private void populateHeapTable() //throws MyException
    {
        this.heapTableList.getItems().clear();
        ObservableList<HeapData> heapData = FXCollections.observableArrayList();
        Map<Integer,IValue> heap = this.currentProgramState.getHeap().getContent();
        for(Integer key:heap.keySet()){
            IValue value = heap.get(key);
            heapData.add(new HeapData(key, value.toString()));
        }
        this.heapTableList.setItems(heapData);
        //this.heapTableList.getItems().add(heapData);
    }

    private void populateSymbolTable(ProgramState programState) //throws MyException
    {
        this.symbolTableList.getItems().clear();
        ObservableList<SymbolData> symbolData = FXCollections.observableArrayList();
        Map<String, IValue> symbolTable = programState.getSymTable().getContent();
        for(String key : symbolTable.keySet()){
            IValue value = symbolTable.get(key);
            symbolData.add(new SymbolData(key, value.toString()));
        }
        this.symbolTableList.setItems(symbolData);
    }

    private void initializeGUITableHeaders(){
        // function to initialize tables with the right columns
        TableColumn<HeapData, Integer> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        TableColumn<HeapData, String> valueColumn = new TableColumn<>("Value");
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        valueColumn.setMinWidth(100);
        this.heapTableList.getColumns().addAll(addressColumn, valueColumn);

        TableColumn<SymbolData, String> variableNameColumn = new TableColumn<>("Variable Name");
        variableNameColumn.setCellValueFactory(new PropertyValueFactory<>("variableName"));
        variableNameColumn.setMinWidth(100);
        TableColumn<SymbolData, String> valColumn = new TableColumn<>("Value");
        valColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        symbolTableList.getColumns().addAll(variableNameColumn, valColumn);

        TableColumn<SemaphoreData,String> indexColumn = new TableColumn<>("Index");
        indexColumn.setCellValueFactory(new PropertyValueFactory<>("index"));
        TableColumn<SemaphoreData,Integer> valueColumnSem = new TableColumn<>("Value");
        valueColumnSem.setCellValueFactory(new PropertyValueFactory<>("value"));
        TableColumn<SemaphoreData,ArrayList<Integer>>valuesColumn = new TableColumn<>("Values");
        valuesColumn.setCellValueFactory(new PropertyValueFactory<>("values"));
        this.semaphoreTable.getColumns().addAll(indexColumn,valueColumnSem,valuesColumn);

    }

    private void populateProgramStateIds() //throws MyException
    {
        this.programStates = this.controller.getRepository().getAllPrograms();
        this.numberOfProgramStatesTextField.setText(Integer.toString(this.programStates.size()));

        this.programStateIDsList.getItems().clear();
        for(ProgramState program : this.programStates){
            this.programStateIDsList.getItems().add(Integer.toString(program.getId()));
        }
    }
    private void populateSemaphoreTable(ProgramState programState){

        this.semaphoreTable.getItems().clear();
        ObservableList<SemaphoreData> semaphoreData = FXCollections.observableArrayList();
        Map<Integer, Pair<Integer, ArrayList<Integer>>> semaphoreTable = programState.getSemaphoreTable().getContent();
        for(Integer key : semaphoreTable.keySet()){
            Pair<Integer, ArrayList<Integer>> pair = semaphoreTable.get(key);
            semaphoreData.add(new SemaphoreData(key,pair.getKey(),pair.getValue()));
        }
        this.semaphoreTable.setItems(semaphoreData);

    }

    public void populateFromProgramState() //throws MyException
    {
        String selectedId = this.programStateIDsList.getSelectionModel().getSelectedItem();
        for(ProgramState programState : this.programStates){
            if(Integer.toString(programState.getId()).equals(selectedId) && this.currentProgramState != programState){
                this.currentProgramState = programState;
                populateExecutionStack(this.currentProgramState);
                populateSymbolTable(this.currentProgramState);
            }
        }
    }

    public void runOneStep(){
        try{
            populateProgramStateIds();
            populateSymbolTable(this.currentProgramState);
            populateExecutionStack(this.currentProgramState);
            populateOutputList();
            populateFileTableList();
            populateHeapTable();
            populateSemaphoreTable(this.currentProgramState);
            this.controller.runOneStepGUI();
        }catch(MyException exception){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, exception.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void setControllerForProgram(Controller controller){
        this.controller = controller;
        this.initialize(this.location, this.resourceBundle);
    }


    public void exit(){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}

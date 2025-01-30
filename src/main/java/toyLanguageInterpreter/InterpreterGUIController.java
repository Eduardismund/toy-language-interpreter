package toyLanguageInterpreter;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import toyLanguageInterpreter.Repository.IRepository;
import toyLanguageInterpreter.Repository.RepoException;
import toyLanguageInterpreter.Repository.Repository;
import toyLanguageInterpreter.controller.Controller;
import toyLanguageInterpreter.exceptions.ADTException;
import toyLanguageInterpreter.exceptions.ControllerException;
import toyLanguageInterpreter.exceptions.InterpreterException;
import toyLanguageInterpreter.model.PrgState;
import toyLanguageInterpreter.model.adt.dictionary.MyDictionary;
import toyLanguageInterpreter.model.adt.heapTable.MyHeap;
import toyLanguageInterpreter.model.adt.latchTable.MyLatchTable;
import toyLanguageInterpreter.model.adt.list.MyList;
import toyLanguageInterpreter.model.adt.lockTable.MyLockTable;
import toyLanguageInterpreter.model.adt.semaphoreTable.MySemaphoreTable;
import toyLanguageInterpreter.model.adt.stack.MyIStack;
import toyLanguageInterpreter.model.adt.stack.MyStack;
import toyLanguageInterpreter.model.adt.toySemaphoreTable.MyToySemaphoreTable;
import toyLanguageInterpreter.model.statements.IStmt;
import toyLanguageInterpreter.model.values.StringValue;
import toyLanguageInterpreter.model.values.Value;
import toyLanguageInterpreter.view.commands.Examples;

import java.util.Map;

public class InterpreterGUIController {
    private Controller controller = null;
    @FXML
    private Label label;

    @FXML
    private TextField noOfPrograms;

    @FXML
    private ListView<PrgState> programStates;

    @FXML
    private ListView<IStmt> executionStack;

    @FXML
    private TableView<Map.Entry<Integer, Value>> heapTable;

    @FXML
    private TableColumn<Map.Entry<Integer, Value>, String> addressColumnHeapTable;

    @FXML
    private TableColumn<Map.Entry<Integer, Value>, String> valueColumnHeapTable;

    @FXML
    private TableColumn<Map.Entry<Integer, Integer>, String> column1Latch;

    @FXML
    private TableColumn<Map.Entry<Integer, Integer>, String> column2Latch;

    @FXML
    private TableView<Map.Entry<String, Value>> symbolTable;

    @FXML
    private TableView<Map.Entry<Integer, Integer>> latchTable;

    @FXML
    private TableColumn<Map.Entry<String, Value>, String> symbolColumnSymbolTable;

    @FXML
    private TableColumn<Map.Entry<String, Value>, String> valueColumnSymbolTable;

    @FXML
    private ListView<Value> output;

    @FXML
    private ListView<StringValue> fileTable;

    @FXML
    private Button runOneStepButton;

    @FXML
    public void initialize() {


        this.runOneStepButton.setOnAction(event -> this.runOneStepButtonHandler());
        runOneStepButton.setDisable(false);


        this.programStates.setCellFactory(new Callback<ListView<PrgState>, ListCell<PrgState>>() {
            @Override
            public ListCell<PrgState> call(ListView<PrgState> param) {
                return new ListCell<PrgState>() {
                    @Override
                    protected void updateItem(PrgState item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty)
                            setText("");
                        else
                            setText("Program " + item.getId_Thread());
                    }
                };
            }
        });

        this.programStates.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.programStates.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.changeThreadState(newValue);
        });

        this.addressColumnHeapTable.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getKey().toString()));
        this.valueColumnHeapTable.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().toString()));

        this.symbolColumnSymbolTable.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getKey()));
        this.valueColumnSymbolTable.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().toString()));

        this.column1Latch.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getKey().toString()));
        this.column2Latch.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().toString()));

    }

    void setStatement(IStmt newStatement, String logPath){
        PrgState prgState = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), newStatement, new MyLatchTable(), new MyToySemaphoreTable(), new MySemaphoreTable(), new MyLockTable(),
                new MyDictionary<>(), new MyHeap<>());

        try{
            Repository repository = new Repository(logPath);
            repository.add(prgState);
            this.controller = new Controller(repository);

            this.updateTables();
            this.programStates.getSelectionModel().selectFirst();
            this.runOneStepButton.setDisable(false);
        } catch (RepoException e) {
            e.printStackTrace();
        }
    }

    private void runOneStepButtonHandler() {
        try{
            this.controller.oneStepForAllProgramsGUI();
        } catch (ControllerException | InterpreterException | ADTException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            this.runOneStepButton.setDisable(true);
        }
        finally{
            this.updateTables();
        }
    }

    private void updateTables() {
        serNoOfProgramStates();
        populateProgramStateList();
        changeThreadState(this.programStates.getSelectionModel().getSelectedItem());
        populateOutput();
        populateHeapTable();
        populateFileTable();
    }


    public void populateExecutionStack(PrgState prgState){
        if(prgState != null){
            ObservableList<IStmt> statements = FXCollections.observableArrayList();
            MyIStack<IStmt> exeStack = prgState.getExeStack().deepCopy();

            while(!exeStack.isEmpty()){
                try {
                    statements.add(exeStack.pop());
                } catch (ADTException | InterpreterException e) {
                    e.printStackTrace();
                }
            }
            this.executionStack.getItems().setAll(statements);
        }
        else{
            this.executionStack.getItems().setAll(FXCollections.observableArrayList());
        }

    }

    public void serNoOfProgramStates(){
        this.noOfPrograms.setText(String.valueOf(this.controller.getPrgList().size()));
    }

    public void populateProgramStateList(){
        this.programStates.setItems(FXCollections.observableArrayList(this.controller.getPrgList()));
    }

    public void populateSymbolTable(PrgState state){
        if(state == null){
            this.symbolTable.getItems().setAll(FXCollections.observableArrayList());
        }
        else{
            this.symbolTable.getItems().setAll(FXCollections.observableArrayList(state.getSymTable().getMap().entrySet()));
        }
    }

    public void populateLatchTable(PrgState state){
        if(state == null){
            this.latchTable.getItems().setAll(FXCollections.observableArrayList());
        }
        else{
            this.latchTable.getItems().setAll(FXCollections.observableArrayList(state.getLatchTable().getMap().entrySet()));
        }
    }

    private void populateOutput(){
        if(!this.controller.getPrgList().isEmpty()){
            this.output.getItems().setAll(FXCollections.observableArrayList(this.controller.getOutput().getList()));
        }
    }

    private void populateHeapTable(){
        if(!this.controller.getPrgList().isEmpty()){
            this.heapTable.getItems().setAll(FXCollections.observableArrayList(this.controller.getHeapTable().getMap().entrySet()));
        }
    }

    private void populateFileTable(){
        if(!this.controller.getPrgList().isEmpty()){
            this.fileTable.getItems().setAll(FXCollections.observableArrayList(this.controller.getFileTable().getMap().keySet()));
        }
    }

    private void changeThreadState(PrgState prgState){
        this.populateSymbolTable(prgState);
        this.populateExecutionStack(prgState);
        this.populateLatchTable(prgState);
    }

    private Controller setControllerForProgram(int selectedProgramId) throws RepoException {
        IStmt example = Examples.exampleList()[selectedProgramId];
        PrgState prgState = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), example, new MyLatchTable(), new MyToySemaphoreTable(), new MySemaphoreTable(), new MyLockTable(), new MyDictionary<>(), new MyHeap<>() );
        IRepository repo = new Repository("log" + (selectedProgramId + 1) + ".txt");
        repo.add(prgState);
        return new Controller(repo);
    }
}
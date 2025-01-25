package toyLanguageInterpreter.controller;

import toyLanguageInterpreter.Repository.IRepository;
import toyLanguageInterpreter.Repository.RepoException;
import toyLanguageInterpreter.exceptions.*;
import toyLanguageInterpreter.exceptions.MyException;
import toyLanguageInterpreter.model.PrgState;
import toyLanguageInterpreter.model.adt.MyDictionary;
import toyLanguageInterpreter.model.adt.MyIDictionary;
import toyLanguageInterpreter.model.adt.MyIHeap;
import toyLanguageInterpreter.model.adt.MyIList;
import toyLanguageInterpreter.model.values.RefValue;
import toyLanguageInterpreter.model.values.StringValue;
import toyLanguageInterpreter.model.values.Value;


import java.io.BufferedReader;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private final IRepository repo;
    boolean displayFlag;
    private ExecutorService executor;

    // constructor of Controller
    public Controller(IRepository repo){
        this.repo = repo;
        this.displayFlag = false;
    }

    // adding a program state to the repository (For the future multithreading)
    public void addProgram(PrgState state) {
        this.repo.add(state);
    }


    /**
     * it displays the ProgramState: the execution stack, the symbol table and the output
     */
    public String displayState(PrgState state){
        return state.toString();
    }

    public void setDisplayFlag(Boolean flag){
        this.displayFlag = flag;
    }


    public List<PrgState> removeCompletedPrograms(List<PrgState> inputProgramList) {
        return inputProgramList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }
    public void garbageCollectorWrapper() throws InterpreterException, ADTException {
        List<PrgState> programStateList = repo.getPrgList();
        PrgState oneState = programStateList.get(0);
        // garbage collector
        oneState.getHeap().setMap(
                garbageCollector(
                        getAddrFromSymTable(
                                programStateList.stream()
                                        .map(programState -> programState.getSymTable().values())
                                        .collect(Collectors.toList()),
                                oneState.getHeap().getMap()),
                        oneState.getHeap().getMap()));
    }
    public Map<Integer, toyLanguageInterpreter.model.values.Value> garbageCollector(Set<Integer> symTableAddr, Map<Integer, Value> heap){ // why set as symTableAddr? because the addresses from the symbol table are unique
        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void allSteps() throws MyException, RepoException, InterpreterException, ADTException, ControllerException {
        /*Resetting the log file in order to not have past executions of the program*/
        repo.resetLogFile();
        /*Printing the original program state*/
        repo.getPrgList().forEach(programState -> {
            try{
                this.repo.logPrgStateExec(programState);
            } catch (MyException | RepoException e) {
                System.out.println(e.getMessage());
            }
        });

        this.executor = Executors.newFixedThreadPool(2);
        // remove the completed programs
        List<PrgState> programStateList = removeCompletedPrograms(repo.getPrgList());
        while(!programStateList.isEmpty()){
            garbageCollectorWrapper();
            oneStepForAllPrograms(programStateList);
            // remove the completed programs
            programStateList = removeCompletedPrograms(repo.getPrgList());
        }
        executor.shutdownNow();
        // HERE the repository still contains at least one Completed Prg
        // and its List<PrgState> is not empty.
        repo.setPrgList(programStateList);
    }
    public void oneStepForAllPrograms(List<PrgState> programStateList) throws ControllerException {

        List<Callable<PrgState>> callList = programStateList.stream()
                .map((PrgState programState)->(Callable<PrgState>)(programState::oneStep))
                .toList();
        // start the execution of the callables
        // it returns the list of new created PrgStates(namely, threads)
        try {
            List<PrgState> newProgramList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            final var res = future.get();
                            return res;
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            return null;
                        }
                    })
                    .filter(p -> p != null)
                    .collect(Collectors.toList());
            if (!newProgramList.isEmpty())
                programStateList.add(newProgramList.get(0));
        } catch (Exception e) {
            throw new ControllerException(e.getMessage());
        }
        this.repo.setPrgList(programStateList);

        programStateList.stream().forEach(programState -> {
            try{
                this.repo.logPrgStateExec(programState);
            } catch (MyException | RepoException e) {
                System.out.println(e.getMessage());
            }
        });
        /*If the execution stack is empty, print in the command line how all looks at the end*/
        programStateList.stream()
                .filter(programState -> programState.getExeStack().isEmpty())
                .forEach(programState -> {
                    try{
                        System.out.println(programState.toString());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                });

    }
    public void resetProgramStates() throws MyException, RepoException {
        repo.resetCurrentState();
    }

    Set<Integer> getAddrFromSymTable(List<Collection<Value>> symbolTablesValues, Map<Integer, Value> heap){
        Set<Integer> toReturn = new TreeSet<>();
        symbolTablesValues
                .forEach(symbolTable -> symbolTable.stream()
                        .filter(v -> v instanceof RefValue)
                        .forEach(v -> {
                            while (v instanceof RefValue){
                                toReturn.add(((RefValue) v).getAddress());
                                v = heap.get(((RefValue) v).getAddress());
                            }
                        }));
        return toReturn;
    }

    public void typeCheck() throws InterpreterException, StatementException, ADTException, ExpressionException {
        repo.typeCheck();
    }

    // <editor-fold desc="Get Runtime States">
    public List<PrgState> getPrgList() {
        return repo.getPrgList();
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return this.repo.getPrgList().getFirst().getFileTable();
    }

    public MyIList<Value> getOutput(){
        return this.repo.getPrgList().getFirst().getOut();
    }

    public MyIHeap<Integer,Value> getHeapTable() {
        return this.repo.getPrgList().getFirst().getHeap();
    }
    // </editor-fold>

    public void oneStepForAllProgramsGUI() throws InterpreterException, ADTException, ControllerException {
        this.executor = Executors.newFixedThreadPool(2);
        List<PrgState> programStateList = removeCompletedPrograms(repo.getPrgList());
        if(!programStateList.isEmpty()) {
            garbageCollectorWrapper();
            oneStepForAllPrograms(programStateList);
            repo.setPrgList(programStateList);
        }
        else {
            throw new ControllerException("Controller: Execution has finished. Program state list is empty.");
        }
        executor.shutdown();
    }
}

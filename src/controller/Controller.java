package controller;

import Repository.IRepository;
import Repository.RepoException;
import exceptions.ADTException;
import exceptions.ControllerException;
import exceptions.InterpreterException;
import model.MyException;
import model.PrgState;
import model.values.RefValue;
import model.values.Value;


import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
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

    /**
     * it simply runs oneStep() while the execution stack of the current program is not empty
     * @throws InterpreterException if the execution stack is empty
     */
//    public void allSteps() throws InterpreterException {
//        ProgramState currentProgramState = repo.getCurrentProgram();
//        repo.logProgramStateExec(currentProgramState);
//        if(this.displayFlag){
//            System.out.println(displayState(currentProgramState));
//        }
//        while(!currentProgramState.getExecutionStack().isEmpty()){
//            try {
//                oneStep(currentProgramState);
//                repo.logProgramStateExec(currentProgramState);
//            } catch (Exception e) {
//                throw new InterpreterException(e.getMessage());
//            }
//            if(this.displayFlag)
//                System.out.println(displayState(currentProgramState));
//            MyHeapInterface<Value> auxiliaryHeap = new MyHeap<>();
//            auxiliaryHeap.setHeap(garbageCollector(getAddrFromSymTabke(currentProgramState.getSymbolTable().values(), currentProgramState.getHeap().getHeap()), currentProgramState.getHeap().getHeap()));
//            currentProgramState.getHeap().setHeap(auxiliaryHeap.getHeap());
//        }
//        // if nothing was displayed on the way, it prints it at the end
//        if(!this.displayFlag){
//            System.out.println(displayState(currentProgramState));
//        }
//    }

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
    public Map<Integer, model.values.Value> garbageCollector(Set<Integer> symTableAddr, Map<Integer, Value> heap){ // why set as symTableAddr? because the addresses from the symbol table are unique
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
}

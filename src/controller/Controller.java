package controller;

import Repository.IRepository;
import Repository.RepoException;
import exceptions.*;
import model.MyException;
import model.adt.MyHeap;
import model.adt.MyIHeap;
import model.statements.IStmt;
import model.PrgState;
import model.adt.MyIStack;
import model.values.RefValue;
import model.values.Value;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;


public class Controller {

    private IRepository repo;
    private boolean displayFLag;

    public Controller(IRepository repo) {
        this.repo = repo;
        this.displayFLag = false;
    }

    public void addProgram(PrgState prgState){
        this.repo.add(prgState);
    }

    private PrgState oneStep(PrgState state) throws MyException, ADTException, StatementException, ExpressionException, InterpreterException, FileNotFoundException, ControllerException {
        MyIStack<IStmt> stk=state.getExeStack();
        if(stk.isEmpty()) throw new ControllerException("prgstate stack is empty");
        IStmt crtStmt = stk.pop();
        return crtStmt.execute(state);
    }

    public void allStep() throws MyException, StatementException, ADTException, ExpressionException, InterpreterException, FileNotFoundException, RepoException, ControllerException {
        PrgState prg = repo.getCurrentState();
        repo.logPrgStateExec(prg);

        if (this.displayFLag) {
            System.out.println(displayState(prg));
        }

        while (!prg.getExeStack().isEmpty()) {
            try {
                oneStep(prg);
                repo.logPrgStateExec(prg);

            } catch (Exception e) {
                throw new ControllerException(e.getMessage());
            }
            if (this.displayFLag) {
                System.out.println(displayState(prg));
            }
            prg.getHeap().setMap(garbageCollector(getAddrFromSymTable(prg.getSymTable().values(), prg.getHeap().getMap()), prg.getHeap().getMap()));
        }

        if (!this.displayFLag) {
            System.out.println(displayState(prg));
        }
    }

    public String displayState(PrgState state){
        return state.toString();
    }

    public void setDisplayFlag(Boolean flag){
        this.displayFLag = flag;
    }
    public void resetProgramStates() throws MyException, RepoException {
        repo.resetCurrentState();
    }

    Map<Integer, Value> garbageCollector(Set<Integer> symTableAddr, Map<Integer, Value> heap){ // why set as symTableAddr? because the addresses from the symbol table are unique

        //heap.keySet().removeIf(symTableAddr::contains);

        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    Set<Integer> getAddrFromSymTable(Collection<Value> symbolTableValues, Map<Integer, Value> heap){
        Set<Integer> toReturn = new TreeSet<>();
        symbolTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .forEach(v -> {
                    while (v instanceof RefValue){
                        toReturn.add(((RefValue) v).getAddress());
                        v = heap.get(((RefValue) v).getAddress());
                    }
                });
        return toReturn;
    }

}

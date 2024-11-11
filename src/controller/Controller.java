package controller;

import Repository.IRepository;
import Repository.RepoException;
import exceptions.*;
import model.MyException;
import model.statements.IStmt;
import model.PrgState;
import model.adt.MyIStack;

import java.io.FileNotFoundException;


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

}

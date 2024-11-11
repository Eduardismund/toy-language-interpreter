package Repository;


import model.MyException;
import model.PrgState;

public interface IRepository {
    PrgState getCurrentState();
    void add(PrgState state);
    void logPrgStateExec(PrgState prgState) throws MyException, RepoException;
    void resetCurrentState() throws MyException, RepoException;


    }

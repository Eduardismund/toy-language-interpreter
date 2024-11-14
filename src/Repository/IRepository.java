package Repository;


import model.MyException;
import model.PrgState;

import java.util.List;

public interface IRepository {
    void add(PrgState state);
    void logPrgStateExec(PrgState prgState) throws MyException, RepoException;
    void resetCurrentState() throws MyException, RepoException;
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> prgList);
    PrgState getPrgStateWithId(int currentId);
    void resetLogFile() throws MyException, RepoException;
}

package toyLanguageInterpreter.Repository;


import toyLanguageInterpreter.exceptions.ADTException;
import toyLanguageInterpreter.exceptions.ExpressionException;
import toyLanguageInterpreter.exceptions.InterpreterException;
import toyLanguageInterpreter.exceptions.StatementException;
import toyLanguageInterpreter.exceptions.MyException;
import toyLanguageInterpreter.model.PrgState;

import java.util.List;

public interface IRepository {
    void add(PrgState state);
    void logPrgStateExec(PrgState prgState) throws MyException, RepoException;
    void resetCurrentState() throws MyException, RepoException;
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> prgList);
    PrgState getPrgStateWithId(int currentId);
    void resetLogFile() throws MyException, RepoException;
    void typeCheck() throws StatementException, InterpreterException, ADTException, ExpressionException;
}

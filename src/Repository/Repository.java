package Repository;


import model.MyException;
import model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Repository implements IRepository{
    private List<PrgState> Repo;
    private String logFilePath;

    public Repository(String logFilePath) throws RepoException {

        this.Repo = new ArrayList<>();
        this.logFilePath = logFilePath;
    }


    @Override
    public void add(PrgState state) {
        Repo.add(state);

    }

    @Override
    public void logPrgStateExec(PrgState prgState) throws MyException, RepoException {
        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            writer.println(prgState.toString());
            writer.flush();
            if(prgState.getExeStack().isEmpty()) {
                writer.close();
            }
        } catch (IOException io) {
            throw new RepoException("The output file cannot be opened!");
        }
    }

    @Override
    public void resetCurrentState() throws MyException, RepoException {
        if(Repo.isEmpty())
            throw new RepoException("Program state list is empty");
        Repo.get(0).resetProgramState();
    }


    @Override
    public void resetLogFile() throws MyException, RepoException {
        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            writer.println("");
            writer.flush();
            writer.close();

        } catch (IOException io) {
            throw new RepoException("The output file cannot be opened!");
        }
    }

    @Override
    public List<PrgState> getPrgList() {
        return this.Repo;
    }

    @Override
    public void setPrgList(List<PrgState> prgList) {
        Repo = prgList;
    }

    @Override
    public PrgState getPrgStateWithId(int currentId) {
        for(PrgState prg:Repo){
            if(prg.getId_Thread() == currentId){
                return prg;
            }
        }
        return null;
    }


}

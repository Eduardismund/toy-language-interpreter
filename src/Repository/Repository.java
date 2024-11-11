package Repository;


import model.MyException;
import model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

public class Repository implements IRepository{
    private LinkedList<PrgState> Repo;
    private String logFilePath;

    public Repository(String logFilePath) throws RepoException {

        this.Repo = new LinkedList<>();
        this.logFilePath = logFilePath;
    }

    @Override
    public PrgState getCurrentState() {
        return Repo.getFirst();
    }

    @Override
    public void add(PrgState state) {
        Repo.add(state);

    }

    @Override
    public void logPrgStateExec(PrgState prgState) throws MyException, RepoException {
        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            writer.println(this.getCurrentState().toString());
            writer.flush();
            if(prgState.getExeStack().isEmpty()) {
                writer.close();
            }
        } catch (IOException io) {
            throw new RepoException("The output file cannot be opened!");
        }
    }


    public void resetCurrentState() throws MyException, RepoException {
        Repo.get(0).resetProgramState();
    }
}

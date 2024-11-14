package view.commands;

import Repository.RepoException;
import controller.Controller;
import model.MyException;
import exceptions.ViewException;

public class RunCommand extends Command{

    private Controller controller;

    public RunCommand(String key, String description, Controller controller) {
        super(key,description);
        this.controller = controller;
    }

    @Override
    public void execute() throws ViewException {
        try{
            controller.allSteps();
        } catch (Exception exception){
            System.out.println(exception.getMessage());
        }
//
//        try{
//            controller.resetProgramStates();
//        } catch (MyException | RepoException e) {
//            System.out.println(e.getMessage());
//        }
    }

    public void resetProgramStates() throws MyException, RepoException {
        controller.resetProgramStates();
    }
}

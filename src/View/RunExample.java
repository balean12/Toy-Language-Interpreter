package View;

import Controller.Controller;
import Domain.Exception.MyException;


public class RunExample extends Command{
    private final Controller controller;
    public RunExample(String key, String description, Controller controller){
        super(key, description);
        this.controller = controller;
    }
    public Controller getController(){
        return  this.controller;
    }

    @Override
    public void execute() {
        try {
            controller.allSteps();
        }
        catch (MyException exception){
            System.out.println(exception.getMessage());
        }
    }
}

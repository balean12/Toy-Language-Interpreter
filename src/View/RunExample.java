package View;

import Controller.Controller;
import Domain.Exception.MyException;

import java.io.IOException;

public class RunExample extends Command{
    private Controller controller;
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

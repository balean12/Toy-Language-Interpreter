package View;

import Controller.Controller;
import Domain.ADTS.*;
import Domain.Exception.MyException;
import Domain.Exp.ArithExp;
import Domain.Exp.RelationalExp;
import Domain.Exp.ValueExp;
import Domain.Exp.VarExp;
import Domain.PrgState;
import Domain.Statement.*;
import Domain.Types.BoolType;
import Domain.Types.IntType;
import Domain.Types.StringType;
import Domain.Value.BoolValue;
import Domain.Value.IntValue;
import Domain.Value.StringValue;
import Domain.Value.Value;
import Repository.IRepository;
import Repository.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class Interpretor {
    public static void main(String[] args){
        //ArrayList<IStmt> allPrograms = new ArrayList<>(10);
        IStack<IStmt> exeStack1 = new MyStack<IStmt>();
        IDictionary<String , Value> symTable1 = new MyDict<String,Value>();
        IList<Value> out1 = new MyList<Value>();
        IDictionary<StringValue, BufferedReader> fileTable1 = new MyDict<StringValue,BufferedReader>();

        //int v;v=2;print(v);
       /* IStmt program1 = new CompStmt(
                new VarDeclStmt("v", new BoolType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));*/
        //int v;string file;file="test1.txt";openRFile(file);readFile(file,v);print(v);closeRFile(file);
        IStmt program1 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new VarDeclStmt("file", new StringType()),
                        new CompStmt(
                                new AssignStmt("file", new ValueExp(new StringValue("test.txt"))),
                                new CompStmt(
                                        new openRFile(new VarExp("file")),
                                        new CompStmt(
                                                new readFile(new VarExp("file"),"v"),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("v")),
                                                        new closeRFile(new VarExp("file"))
                                                )
                                        )
                                )
                        )
                )
        );
        PrgState prg1 = new PrgState(exeStack1,symTable1,out1,program1, fileTable1);
        IRepository repository1 = new Repository(prg1,"log1.txt");
        Controller controller1 = new Controller(repository1);

        //int a;int b;a=2+3*5;b=a+1;print(b)\\
        /*IStmt program2 = new CompStmt(
                new VarDeclStmt("a", new IntType()),
                new CompStmt(
                        new VarDeclStmt("b", new IntType()),
                        new CompStmt(
                                new AssignStmt("a",
                                        new ArithExp(
                                                new ValueExp(new IntValue(2)),
                                                new ArithExp(
                                                        new ValueExp(new IntValue(3)),
                                                        new ValueExp(new IntValue(2)),'*'), '+')),
                                new CompStmt(
                                        new AssignStmt("b",
                                                new ArithExp(
                                                        new VarExp("a"),
                                                        new ValueExp(new IntValue(1)), '+')),
                                        new PrintStmt(new VarExp("b"))))));*/
        //int v;string file;file="test1.txt";openRFile(file);readFile(file,v);print(v);closeRFile(file);
        //int a;int b;a=2+3*5;b=a+1;print(b)\\
        //int a; int b; string file; file = "test1.txt"; openRFile(file); readFile(file,a); readFile(file,b);a=a*2-3;b=a+1;print(b);closeRFile(file)
        IStack<IStmt> exeStack2 = new MyStack<IStmt>();
        IDictionary<String , Value> symTable2 = new MyDict<String,Value>();
        IList<Value> out2 = new MyList<Value>();
        IDictionary<StringValue, BufferedReader> fileTable2 = new MyDict<StringValue,BufferedReader>();
        IStmt program2 = new CompStmt(
                new VarDeclStmt("a", new IntType()),
                new CompStmt(
                        new VarDeclStmt("b", new IntType()),
                        new CompStmt(
                                new VarDeclStmt("file", new StringType()),
                                new CompStmt(
                                        new AssignStmt("file", new ValueExp(new StringValue("test.txt"))),
                                        new CompStmt(
                                                new openRFile( new VarExp("file")),
                                                new CompStmt(
                                                        new readFile(new VarExp("file"), "a"),
                                                        new CompStmt(
                                                                new readFile(new VarExp("file"), "b"),
                                                                new CompStmt(
                                                                        new AssignStmt("a",
                                                                                new ArithExp(
                                                                                    new ArithExp(
                                                                                            new VarExp("a"),
                                                                                            new ValueExp(new IntValue(2)), '*'),
                                                                                    new ValueExp(new IntValue(3)), '-')
                                                                                ),
                                                                        new CompStmt(
                                                                                new AssignStmt("b",
                                                                                        new ArithExp(
                                                                                                new VarExp("a"),
                                                                                                new ValueExp(new IntValue(1)), '+')
                                                                                        ),
                                                                                new CompStmt(
                                                                                        new PrintStmt(new VarExp("b")),
                                                                                        new closeRFile(new VarExp("file"))
                                                                                )
                                                                            )
                                                                        )
                                                                    )
                                                                )
                                                        )
                                                )
                                            )
                                        )
        );
        PrgState prg2 = new PrgState(exeStack2,symTable2,out2,program2, fileTable2);
        IRepository repository2 = new Repository(prg2,"log2.txt");
        Controller controller2 = new Controller(repository2);


        IStack<IStmt> exeStack3 = new MyStack<IStmt>();
        IDictionary<String , Value> symTable3 = new MyDict<String,Value>();
        IList<Value> out3 = new MyList<Value>();
        IDictionary<StringValue, BufferedReader> fileTable3 = new MyDict<StringValue,BufferedReader>();
        IStmt program3 = new CompStmt(
                new VarDeclStmt("varf", new StringType()),
                new CompStmt(
                        new AssignStmt("varf", new ValueExp(new StringValue("test.txt"))),
                        new CompStmt(
                                new openRFile(new VarExp("varf")),
                                new CompStmt(
                                        new VarDeclStmt("a", new IntType()),
                                        new CompStmt(
                                                new readFile(new VarExp("varf"), "a"),
                                                new CompStmt(
                                                        new IfStmt(
                                                                new RelationalExp(
                                                                        new ValueExp(new IntValue(10)),
                                                                        new VarExp("a"),
                                                                        ">"),
                                                                new PrintStmt(new ValueExp(new StringValue("a less than 10"))),
                                                                new PrintStmt(new ValueExp(new StringValue("a greater than 10")))),
                                                        new closeRFile(new VarExp("varf"))))))));
        PrgState prg3 = new PrgState(exeStack3,symTable3,out3,program3, fileTable3);
        IRepository repository3 = new Repository(prg3,"log3.txt");
        Controller controller3 = new Controller(repository3);
        /*PrgState prg2 = new PrgState(exeStack,symTable,out,program2, fileTable);
        IRepository repository2 = new Repository(prg2,"log2.txt");
        Controller controller2 = new Controller(repository2);
        //boolean a;int v;a=true; if a is true then v=2 else v = 3; print v;
        IStmt program3 = new CompStmt(
                new VarDeclStmt("a", new BoolType()),
                new CompStmt(
                        new VarDeclStmt("v", new IntType()),
                        new CompStmt(
                                new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(
                                        new IfStmt(
                                                new VarExp("a"),
                                                new AssignStmt("v", new ValueExp(new IntValue(2))),
                                                new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                        new PrintStmt(new VarExp("v"))))));
        PrgState prg3 = new PrgState(exeStack,symTable,out,program3, fileTable);
        IRepository repository3 = new Repository(prg3,"log3.txt");
        Controller controller3 = new Controller(repository3);*/
        //controller3.addProgram(prg3);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", program1.toString(), controller1));
        menu.addCommand(new RunExample("2", program2.toString(), controller2));
        menu.addCommand(new RunExample("3", program3.toString(), controller3));
        menu.show();


        /*allPrograms.add(program1);
        allPrograms.add(program2);
        allPrograms.add(program3);*/
        /*IStmt cloned = (IStmt) program1.deepCopy();
        System.out.println("Projects are loaded! Choose 0,1 or 2!");
        Scanner scanner = new Scanner(System.in);
        int userChoice = scanner.nextInt();
        while(userChoice <0 || userChoice > 2){
            System.out.println("Choose a 0,1 or 2!!");
            scanner = new Scanner(System.in);
            userChoice = scanner.nextInt();
        }

        PrgState programState = new PrgState(exeStack, symTable, out, allPrograms.get(userChoice));
        controller.addProgram(programState);
        try{
            System.out.println("Original program: \n" + allPrograms.get(userChoice).toString() + "\n");
            controller.allStep();
            System.out.println(controller.getAllSteps());
            System.out.println(controller.getOutput());
        }
        catch(MyException exception){
            System.out.println(exception.getMessage());
        }
    }*/
}}
package View;

import Controller.Controller;
import Domain.ADTS.*;
import Domain.Exp.*;
import Domain.PrgState;
import Domain.Statement.*;
import Domain.Types.IntType;
import Domain.Types.RefType;
import Domain.Types.StringType;
import Domain.Value.IntValue;
import Domain.Value.StringValue;
import Domain.Value.Value;
import Repository.IRepository;
import Repository.Repository;
import java.io.BufferedReader;

public class Interpretor {
    public static void main(String[] args){
        //ArrayList<IStmt> allPrograms = new ArrayList<>(10);
        IStack<IStmt> exeStack1 = new MyStack<IStmt>();
        IDictionary<String , Value> symTable1 = new MyDict<String,Value>();
        IList<Value> out1 = new MyList<Value>();
        IDictionary<StringValue, BufferedReader> fileTable1 = new MyDict<StringValue,BufferedReader>();
        IHeap<Integer, Value> heap1 = new MyHeap<Integer,Value>();

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
        PrgState prg1 = new PrgState(exeStack1,symTable1,out1,program1, fileTable1,heap1);
        IRepository repository1 = new Repository("log1.txt");
        Controller controller1 = new Controller(repository1);
        controller1.addProgram(prg1);

        /*PrgState prg1 = new PrgState(exeStack1,symTable1,out1,program1, fileTable1,heap1);
        IRepository repository1 = new Repository(prg1,"log1.txt");
        Controller controller1 = new Controller(repository1);*/

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
        IHeap<Integer, Value> heap2 = new MyHeap<Integer,Value>();
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
        PrgState prg2 = new PrgState(exeStack2,symTable2,out2,program2, fileTable2, heap2);
        IRepository repository2 = new Repository("log2.txt");
        Controller controller2 = new Controller(repository2);
        controller2.addProgram(prg2);


        IStack<IStmt> exeStack3 = new MyStack<IStmt>();
        IDictionary<String , Value> symTable3 = new MyDict<String,Value>();
        IList<Value> out3 = new MyList<Value>();
        IDictionary<StringValue, BufferedReader> fileTable3 = new MyDict<StringValue,BufferedReader>();
        IHeap<Integer, Value> heap3 = new MyHeap<Integer,Value>();
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
                                                                        new ValueExp(new IntValue(5)),
                                                                        new VarExp("a"),
                                                                        ">"),
                                                                new PrintStmt(new ValueExp(new StringValue("a less than 10"))),
                                                                new PrintStmt(new ValueExp(new StringValue("a greater than 10")))),
                                                        new closeRFile(new VarExp("varf"))))))));
        PrgState prg3 = new PrgState(exeStack3,symTable3,out3,program3, fileTable3, heap3);
        IRepository repository3 = new Repository("log3.txt");
        Controller controller3 = new Controller(repository3);
        controller3.addProgram(prg3);
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

        // Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        IStack<IStmt> exeStack4 = new MyStack<>();
        IDictionary<String, Value> symbolTable4 = new MyDict<>();
        IDictionary<StringValue, BufferedReader> fileTable4 = new MyDict<StringValue,BufferedReader>();
        IHeap<Integer, Value> heap4 = new MyHeap<Integer,Value>();
        IList<Value> out4 = new MyList<>();


        IStmt program4 = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v",
                                new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new PrintStmt(new readHeap(new VarExp("v"))),
                                new CompStmt(
                                        new WriteHeap("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(
                                                new ArithExp(
                                                        new readHeap(new VarExp("v")),
                                                        new ValueExp(new IntValue(5)),
                                                        '+')
                                        )
                                )
                        )
                ));
        PrgState prg4 = new PrgState(exeStack4,symbolTable4,out4,program4, fileTable4, heap4);
        IRepository repository4 = new Repository("log4.txt");
        Controller controller4 = new Controller(repository4);
        controller4.addProgram(prg4);


        IStack<IStmt> exeStack5 = new MyStack<>();
        IDictionary<String, Value> symbolTable5 = new MyDict<>();
        IDictionary<StringValue, BufferedReader> fileTable5 = new MyDict<StringValue,BufferedReader>();
        IHeap<Integer, Value> heap5 = new MyHeap<Integer,Value>();
        IList<Value> out5 = new MyList<>();
        ////Ref int v;new(v,20);Ref Ref int a; new(a,v); writeheap(v,30);print(rH(rH(a)))
        IStmt program5 = new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(
                                        new NewStmt("a", new VarExp("v")),
                                        new CompStmt(
                                                new WriteHeap("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new readHeap(new readHeap(new VarExp("a"))))
                                        )
                                )
                        )
                )
        );
        PrgState prg5 = new PrgState(exeStack5,symbolTable5,out5,program5, fileTable5, heap5);
        IRepository repository5 = new Repository("log5.txt");
        Controller controller5 = new Controller(repository5);
        controller5.addProgram(prg5);

        IStack<IStmt> exeStack6 = new MyStack<>();
        IDictionary<String, Value> symbolTable6 = new MyDict<>();
        IDictionary<StringValue, BufferedReader> fileTable6 = new MyDict<StringValue,BufferedReader>();
        IHeap<Integer, Value> heap6 = new MyHeap<Integer,Value>();
        IList<Value> out6 = new MyList<>();
        //int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        IStmt program6 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(
                                new WhileStmt(new CompStmt(
                                        new PrintStmt(new VarExp("v")),
                                        new AssignStmt("v", new ArithExp(new VarExp("v"), new ValueExp(new IntValue(1)), '-'))
                                ),
                                        new RelationalExp(new VarExp("v"), new ValueExp(new IntValue(0)), ">" )),
                                        //new ArithExp(new VarExp("v"), new ValueExp(new IntValue(1)), '-')),
                                new PrintStmt(new VarExp("v"))
                        )
                )
        );
        PrgState prg6 = new PrgState(exeStack6,symbolTable6,out6,program6, fileTable6, heap6);
        IRepository repository6 = new Repository("log6.txt");
        Controller controller6 = new Controller(repository6);
        controller6.addProgram(prg6);

        IStack<IStmt> exeStack7 = new MyStack<>();
        IDictionary<String, Value> symbolTable7 = new MyDict<>();
        IDictionary<StringValue, BufferedReader> fileTable7 = new MyDict<StringValue,BufferedReader>();
        IHeap<Integer, Value> heap7 = new MyHeap<Integer,Value>();
        IList<Value> out7 = new MyList<>();
        IStmt program7  = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(
                                new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(
                                        new NewStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(
                                                new ForkStatement(
                                                        new CompStmt(
                                                                new WriteHeap("a", new ValueExp(new IntValue(30))),
                                                                new CompStmt(
                                                                        new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                                        new CompStmt(
                                                                                new PrintStmt(new VarExp("v")),
                                                                                new PrintStmt(new readHeap(new VarExp("a")))
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new readHeap(new VarExp("a")))
                                                )
                                        )
                                )
                        )));

        PrgState prg7 = new PrgState(exeStack7,symbolTable7,out7,program7, fileTable7, heap7);
        IRepository repository7 = new Repository("log7.txt");
        Controller controller7 = new Controller(repository7);
        controller7.addProgram(prg7);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", program1.toString(), controller1));
        menu.addCommand(new RunExample("2", program2.toString(), controller2));
        menu.addCommand(new RunExample("3", program3.toString(), controller3));
        menu.addCommand(new RunExample("4", program4.toString(), controller4));
        menu.addCommand(new RunExample("5", program5.toString(), controller5));
        menu.addCommand(new RunExample("6", program6.toString(), controller6));
        menu.addCommand(new RunExample("7", program7.toString(), controller7));
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
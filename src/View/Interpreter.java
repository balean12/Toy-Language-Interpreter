package View;

import Controller.Controller;
import Domain.ADTS.*;
import Domain.Exp.*;
import Domain.ProgramState;
import Domain.Statement.*;
import Domain.Types.BooleanType;
import Domain.Types.IntegerType;
import Domain.Types.ReferenceType;
import Domain.Types.StringType;
import Domain.Value.IntValue;
import Domain.Value.StringValue;
import Domain.Value.IValue;
import Repository.IRepository;
import Repository.Repository;

import java.io.BufferedReader;

public class Interpreter {

    public static void main(String[] args){

        IStack<IStmt> exeStack1 = new MyStack<>();
        IDictionary<String , IValue> symTable1 = new MyDictionary<>();
        IList<IValue> out1 = new MyList<>();
        IDictionary<StringValue, BufferedReader> fileTable1 = new MyDictionary<>();
        IHeap<Integer, IValue> heap1 = new MyHeap<>();

        IStmt program1 = new CompoundStatement(
                new VariableDeclarationStatement("v", new IntegerType()),
                new CompoundStatement(
                        new VariableDeclarationStatement("file", new StringType()),
                        new CompoundStatement(
                                new AssignStatement("file", new ValueExpression(new StringValue("test.txt"))),
                                new CompoundStatement(
                                        new OpenRFileStatement(new VariableExpression("file")),
                                        new CompoundStatement(
                                                new ReadFileStatement(new VariableExpression("file"),"v"),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("v")),
                                                        new CloseRFileStatement(new VariableExpression("file"))
                                                )
                                        )
                                )
                        )
                )
        );
        ProgramState prg1 = new ProgramState(exeStack1,symTable1,out1,program1, fileTable1,heap1);
        IRepository repository1 = new Repository("log1.txt");
        Controller controller1 = new Controller(repository1);
        controller1.addProgram(prg1);

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
        IStack<IStmt> exeStack2 = new MyStack<>();
        IDictionary<String , IValue> symTable2 = new MyDictionary<>();
        IList<IValue> out2 = new MyList<>();
        IDictionary<StringValue, BufferedReader> fileTable2 = new MyDictionary<>();
        IHeap<Integer, IValue> heap2 = new MyHeap<>();
        IStmt program2 = new CompoundStatement(
                new VariableDeclarationStatement("a", new BooleanType()),
                new CompoundStatement(
                        new VariableDeclarationStatement("b", new IntegerType()),
                        new CompoundStatement(
                                new VariableDeclarationStatement("file", new StringType()),
                                new CompoundStatement(
                                        new AssignStatement("file", new ValueExpression(new StringValue("test.txt"))),
                                        new CompoundStatement(
                                                new OpenRFileStatement( new VariableExpression("file")),
                                                new CompoundStatement(
                                                        new ReadFileStatement(new VariableExpression("file"), "a"),
                                                        new CompoundStatement(
                                                                new ReadFileStatement(new VariableExpression("file"), "b"),
                                                                new CompoundStatement(
                                                                        new AssignStatement("a",
                                                                                new ArithmeticExpression(
                                                                                    new ArithmeticExpression(
                                                                                            new VariableExpression("a"),
                                                                                            new ValueExpression(new IntValue(2)), '*'),
                                                                                    new ValueExpression(new IntValue(3)), '-')
                                                                                ),
                                                                        new CompoundStatement(
                                                                                new AssignStatement("b",
                                                                                        new ArithmeticExpression(
                                                                                                new VariableExpression("a"),
                                                                                                new ValueExpression(new IntValue(1)), '+')
                                                                                        ),
                                                                                new CompoundStatement(
                                                                                        new PrintStatement(new VariableExpression("b")),
                                                                                        new CloseRFileStatement(new VariableExpression("file"))
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
        ProgramState prg2 = new ProgramState(exeStack2,symTable2,out2,program2, fileTable2, heap2);
        IRepository repository2 = new Repository("log2.txt");
        Controller controller2 = new Controller(repository2);
        controller2.addProgram(prg2);


        IStack<IStmt> exeStack3 = new MyStack<>();
        IDictionary<String , IValue> symTable3 = new MyDictionary<>();
        IList<IValue> out3 = new MyList<>();
        IDictionary<StringValue, BufferedReader> fileTable3 = new MyDictionary<>();
        IHeap<Integer, IValue> heap3 = new MyHeap<>();
        IStmt program3 = new CompoundStatement(
                new VariableDeclarationStatement("varf", new StringType()),
                new CompoundStatement(
                        new AssignStatement("varf", new ValueExpression(new StringValue("test.txt"))),
                        new CompoundStatement(
                                new OpenRFileStatement(new VariableExpression("varf")),
                                new CompoundStatement(
                                        new VariableDeclarationStatement("a", new IntegerType()),//HERERERERE
                                        new CompoundStatement(
                                                new ReadFileStatement(new VariableExpression("varf"), "a"),
                                                new CompoundStatement(
                                                        new IfStatement(
                                                                new RelationalExpression(
                                                                        new ValueExpression(new IntValue(5)),
                                                                        new VariableExpression("a"),
                                                                        ">"),
                                                                new PrintStatement(new ValueExpression(new StringValue("a less than 10"))),
                                                                new PrintStatement(new ValueExpression(new StringValue("a greater than 10")))),
                                                        new CloseRFileStatement(new VariableExpression("varf"))))))));
        ProgramState prg3 = new ProgramState(exeStack3,symTable3,out3,program3, fileTable3, heap3);
        IRepository repository3 = new Repository("log3.txt");
        Controller controller3 = new Controller(repository3);
        controller3.addProgram(prg3);

        // Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        IStack<IStmt> exeStack4 = new MyStack<>();
        IDictionary<String, IValue> symbolTable4 = new MyDictionary<>();
        IDictionary<StringValue, BufferedReader> fileTable4 = new MyDictionary<>();
        IHeap<Integer, IValue> heap4 = new MyHeap<>();
        IList<IValue> out4 = new MyList<>();


        IStmt program4 = new CompoundStatement(
                new VariableDeclarationStatement("v", new ReferenceType(new IntegerType())),
                new CompoundStatement(
                        new NewStatement("v",
                                new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                new CompoundStatement(
                                        new WriteHeapStatement("v", new ValueExpression(new IntValue(30))),
                                        new PrintStatement(
                                                new ArithmeticExpression(
                                                        new ReadHeapExpression(new VariableExpression("v")),
                                                        new ValueExpression(new IntValue(5)),
                                                        '+')
                                        )
                                )
                        )
                ));
        ProgramState prg4 = new ProgramState(exeStack4,symbolTable4,out4,program4, fileTable4, heap4);
        IRepository repository4 = new Repository("log4.txt");
        Controller controller4 = new Controller(repository4);
        controller4.addProgram(prg4);


        IStack<IStmt> exeStack5 = new MyStack<>();
        IDictionary<String, IValue> symbolTable5 = new MyDictionary<>();
        IDictionary<StringValue, BufferedReader> fileTable5 = new MyDictionary<>();
        IHeap<Integer, IValue> heap5 = new MyHeap<>();
        IList<IValue> out5 = new MyList<>();
        ////Ref int v;new(v,20);Ref Ref int a; new(a,v); writeheap(v,30);print(rH(rH(a)))
        IStmt program5 = new CompoundStatement(
                new VariableDeclarationStatement("v", new ReferenceType(new IntegerType())),
                new CompoundStatement(
                        new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntegerType()))),
                                new CompoundStatement(
                                        new NewStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new WriteHeapStatement("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))))
                                        )
                                )
                        )
                )
        );
        ProgramState prg5 = new ProgramState(exeStack5,symbolTable5,out5,program5, fileTable5, heap5);
        IRepository repository5 = new Repository("log5.txt");
        Controller controller5 = new Controller(repository5);
        controller5.addProgram(prg5);

        IStack<IStmt> exeStack6 = new MyStack<>();
        IDictionary<String, IValue> symbolTable6 = new MyDictionary<>();
        IDictionary<StringValue, BufferedReader> fileTable6 = new MyDictionary<>();
        IHeap<Integer, IValue> heap6 = new MyHeap<>();
        IList<IValue> out6 = new MyList<>();
        //int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        IStmt program6 = new CompoundStatement(
                new VariableDeclarationStatement("v", new IntegerType()),
                new CompoundStatement(
                        new AssignStatement("v", new ValueExpression(new IntValue(4))),
                        new CompoundStatement(
                                new WhileStatement(new CompoundStatement(
                                        new PrintStatement(new VariableExpression("v")),
                                        new AssignStatement("v", new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(1)), '-'))
                                ),
                                        new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)), ">" )),
                                        //new ArithExp(new VarExp("v"), new ValueExp(new IntValue(1)), '-')),
                                new PrintStatement(new VariableExpression("v"))
                        )
                )
        );
        ProgramState prg6 = new ProgramState(exeStack6,symbolTable6,out6,program6, fileTable6, heap6);
        IRepository repository6 = new Repository("log6.txt");
        Controller controller6 = new Controller(repository6);
        controller6.addProgram(prg6);

        IStack<IStmt> exeStack7 = new MyStack<>();
        IDictionary<String, IValue> symbolTable7 = new MyDictionary<>();
        IDictionary<StringValue, BufferedReader> fileTable7 = new MyDictionary<>();
        IHeap<Integer, IValue> heap7 = new MyHeap<>();
        IList<IValue> out7 = new MyList<>();
        IStmt program7  = new CompoundStatement(
                new VariableDeclarationStatement("v", new IntegerType()),
                new CompoundStatement(
                        new VariableDeclarationStatement("a", new ReferenceType(new IntegerType())),
                        new CompoundStatement(
                                new AssignStatement("v", new ValueExpression(new IntValue(10))),
                                new CompoundStatement(
                                        new NewStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(
                                                new ForkStatement(
                                                        new CompoundStatement(
                                                                new WriteHeapStatement("a", new ValueExpression(new IntValue(30))),
                                                                new CompoundStatement(
                                                                        new AssignStatement("v", new ValueExpression(new IntValue(32))),
                                                                        new CompoundStatement(
                                                                                new PrintStatement(new VariableExpression("v")),
                                                                                new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                                                )
                                        )
                                )
                        )));

        ProgramState prg7 = new ProgramState(exeStack7,symbolTable7,out7,program7, fileTable7, heap7);
        IRepository repository7 = new Repository("log7.txt");
        Controller controller7 = new Controller(repository7);
        controller7.addProgram(prg7);

        IStack<IStmt> exeStack8 = new MyStack<>();
        IDictionary<String, IValue> symbolTable8 = new MyDictionary<>();
        IDictionary<StringValue, BufferedReader> fileTable8 = new MyDictionary<>();
        IHeap<Integer, IValue> heap8 = new MyHeap<>();
        IList<IValue> out8 = new MyList<>();
        IStmt program8  = new CompoundStatement(
                new VariableDeclarationStatement("v", new IntegerType()),
                new CompoundStatement(
                        new VariableDeclarationStatement("a", new ReferenceType(new IntegerType())),
                        new CompoundStatement(
                                new AssignStatement("v", new ValueExpression(new IntValue(10))),
                                new CompoundStatement(
                                        new NewStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(
                                                new ForkStatement(
                                                        new CompoundStatement(
                                                                new WriteHeapStatement("a", new ValueExpression(new IntValue(30))),
                                                                new CompoundStatement(
                                                                        new AssignStatement("v", new ValueExpression(new IntValue(32))),
                                                                        new CompoundStatement(
                                                                                new PrintStatement(new VariableExpression("v")),
                                                                                new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new CompoundStatement(
                                                        new ForkStatement(new PrintStatement(new VariableExpression("v"))),
                                                        new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))))
                                                )
                                        )
                                )
                        )));
        ProgramState prg8 = new ProgramState(exeStack8,symbolTable8,out8,program8, fileTable8, heap8);
        IRepository repository8 = new Repository("log8.txt");
        Controller controller8 = new Controller(repository8);
        controller8.addProgram(prg8);
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", program1.toString(), controller1));
        menu.addCommand(new RunExample("2", program2.toString(), controller2));
        menu.addCommand(new RunExample("3", program3.toString(), controller3));
        menu.addCommand(new RunExample("4", program4.toString(), controller4));
        menu.addCommand(new RunExample("5", program5.toString(), controller5));
        menu.addCommand(new RunExample("6", program6.toString(), controller6));
        menu.addCommand(new RunExample("7", program7.toString(), controller7));
        menu.addCommand(new RunExample("8", program8.toString(), controller8));
        menu.show();
        /*allPrograms.add(program1);
        allPrograms.add(program2);
        allPrograms.add(program3);
        IStmt cloned = (IStmt) program1.deepCopy();
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
        }*/
    }
}
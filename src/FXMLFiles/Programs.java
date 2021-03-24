package FXMLFiles;

import Domain.Exp.*;
import Domain.Statement.*;
import Domain.Types.IntegerType;
import Domain.Types.ReferenceType;
import Domain.Types.StringType;
import Domain.Value.IntValue;
import Domain.Value.StringValue;

import java.util.ArrayList;

public class Programs {
    private final ArrayList<IStmt> programs;
    public Programs() {
        programs = new ArrayList<>();
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
        IStmt program2 = new CompoundStatement(
                new VariableDeclarationStatement("a", new IntegerType()),
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

        IStmt program3 = new CompoundStatement(
                new VariableDeclarationStatement("varf", new StringType()),
                new CompoundStatement(
                        new AssignStatement("varf", new ValueExpression(new StringValue("test.txt"))),
                        new CompoundStatement(
                                new OpenRFileStatement(new VariableExpression("varf")),
                                new CompoundStatement(
                                        new VariableDeclarationStatement("a", new IntegerType()),
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
                                //new ArithmeticExpression(new VariableExp("v"), new ValueExp(new IntValue(1)), '-')),
                                new PrintStatement(new VariableExpression("v"))
                        )
                )
        );

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
        // Ref int a; new(a,20);
        //(for(v=0;v<3;v=v+1) fork(print(v);v=v*rh(a)));
        //print(rh(a))
        IStmt program12 = new CompoundStatement(
                new VariableDeclarationStatement("a", new ReferenceType(new IntegerType())),
                new CompoundStatement(
                        new NewStatement("a", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new ForStatement("v",
                                        new ValueExpression(new IntValue(0)),
                                        new ValueExpression(new IntValue(3)),
                                        new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(1)), '+'),
                                        new ForkStatement(
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("v")),
                                                        new AssignStatement("v",
                                                                new ArithmeticExpression(
                                                                        new VariableExpression("v"),
                                                                        new ReadHeapExpression(new VariableExpression("a")),
                                                                        '*'))
                                                )
                                        )
                                ),
                                new PrintStatement(new ReadHeapExpression(new VariableExpression("a"))))));
        //Ref int v1; int cnt;
        //new(v1,1);createSemaphore(cnt,rH(v1));
        //fork( acquire(cnt); wh(v1,rh(v1)*10); print(rh(v1)); release(cnt) );
        //fork( acquire(cnt); wh(v1,rh(v1)*10); wh(v1,rh(v1)*2); print(rh(v1));release(cnt) );
        //acquire(cnt);
        //print(rh(v1)-1);
        //release(cnt)

        /*Ref int v1; int cnt;
        new(v1,1);createSemaphore(cnt,rH(v1));
        fork(acquire(cnt);wh(v1,rh(v1)*10));print(rh(v1));release(cnt));
        fork(acquire(cnt);wh(v1,rh(v1)*10));wh(v1,rh(v1)*2));print(rh(v1));release(cnt));
        acquire(cnt);
        print(rh(v1)-1);
        release(cnt)
        The final Out should be {10,200,9} or {10,9,200}.*/

        IStmt program10 = new CompoundStatement(
                new VariableDeclarationStatement("v1", new ReferenceType(new IntegerType())),
                new CompoundStatement(
                        new VariableDeclarationStatement("cnt", new IntegerType()),
                        new CompoundStatement(
                                new NewStatement("v1", new ValueExpression(new IntValue(1))),
                                new CompoundStatement(
                                        new CreateSemaphoreStatement("cnt", new ReadHeapExpression(new VariableExpression("v1"))),
                                        new CompoundStatement(
                                                new ForkStatement(
                                                        new CompoundStatement(
                                                                new Acquire("cnt"),
                                                                new CompoundStatement(
                                                                        new WriteHeapStatement("v1",
                                                                                new ArithmeticExpression(
                                                                                        new ReadHeapExpression(new VariableExpression("v1")),
                                                                                        new ValueExpression(new IntValue(10)),
                                                                                        '*')),
                                                                        new CompoundStatement(
                                                                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v1"))),
                                                                                new Release("cnt"))))),
                                                new CompoundStatement(
                                                        new ForkStatement(
                                                                new CompoundStatement(
                                                                        new Acquire("cnt"),
                                                                        new CompoundStatement(
                                                                                new WriteHeapStatement("v1",
                                                                                        new ArithmeticExpression(
                                                                                                new ReadHeapExpression(new VariableExpression("v1")),
                                                                                                new ValueExpression(new IntValue(10)),
                                                                                                '*')),
                                                                                new CompoundStatement(
                                                                                        new WriteHeapStatement("v1",
                                                                                                new ArithmeticExpression(
                                                                                                        new ReadHeapExpression(new VariableExpression("v1")),
                                                                                                        new ValueExpression(new IntValue(2)),
                                                                                                        '*')),
                                                                                        new CompoundStatement(
                                                                                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v1"))),
                                                                                                new Release("cnt")))))),
                                                        new CompoundStatement(
                                                                new Acquire("cnt"),
                                                                new CompoundStatement(
                                                                        new PrintStatement(new ArithmeticExpression(
                                                                                new ReadHeapExpression(new VariableExpression("v1")),
                                                                                new ValueExpression(new IntValue(1)),
                                                                                '-')),
                                                                        new Release("cnt")))))))));
        this.programs.add(program1);
        this.programs.add(program2);
        this.programs.add(program3);
        this.programs.add(program4);
        this.programs.add(program5);
        this.programs.add(program6);
        this.programs.add(program7);
        this.programs.add(program8);
        this.programs.add(program10);

    }
    public ArrayList<IStmt> getAllPrograms() {
        return this.programs;
    }
}

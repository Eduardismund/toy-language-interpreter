package toyLanguageInterpreter.view.commands;

//import CountDownLatch.AwaitStatement;
//import CountDownLatch.CountdownStatement;
//import CountDownLatch.NewLatchStatement;




import javafx.util.Pair;
import statement.ConditionalAssignment;
import statement.ForStatement;
import statement.WaitStatement;
import toyLanguageInterpreter.model.expressions.*;
import toyLanguageInterpreter.model.statements.*;
import toyLanguageInterpreter.model.statements.CountDownLatchStatements.AwaitStatement;
import toyLanguageInterpreter.model.statements.CountDownLatchStatements.CountdownStatement;
import toyLanguageInterpreter.model.statements.CountDownLatchStatements.NewLatchStatement;
import toyLanguageInterpreter.model.statements.fileStatements.CloseReadFileStmt;
import toyLanguageInterpreter.model.statements.fileStatements.OpenFileStmt;
import toyLanguageInterpreter.model.statements.fileStatements.ReadFileStatement;
import toyLanguageInterpreter.model.statements.heapStatements.HeapAllocationStmt;
import toyLanguageInterpreter.model.statements.heapStatements.HeapWritingStmt;
import toyLanguageInterpreter.model.types.BoolType;
import toyLanguageInterpreter.model.types.IntType;
import toyLanguageInterpreter.model.types.RefType;
import toyLanguageInterpreter.model.types.StringType;
import toyLanguageInterpreter.model.values.BoolValue;
import toyLanguageInterpreter.model.values.IntValue;
import toyLanguageInterpreter.model.values.StringValue;
import toyLanguageInterpreter.model.values.Value;

import java.util.Arrays;


public class Examples {

    private static IStmt createExample10() {
        return new CompStmt(
                new VarDeclStmt(new IntType(), "v"),
                new CompStmt(
                        new VarDeclStmt(new RefType(new IntType()), "a"),
                        new CompStmt(
                                new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(
                                        new HeapAllocationStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(
                                                new ForkStmt(
                                                        new CompStmt(
                                                                new HeapWritingStmt("a", new ValueExp(new IntValue(30))),
                                                                new CompStmt(
                                                                        new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                                        new CompStmt(
                                                                                new PrintStmt(new VarExp("v")),
                                                                                new PrintStmt(new HeapReadingExp(new VarExp("a")))
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new HeapReadingExp(new VarExp("a")))
                                                )
                                        )
                                )
                        )
                )
        );
    }
    //Example:  int v; v=4; (while (v>0) print(v);v=v-1);print(v)
    private static IStmt createExample9(){
        return new CompStmt(
                new VarDeclStmt(new IntType(), "v"),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(
                                new WhileStmt(new RelExp(">", new VarExp("v"), new ValueExp(new IntValue(0))),
                                        new CompStmt(
                                                new PrintStmt(new VarExp("v")),new AssignStmt("v", new ArithExp(new VarExp("v"), new ValueExp(new IntValue(1)), 2)))),
                                new PrintStmt(new VarExp("v"))

                        )
                )
        );
    }


    //Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
    private static IStmt createExample8(){
        return new CompStmt(
                new VarDeclStmt(new RefType(new IntType()), "v"),
                new CompStmt(
                        new HeapAllocationStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new PrintStmt(new HeapReadingExp(new VarExp("v"))),
                                new CompStmt(
                                        new HeapWritingStmt("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp(new HeapReadingExp(new VarExp("v")), new ValueExp(new IntValue(5)), 1))

                                )
                        )
                )
        );
    }


    //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
    private static IStmt createExample7(){
        return new CompStmt(
                new VarDeclStmt(new RefType(new IntType()), "v"),
                new CompStmt(
                        new HeapAllocationStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt(new RefType(new RefType( new IntType())), "a"),
                                new CompStmt(
                                        new HeapAllocationStmt("a", new VarExp("v")),
                                        new CompStmt(
                                                new PrintStmt(new HeapReadingExp(new VarExp("v"))),
                                                new PrintStmt(new ArithExp(new HeapReadingExp(new HeapReadingExp(new VarExp("a"))), new ValueExp(new IntValue(5)), 1))
                                                //new NopStmt()
                                        )
                                )

                        )
                )
        );
    }

    // Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
    private static IStmt createExample6(){
        return new CompStmt(
                new VarDeclStmt(new RefType(new IntType()), "v"),
                new CompStmt(
                        new HeapAllocationStmt("v",new ValueExp(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt(new RefType(new RefType(new IntType())), "a"),
                                new CompStmt(
                                        new HeapAllocationStmt("a",new VarExp("v")),
                                        new CompStmt(
                                                new PrintStmt(new VarExp("v")),
                                                new PrintStmt(new VarExp("a"))
                                        )
                                )

                        )
                )
        );
    }

    private static IStmt createExample5(){
        return new CompStmt(
                new VarDeclStmt(new StringType(), "varf"),
                new CompStmt(
                        new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(
                                new OpenFileStmt(new VarExp("varf")),
                                new CompStmt(
                                        new VarDeclStmt(new IntType(), "varc"),
                                        new CompStmt(
                                                new ReadFileStatement("varc", new VarExp("varf")),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(
                                                                new ReadFileStatement("varc", new VarExp("varf")),
                                                                new CompStmt(
                                                                        new PrintStmt(new VarExp("varc")),
                                                                        new CloseReadFileStmt(new VarExp("varf"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

    }

    private static IStmt createExample4() {
        return new CompStmt(new VarDeclStmt(new BoolType(), "b"),
                new CompStmt(new VarDeclStmt(new IntType(), "c"),
                        new CompStmt(
                                new AssignStmt("b", new ValueExp(new BoolValue(true))),
                                new IfStmt(
                                        new LogicExp(new VarExp("b"), new ValueExp(new BoolValue(true)), "&&"),
                                        new AssignStmt("c", new ArithExp(new ValueExp(new IntValue(0)), new ValueExp(new IntValue(4)), 1)),
                                        new AssignStmt("c", new ArithExp(new ValueExp(new IntValue(0)), new ValueExp(new IntValue(2)), 1))
                                )
                        )
                ));
    }

    private static IStmt createExample1() {
        return new CompStmt(new VarDeclStmt(new IntType(), "v"),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));
    }

    private static IStmt createExample2() {
        return new CompStmt(new VarDeclStmt(new IntType(), "a"),
                new CompStmt(new VarDeclStmt(new IntType(), "b"),
                        new CompStmt(new AssignStmt("a", new ArithExp(new ValueExp(new IntValue(2)), new ArithExp(new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)), 3),1)),
                                new CompStmt(new AssignStmt("b", new ArithExp(new VarExp("a"), new ValueExp(new IntValue(1)),1)),
                                        new PrintStmt(new VarExp("b"))))));
    }

    private static IStmt createExample3() {
        return new CompStmt(new VarDeclStmt(new BoolType(), "a"),
                new CompStmt(new VarDeclStmt(new IntType(), "v"),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),
                                        new AssignStmt("v", new ValueExp(new IntValue(2))),
                                        new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                        new PrintStmt(new VarExp("v"))))));
    }

    private static IStmt createExample11() {
        return new CompStmt(new VarDeclStmt(new RefType(new IntType()), "a"),
                new CompStmt(new VarDeclStmt(new RefType(new IntType()), "b"),
                        new CompStmt(new VarDeclStmt(new IntType(), "v"),
                                new CompStmt(new HeapAllocationStmt("a",new ValueExp(new IntValue(0))),
                                        new CompStmt(new HeapAllocationStmt("b",new ValueExp(new IntValue(0))),
                                                new CompStmt(new HeapWritingStmt("a", new ValueExp(new IntValue(1))),
                                                        new CompStmt(new HeapWritingStmt("b", new ValueExp(new IntValue(2))),
                                                                new CompStmt(new CondAssignmentStatement("v", new RelExp("<",new HeapReadingExp(new VarExp("a")), new HeapReadingExp(new VarExp("b"))), new ValueExp(new IntValue(100)), new ValueExp(new IntValue(200))),
                                                                        new CompStmt(new PrintStmt(new VarExp("v" )),
                                                                                new CompStmt(new CondAssignmentStatement("v", new RelExp(">",new ArithExp(new HeapReadingExp(new VarExp("b")), new ValueExp(new IntValue(2)), 2), new HeapReadingExp(new VarExp("a"))), new ValueExp(new IntValue(100)), new ValueExp(new IntValue(200))),
                                                                                        new PrintStmt(new VarExp("v"))))))))))));
    }








    public static IStmt createExample12() {
        IStmt declareV1 = new VarDeclStmt(new RefType(new IntType()), "v1");
        IStmt declareV2 = new VarDeclStmt(new RefType(new IntType()), "v2");
        IStmt declareV3 = new VarDeclStmt(new RefType(new IntType()), "v3");
        IStmt declareCnt = new VarDeclStmt(new IntType(), "cnt");

        // heap allocations and latchSTatement deifnition
        IStmt newV1 = new HeapAllocationStmt("v1", new ValueExp(new IntValue(2)));
        IStmt newV2 = new HeapAllocationStmt("v2", new ValueExp(new IntValue(3)));
        IStmt newV3 = new HeapAllocationStmt("v3", new ValueExp(new IntValue(4)));
        IStmt newLatchCnt = new NewLatchStatement("cnt", new HeapReadingExp(new VarExp("v2")));

        // first fork
        IStmt modifyV1 = new HeapWritingStmt("v1", new ArithExp(new HeapReadingExp(new VarExp("v1")), new ValueExp(new IntValue(10)), 3));
        IStmt printV1 = new PrintStmt(new HeapReadingExp(new VarExp("v1")));
        IStmt countDownCnt1 = new CountdownStatement("cnt");
        IStmt fork1 = new ForkStmt(new CompStmt(modifyV1, new CompStmt(printV1, countDownCnt1)));

        // second fork
        IStmt modifyV2 = new HeapWritingStmt("v2", new ArithExp( new HeapReadingExp(new VarExp("v2")), new ValueExp(new IntValue(10)), 3));
        IStmt printV2 = new PrintStmt(new HeapReadingExp(new VarExp("v2")));
        IStmt countDownCnt2 = new CountdownStatement("cnt");
        IStmt fork2 = new ForkStmt(new CompStmt(modifyV2, new CompStmt(printV2, countDownCnt2)));

        // third fork
        IStmt modifyV3 = new HeapWritingStmt("v3", new ArithExp(new HeapReadingExp(new VarExp("v3")), new ValueExp(new IntValue(10)), 3));
        IStmt printV3 = new PrintStmt(new HeapReadingExp(new VarExp("v3")));
        IStmt countDownCnt3 = new CountdownStatement("cnt");
        IStmt fork3 = new ForkStmt(new CompStmt(modifyV3, new CompStmt(printV3, countDownCnt3)));

        // await(cnt)
        IStmt awaitCnt = new AwaitStatement("cnt");

        // print(100)
        IStmt print100 = new PrintStmt(new ValueExp(new IntValue(100)));

        // countDown(cnt)
        IStmt countDownCnt = new CountdownStatement("cnt");

        return new CompStmt(
                declareV1,
                new CompStmt(
                        declareV2,
                        new CompStmt(
                                declareV3,
                                new CompStmt(
                                        declareCnt,
                                        new CompStmt(
                                                newV1,
                                                new CompStmt(
                                                        newV2,
                                                        new CompStmt(
                                                                newV3,
                                                                new CompStmt(
                                                                        newLatchCnt,
                                                                        new CompStmt(
                                                                                fork1,
                                                                                new CompStmt(
                                                                                        fork2,
                                                                                        new CompStmt(
                                                                                                fork3,
                                                                                                new CompStmt(
                                                                                                        awaitCnt,
                                                                                                        new CompStmt(
                                                                                                                print100,
                                                                                                                new CompStmt(countDownCnt, print100)
                                                                                                        )
                                                                                                )
                                                                                        )
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
    }



    public static IStmt[] exampleList(){
        return new IStmt[]{createExample1(), createExample2(), createExample3(),createExample4(), createExample5(),
                createExample6(), createExample7(), createExample8(), createExample9(), createExample10(), createExample11(), createExample12()};
    }
}

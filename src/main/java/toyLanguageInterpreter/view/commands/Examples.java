package toyLanguageInterpreter.view.commands;

import toyLanguageInterpreter.model.expressions.*;
import toyLanguageInterpreter.model.statements.*;
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

    public static IStmt[] exampleList(){
        return new IStmt[]{createExample1(), createExample2(), createExample3(),createExample4(), createExample5(),
                createExample6(), createExample7(), createExample8(), createExample9(), createExample10()};
    }
}

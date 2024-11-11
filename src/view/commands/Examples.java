package view.commands;

import model.expressions.ArithExp;
import model.expressions.LogicExp;
import model.expressions.ValueExp;
import model.expressions.VarExp;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;

public class Examples {

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
        return new IStmt[]{createExample1(), createExample2(), createExample3(),createExample4(), createExample5()};
    }
}

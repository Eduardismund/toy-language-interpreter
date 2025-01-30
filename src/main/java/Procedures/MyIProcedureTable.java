package Procedures;

import javafx.util.Pair;
import toyLanguageInterpreter.model.adt.dictionary.MyIDictionary;
import toyLanguageInterpreter.model.statements.*;

import java.util.List;

public interface MyIProcedureTable extends MyIDictionary<String, Pair<List<String>, IStmt>> {
}



//final var procedureTable = new MyProcedureTable();
//IStmt f1 = new CompStmt(
//        new VarDeclStmt(new IntType(), "v"),
//        new CompStmt(
//                new AssignStmt("v", new ArithExp(new VarExp("a"), new VarExp("b"), 1)),
//                new PrintStmt(new VarExp("v"))
//        )
//);
//IStmt f2 = new CompStmt(
//        new VarDeclStmt(new IntType(), "v"),
//        new CompStmt(
//                new AssignStmt("v", new ArithExp(new VarExp("a"), new VarExp("b"), 3)),
//                new PrintStmt(new VarExp("v"))
//        )
//);
//
//        procedureTable.put("sum", new Pair<>(Arrays.asList("a", "b"), f1));
//        procedureTable.put("product", new Pair<>(Arrays.asList("a", "b"), f2));
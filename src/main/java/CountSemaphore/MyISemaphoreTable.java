package CountSemaphore;

import javafx.util.Pair;
import toyLanguageInterpreter.exceptions.ADTException;
import toyLanguageInterpreter.model.adt.MyIDictionary;
import toyLanguageInterpreter.model.expressions.ArithExp;
import toyLanguageInterpreter.model.expressions.HeapReadingExp;
import toyLanguageInterpreter.model.expressions.ValueExp;
import toyLanguageInterpreter.model.expressions.VarExp;
import toyLanguageInterpreter.model.statements.*;
import toyLanguageInterpreter.model.statements.heapStatements.HeapAllocationStmt;
import toyLanguageInterpreter.model.statements.heapStatements.HeapWritingStmt;
import toyLanguageInterpreter.model.types.IntType;
import toyLanguageInterpreter.model.types.RefType;
import toyLanguageInterpreter.model.values.IntValue;

import java.util.List;

public interface MyISemaphoreTable extends MyIDictionary<Integer, Pair<Integer, List<Integer>>> {
    int put(Pair<Integer, List<Integer>> value) throws ADTException;
    int getFreeAddress();
}








//
//public static IStmt createExample14() {
//    IStmt declV1 = new VarDeclStmt(new RefType(new IntType()), "v1");
//
//    // Declare cnt as an integer
//    IStmt declCnt = new VarDeclStmt(new IntType(), "cnt");
//
//    // Initialize v1 with 1
//    IStmt newV1 = new HeapAllocationStmt("v1", new ValueExp(new IntValue(1)));
//
//    // Create semaphore 'cnt' with initial value from rH(v1)
//    IStmt createSemaphore = new CreateSemaphoreStmt("cnt", new HeapReadingExp(new VarExp("v1")));
//
//    // First Fork - Thread 1
//    // Acquire semaphore 'cnt'
//    IStmt acquireCnt1 = new AcquireStatement("cnt");
//
//    // Write to heap (v1 = rH(v1) * 10)
//    IStmt writeV1_1 = new HeapWritingStmt("v1", new ArithExp(new HeapReadingExp(new VarExp("v1")), new ValueExp(new IntValue(10)), 3));
//
//    // Print rH(v1)
//    IStmt printV1_1 = new PrintStmt(new HeapReadingExp(new VarExp("v1")));
//
//    // Release semaphore 'cnt'
//    IStmt releaseCnt1 = new ReleaseStatement("cnt");
//
//    // Second Fork - Thread 2
//    // Acquire semaphore 'cnt'
//    IStmt acquireCnt2 = new AcquireStatement("cnt");
//
//    // Write to heap (v1 = rH(v1) * 2)
//    IStmt writeV1_2 = new HeapWritingStmt("v1", new ArithExp(new HeapReadingExp(new VarExp("v1")), new ValueExp(new IntValue(2)), 3));
//
//    // Print rH(v1)
//    IStmt printV1_2 = new PrintStmt(new HeapReadingExp(new VarExp("v1")));
//
//    // Release semaphore 'cnt'
//    IStmt releaseCnt2 = new ReleaseStatement("cnt");
//
//    // Main Thread
//    // Acquire semaphore 'cnt'
//    IStmt acquireCntMain = new AcquireStatement("cnt");
//
//    // Print rH(v1) - 1
//    IStmt printV1Main = new PrintStmt(new ArithExp(new HeapReadingExp(new VarExp("v1")), new ValueExp(new IntValue(1)), 2));
//
//    // Release semaphore 'cnt'
//    IStmt releaseCntMain = new ReleaseStatement("cnt");
//
//    // Combine all statements using CompStmt
//    IStmt program = new CompStmt(
//            declV1,
//            new CompStmt(
//                    declCnt,
//                    new CompStmt(
//                            newV1,
//                            new CompStmt(
//                                    createSemaphore,
//                                    new CompStmt(
//                                            new ForkStmt(
//                                                    new CompStmt(
//                                                            acquireCnt1,
//                                                            new CompStmt(
//                                                                    writeV1_1,
//                                                                    new CompStmt(
//                                                                            printV1_1,
//                                                                            releaseCnt1
//                                                                    )
//                                                            )
//                                                    )
//                                            ),
//                                            new CompStmt(
//                                                    new ForkStmt(
//                                                            new CompStmt(
//                                                                    acquireCnt2,
//                                                                    new CompStmt(
//                                                                            writeV1_1,
//                                                                            new CompStmt(
//                                                                                    writeV1_2,
//                                                                                    new CompStmt(
//                                                                                            printV1_2,
//                                                                                            releaseCnt2
//                                                                                    )
//                                                                            )
//                                                                    )
//                                                            )),
//                                                    new CompStmt(
//                                                            acquireCntMain,
//                                                            new CompStmt(
//                                                                    printV1Main,
//                                                                    releaseCntMain
//                                                            )
//                                                    )
//                                            )
//                                    )
//                            )
//                    )
//            )
//    );
//    return program;
//
//}


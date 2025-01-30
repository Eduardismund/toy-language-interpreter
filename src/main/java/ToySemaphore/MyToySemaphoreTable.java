package ToySemaphore;

import CountSemaphore.MyISemaphoreTable;
import javafx.util.Pair;
import toyLanguageInterpreter.exceptions.ADTException;
import toyLanguageInterpreter.model.adt.MyDictionary;
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

public class MyToySemaphoreTable extends MyDictionary<Integer, Pair<Integer, Pair<List<Integer>, Integer>>> implements MyIToySemaphoreTable {
    private int freeAddress = 1;

    public MyToySemaphoreTable() {
        super(); // Initializes the dictionary (Map)
    }

    // Adds a new semaphore entry and returns the allocated address
    @Override
    public int put(Pair<Integer, Pair<List<Integer>, Integer>> value) throws ADTException {
        synchronized (this) {
            int address = freeAddress;
            this.put(address, value);  // Adds the semaphore entry at the current address
            freeAddress++;             // Increments the address for the next entry
            return address;
        }
    }

    // Retrieves the next available address for the semaphore table
    @Override
    public int getFreeAddress() {
        synchronized (this) {
            return freeAddress;
        }
    }
}















//public static IStmt createExample11() {
//    // Declarations
//    String varV1 = "v1";
//    String varCnt = "cnt";
//
//    // Program Statements
//    IStmt program = new CompStmt(
//            new VarDeclStmt(new RefType(new IntType()), varV1), // Ref int v1;
//            new CompStmt(
//                    new VarDeclStmt(new IntType(), varCnt), // int cnt;
//                    new CompStmt(
//                            new HeapAllocationStmt(varV1, new ValueExp(new IntValue(2))), // new(v1, 2);
//                            new CompStmt(
//                                    new NewSemaphoreStatement(varCnt, new HeapReadingExp(new VarExp(varV1)), new ValueExp(new IntValue(1))), // newSemaphore(cnt, rH(v1), 1);
//                                    new CompStmt(
//                                            new ForkStmt(
//                                                    new CompStmt(
//                                                            new AcquireToyStatement(varCnt), // acquire(cnt);
//                                                            new CompStmt(
//                                                                    new HeapWritingStmt(varV1, // wh(v1, rH(v1) * 10);
//                                                                            new ArithExp(new HeapReadingExp(new VarExp(varV1)), new ValueExp(new IntValue(10)), 3)
//                                                                    ),
//                                                                    new CompStmt(
//                                                                            new PrintStmt(new HeapReadingExp(new VarExp(varV1))), // print(rH(v1));
//                                                                            new ReleaseToyStatement(varCnt) // release(cnt);
//                                                                    )
//                                                            )
//                                                    )
//                                            ),
//                                            new CompStmt(
//                                                    new ForkStmt(
//                                                            new CompStmt(
//                                                                    new AcquireToyStatement(varCnt), // acquire(cnt);
//                                                                    new CompStmt(
//                                                                            new CompStmt(
//                                                                                    new HeapWritingStmt(varV1, // wh(v1, rH(v1) * 10);
//                                                                                            new ArithExp(new HeapReadingExp(new VarExp(varV1)), new ValueExp(new IntValue(10)), 3)
//                                                                                    ),
//                                                                                    new HeapWritingStmt(varV1, // wh(v1, rH(v1) * 2);
//                                                                                            new ArithExp( new HeapReadingExp(new VarExp(varV1)), new ValueExp(new IntValue(2)), 3)
//                                                                                    )
//                                                                            ),
//                                                                            new CompStmt(
//                                                                                    new PrintStmt(new HeapReadingExp(new VarExp(varV1))), // print(rH(v1));
//                                                                                    new ReleaseToyStatement(varCnt) // release(cnt);
//                                                                            )
//                                                                    )
//                                                            )
//                                                    ),
//                                                    new CompStmt(
//                                                            new AcquireToyStatement(varCnt), // acquire(cnt);
//                                                            new CompStmt(
//                                                                    new PrintStmt(new ArithExp(new HeapReadingExp(new VarExp(varV1)), new ValueExp(new IntValue(1)), 2)), // print(rH(v1) - 1);
//                                                                    new ReleaseToyStatement(varCnt) // release(cnt);
//                                                            )
//                                                    )
//                                            )
//                                    )
//                            )
//                    )
//            )
//    );
//
//    return program;
//}
//

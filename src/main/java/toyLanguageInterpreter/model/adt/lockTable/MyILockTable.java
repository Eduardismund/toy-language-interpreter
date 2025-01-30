package toyLanguageInterpreter.model.adt.lockTable;

import toyLanguageInterpreter.exceptions.ADTException;

import java.util.HashMap;
import java.util.Set;

public interface MyILockTable {
    int getFreeValue();
    void put(int key, int value) throws ADTException;
    HashMap<Integer, Integer> getContent();
    boolean containsKey(int position);
    int get(int position) throws ADTException;
    void update(int position, int value) throws ADTException;
    void setContent(HashMap<Integer, Integer> newMap);
    Set<Integer> keySet();
}




















//    public static IStmt createExample14() {
//        // Variables: v1, v2, x, q
//        IStmt declareV1 = new VarDeclStmt(new RefType(new IntType()), "v1");
//        IStmt declareV2 = new VarDeclStmt(new RefType(new IntType()), "v2");
//        IStmt declareX = new VarDeclStmt(new IntType(), "x");
//        IStmt declareQ = new VarDeclStmt(new IntType(), "q");
//
//        // new(v1,20); new(v2,30); newLock(x);
//        IStmt newV1 = new HeapAllocationStmt("v1", new ValueExp(new IntValue(20)));
//        IStmt newV2 = new HeapAllocationStmt("v2", new ValueExp(new IntValue(30)));
//        IStmt newLockX = new NewLockStatement("x");
//
//        // First Fork
//        IStmt lockX1 = new LockStatement("x");
//        IStmt unlockX1 = new UnlockStatement("x");
//        IStmt modifyV1Minus1 = new HeapWritingStmt("v1", new ArithExp(new HeapReadingExp(new VarExp("v1")), new ValueExp(new IntValue(1)), 2));
//        IStmt lockX2 = new LockStatement("x");
//        IStmt modifyV1Times10 = new HeapWritingStmt("v1", new ArithExp(new HeapReadingExp(new VarExp("v1")), new ValueExp(new IntValue(10)), 3));
//        IStmt unlockX2 = new UnlockStatement("x");
//
//        IStmt innerFork1 = new ForkStmt(new CompStmt(lockX1, new CompStmt(modifyV1Minus1, unlockX1)));
//        IStmt fork1 = new ForkStmt(new CompStmt(innerFork1, new CompStmt(lockX2, new CompStmt(modifyV1Times10, unlockX2))));
//
//        // newLock(q);
//        IStmt newLockQ = new NewLockStatement("q");
//
//        // Second Fork
//        IStmt lockQ1 = new LockStatement("q");
//        IStmt unlockQ1 = new UnlockStatement("q");
//        IStmt modifyV2Plus5 = new HeapWritingStmt("v2", new ArithExp(new HeapReadingExp(new VarExp("v2")), new ValueExp(new IntValue(5)), 1));
//        IStmt lockQ2 = new LockStatement("q");
//        IStmt modifyV2Times10 = new HeapWritingStmt("v2", new ArithExp(new HeapReadingExp(new VarExp("v2")), new ValueExp(new IntValue(10)), 3));
//        IStmt unlockQ2 = new UnlockStatement("q");
//
//        IStmt innerFork2 = new ForkStmt(new CompStmt(lockQ1, new CompStmt(modifyV2Plus5, unlockQ1)));
//        IStmt fork2 = new ForkStmt(new CompStmt(innerFork2, new CompStmt(lockQ2, new CompStmt(modifyV2Times10, unlockQ2))));
//
//        // nop; nop; nop; nop;
//        IStmt noOp = new NopStmt();
//        IStmt multipleNoOps = new CompStmt(noOp, new CompStmt(noOp, new CompStmt(noOp, noOp)));
//
//        // lock(x); print(rh(v1)); unlock(x);
//        IStmt printV1 = new PrintStmt(new HeapReadingExp(new VarExp("v1")));
//        IStmt lockXPrint = new CompStmt(new LockStatement("x"), new CompStmt(printV1, new UnlockStatement("x")));
//
//        // lock(q); print(rh(v2)); unlock(q);
//        IStmt printV2 = new PrintStmt(new HeapReadingExp(new VarExp("v2")));
//        IStmt lockQPrint = new CompStmt(new LockStatement("q"), new CompStmt(printV2, new UnlockStatement("q")));
//
//        // Combine everything into a compound statement
//        return new CompStmt(
//                declareV1,
//                new CompStmt(
//                        declareV2,
//                        new CompStmt(
//                                declareX,
//                                new CompStmt(
//                                        declareQ,
//                                        new CompStmt(
//                                                newV1,
//                                                new CompStmt(
//                                                        newV2,
//                                                        new CompStmt(
//                                                                newLockX,
//                                                                new CompStmt(
//                                                                        fork1,
//                                                                        new CompStmt(
//                                                                                newLockQ,
//                                                                                new CompStmt(
//                                                                                        fork2,
//                                                                                        new CompStmt(multipleNoOps, new CompStmt(lockXPrint, lockQPrint))
//                                                                                )
//                                                                        )
//                                                                )
//                                                        )
//                                                )
//                                        )
//                                )
//                        )
//                )
//        );
//    }

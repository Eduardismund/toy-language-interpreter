package statement;

import toyLanguageInterpreter.exceptions.ADTException;
import toyLanguageInterpreter.exceptions.ExpressionException;
import toyLanguageInterpreter.exceptions.StatementException;
import toyLanguageInterpreter.model.PrgState;
import toyLanguageInterpreter.model.adt.MyIDictionary;
import toyLanguageInterpreter.model.expressions.*;
import toyLanguageInterpreter.model.statements.*;
import toyLanguageInterpreter.model.statements.heapStatements.HeapAllocationStmt;
import toyLanguageInterpreter.model.statements.heapStatements.HeapWritingStmt;
import toyLanguageInterpreter.model.types.BoolType;
import toyLanguageInterpreter.model.types.IntType;
import toyLanguageInterpreter.model.types.RefType;
import toyLanguageInterpreter.model.types.Type;
import toyLanguageInterpreter.model.values.IntValue;

public class ConditionalAssignment implements IStmt {
    private String varName;
    private Exp expr1;
    private Exp expr2;
    private Exp expr3;

    public ConditionalAssignment(String varName, Exp expr1, Exp expr2, Exp expr3) {
        this.varName = varName;
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.expr3 = expr3;
    }

    @Override
    public PrgState execute(PrgState currentState) throws StatementException {
        AssignStmt thenStmt = new AssignStmt(this.varName, this.expr2);
        AssignStmt elseStmt = new AssignStmt(this.varName, this.expr3);
        currentState.getExeStack().push(new IfStmt(this.expr1, thenStmt, elseStmt));
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ConditionalAssignment(this.varName, this.expr1.deepCopy(), this.expr2.deepCopy(), this.expr3.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ADTException, ExpressionException {
        Type expr1Type = this.expr1.typeCheck(typeEnv);
        if (!expr1Type.equals(new BoolType())) {
            throw new StatementException("TYPE CHECK ERROR: The condition given in the conditional assignment (" + this.expr1.toString() + ") is not a boolean.");
        }

        Type vType = typeEnv.lookup(this.varName);
        Type expr2Type = this.expr2.typeCheck(typeEnv);
        Type expr3Type = this.expr3.typeCheck(typeEnv);
        if (vType.equals(expr2Type) && expr2Type.equals(expr3Type)) {
            return typeEnv;
        } else {
            throw new StatementException("TYPE CHECK ERROR: The type of the variable (" + this.varName + ") and the expressions (" +
                                  this.expr2.toString() + "/" + this.expr3.toString() + ") in the Conditional Assignment do not match.");
        }
    }

    @Override
    public String toString() {
        return this.varName + " = " + this.expr1.toString() + " ? " + this.expr2.toString() + " : " + this.expr3.toString();
    }
}







//private static IStmt createExample11() {
//    return new CompStmt(new VarDeclStmt(new RefType(new IntType()), "a"),
//            new CompStmt(new VarDeclStmt(new RefType(new IntType()), "b"),
//                    new CompStmt(new VarDeclStmt(new IntType(), "v"),
//                            new CompStmt(new HeapAllocationStmt("a",new ValueExp(new IntValue(0))),
//                                    new CompStmt(new HeapAllocationStmt("b",new ValueExp(new IntValue(0))),
//                                            new CompStmt(new HeapWritingStmt("a", new ValueExp(new IntValue(1))),
//                                                    new CompStmt(new HeapWritingStmt("b", new ValueExp(new IntValue(2))),
//                                                            new CompStmt(new ConditionalAssignment("v", new RelExp("<",new HeapReadingExp(new VarExp("a")), new HeapReadingExp(new VarExp("b"))), new ValueExp(new IntValue(100)), new ValueExp(new IntValue(200))),
//                                                                    new CompStmt(new PrintStmt(new VarExp("v" )),
//                                                                            new CompStmt(new ConditionalAssignment("v", new RelExp(">",new ArithExp(new HeapReadingExp(new VarExp("b")), new ValueExp(new IntValue(2)), 2), new HeapReadingExp(new VarExp("a"))), new ValueExp(new IntValue(100)), new ValueExp(new IntValue(200))),
//                                                                                    new PrintStmt(new VarExp("v"))))))))))));
//}
//

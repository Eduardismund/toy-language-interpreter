package statement;

import toyLanguageInterpreter.exceptions.ADTException;
import toyLanguageInterpreter.exceptions.ExpressionException;
import toyLanguageInterpreter.exceptions.StatementException;
import toyLanguageInterpreter.model.PrgState;
import toyLanguageInterpreter.model.adt.MyIDictionary;
import toyLanguageInterpreter.model.expressions.*;
import toyLanguageInterpreter.model.statements.*;
import toyLanguageInterpreter.model.statements.heapStatements.HeapAllocationStmt;
import toyLanguageInterpreter.model.types.BoolType;
import toyLanguageInterpreter.model.types.IntType;
import toyLanguageInterpreter.model.types.RefType;
import toyLanguageInterpreter.model.types.Type;
import toyLanguageInterpreter.model.values.IntValue;

public class ForStatement implements IStmt {
    IStmt initialValue;
    Exp comparisonExpression;
    IStmt increment;
    IStmt actionStatement;

    public ForStatement(IStmt initialValue, Exp comparisonExpression, IStmt increment, IStmt actionStatement) {
        this.initialValue = initialValue;
        this.comparisonExpression = comparisonExpression;
        this.increment = increment;
        this.actionStatement = actionStatement;
    }

    @Override
    public PrgState execute(PrgState currentState) throws StatementException {
        IStmt stmt1 = this.initialValue;
        WhileStmt stmt2 = new WhileStmt(this.comparisonExpression, new CompStmt(this.actionStatement, this.increment));
        CompStmt newStmt = new CompStmt(stmt1, stmt2);
        currentState.getExeStack().push(newStmt);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ForStatement(this.initialValue.deepCopy(), this.comparisonExpression.deepCopy(), this.increment.deepCopy(), this.actionStatement.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws ADTException, ExpressionException, StatementException {
        Type comparisonExpressionType = this.comparisonExpression.typeCheck(typeEnv);
        if (!comparisonExpressionType.equals(new BoolType())) {
            throw new StatementException("TYPE CHECK ERROR: The condition of FOR is not of type bool.");
        }
        this.initialValue.typeCheck(typeEnv);
        this.increment.typeCheck(typeEnv);
        this.actionStatement.typeCheck(typeEnv.shallowCopy());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "for(" + this.initialValue.toString() + "; " + this.comparisonExpression.toString() + "; " + this.increment.toString() + ") " + this.actionStatement.toString();
    }
}




//private static IStmt createExample12() {
//    IStmt initial = new AssignStmt("v", new ValueExp(new IntValue(0)));
//    Exp condition = new RelExp("<", new VarExp("v"), new ValueExp(new IntValue(3)));
//    IStmt increment = new AssignStmt("v", new ArithExp(new VarExp("v"), new ValueExp(new IntValue(1)), 1));
//    IStmt action = new PrintStmt(new VarExp("v"));
//
//    return new CompStmt(new VarDeclStmt(new RefType(new IntType()), "a"),
//            new CompStmt(new HeapAllocationStmt("a",new ValueExp(new IntValue(20))),
//                    new CompStmt(new VarDeclStmt(new IntType(), "v"),
//                            new CompStmt(new ForStatement(initial, condition, increment, action),
//                                    new PrintStmt(new HeapReadingExp(new VarExp("a")))))));
//}


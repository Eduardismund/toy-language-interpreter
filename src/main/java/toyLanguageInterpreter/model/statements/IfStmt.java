package toyLanguageInterpreter.model.statements;

import toyLanguageInterpreter.exceptions.ADTException;
import toyLanguageInterpreter.exceptions.ExpressionException;
import toyLanguageInterpreter.exceptions.InterpreterException;
import toyLanguageInterpreter.exceptions.StatementException;
import toyLanguageInterpreter.exceptions.MyException;
import toyLanguageInterpreter.model.PrgState;
import toyLanguageInterpreter.model.adt.MyIDictionary;
import toyLanguageInterpreter.model.expressions.Exp;
import toyLanguageInterpreter.model.types.BoolType;
import toyLanguageInterpreter.model.types.Type;
import toyLanguageInterpreter.model.values.BoolValue;
import toyLanguageInterpreter.model.values.Value;
import toyLanguageInterpreter.model.adt.MyIStack;

public class IfStmt implements IStmt {
    private IStmt thenStmt;
    private IStmt elseStmt;
    private Exp exp;

    public IfStmt(Exp exp, IStmt thenStmt, IStmt elseStmt) {
        this.exp = exp;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, StatementException, ExpressionException, InterpreterException, ADTException {
        final var heap = state.getHeap();
        Value v = exp.eval(state.getSymTable(), heap);
        MyIStack<IStmt> stack = state.getExeStack();
        if (v instanceof BoolValue) {
            if (((BoolValue) v).getVal()) {
                stack.push(thenStmt);
            } else {
                stack.push(elseStmt);
            }
        } else {
            throw new StatementException("Expression does not evaluate to a BoolValue!");
        }
        state.setExecutionStack(stack);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(exp.deepCopy(), thenStmt, elseStmt);
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws ADTException, ExpressionException, StatementException {
        final var typeExp = exp.typeCheck(typeEnv);
        if(!(typeExp.equals(new BoolType()))) {
            throw new ExpressionException("The If Statement has no type Bool!");
        }
        thenStmt.typeCheck(typeEnv.deepCopy());
        elseStmt.typeCheck(typeEnv.deepCopy());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "if (" + exp.toString() + ") then (" + thenStmt.toString()  + ") else (" + elseStmt.toString() + ")";
    }
}

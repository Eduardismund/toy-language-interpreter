package toyLanguageInterpreter.model.statements;

import toyLanguageInterpreter.exceptions.ADTException;
import toyLanguageInterpreter.exceptions.ExpressionException;
import toyLanguageInterpreter.exceptions.StatementException;
import toyLanguageInterpreter.exceptions.MyException;
import toyLanguageInterpreter.model.PrgState;
import toyLanguageInterpreter.model.adt.MyIDictionary;
import toyLanguageInterpreter.model.adt.MyIStack;
import toyLanguageInterpreter.model.types.Type;

public class CompStmt implements IStmt {

    private IStmt first;
    private IStmt second;

    public IStmt getFirst() {
        return first;
    }

    public IStmt getSecond() {
        return second;
    }

    public CompStmt(IStmt first, IStmt second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString()
    {
        return "(" + first.toString() + "; " + second.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stack = state.getExeStack();
        stack.push(second);
        stack.push(first);
        state.setExecutionStack(stack);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CompStmt(first.deepCopy(), second.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ADTException, ExpressionException {
        return second.typeCheck(first.typeCheck(typeEnv));
    }
}

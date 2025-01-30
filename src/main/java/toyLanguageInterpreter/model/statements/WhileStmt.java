package toyLanguageInterpreter.model.statements;

import toyLanguageInterpreter.exceptions.ADTException;
import toyLanguageInterpreter.exceptions.ExpressionException;
import toyLanguageInterpreter.exceptions.InterpreterException;
import toyLanguageInterpreter.exceptions.StatementException;
import toyLanguageInterpreter.exceptions.MyException;
import toyLanguageInterpreter.model.PrgState;
import toyLanguageInterpreter.model.adt.dictionary.MyIDictionary;
import toyLanguageInterpreter.model.expressions.Exp;
import toyLanguageInterpreter.model.types.BoolType;
import toyLanguageInterpreter.model.types.Type;
import toyLanguageInterpreter.model.values.BoolValue;
import toyLanguageInterpreter.model.values.Value;

import java.io.FileNotFoundException;


public class WhileStmt implements IStmt{
    private Exp expression;
    private IStmt stmt;

    public WhileStmt(Exp exp, IStmt stmt) {
        this.expression = exp;
        this.stmt = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, MyException, StatementException, ExpressionException, InterpreterException, FileNotFoundException, ADTException {
        final var exeStack = state.getExeStack();
        final var heap = state.getHeap();
        final var symTable = state.getSymTable();

        Value val = this.expression.eval(symTable, heap);

        if(!(val instanceof BoolValue)){
            throw new StatementException("The expression is not a boolean");
        }

        if(((BoolValue) val).getVal()){
            exeStack.push(this.deepCopy());
            exeStack.push(stmt);
        }

        state.setExecutionStack(exeStack);
        return null;
    }

    @Override
    public String toString() {
        return "while(" + expression.toString() + ") {" + this.stmt.toString() + "}";
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(expression.deepCopy(), stmt.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws ADTException, ExpressionException, StatementException {
        final var typeExp = expression.typeCheck(typeEnv);
        if(!(typeExp.equals(new BoolType()))){
            throw new ExpressionException("The condition of WHILE is not a boolean");
        }
        this.stmt.typeCheck(typeEnv.deepCopy());
        return typeEnv;
    }
}

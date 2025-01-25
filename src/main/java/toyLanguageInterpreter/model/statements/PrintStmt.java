package toyLanguageInterpreter.model.statements;

import toyLanguageInterpreter.exceptions.ADTException;
import toyLanguageInterpreter.exceptions.ExpressionException;
import toyLanguageInterpreter.exceptions.InterpreterException;
import toyLanguageInterpreter.exceptions.MyException;
import toyLanguageInterpreter.model.PrgState;
import toyLanguageInterpreter.model.adt.MyIDictionary;
import toyLanguageInterpreter.model.expressions.Exp;
import toyLanguageInterpreter.model.types.Type;
import toyLanguageInterpreter.model.values.Value;
import toyLanguageInterpreter.model.adt.MyIList;

public class PrintStmt implements IStmt {
    private Exp exp;

    public PrintStmt(Exp exp){
        this.exp = exp;
    }

    @Override
    public String toString(){
        if(exp != null) {
            return "print(" + exp.toString() + ")";
        }
        return "print()";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, ExpressionException, InterpreterException, ADTException {
        MyIList<Value> out = state.getOut();
        final var heap = state.getHeap();
        Value value = exp.eval(state.getSymTable(), heap);
        out.add(value);
        state.setOutput(out);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws ADTException, ExpressionException {
        exp.typeCheck(typeEnv);
        return typeEnv;
    }
}

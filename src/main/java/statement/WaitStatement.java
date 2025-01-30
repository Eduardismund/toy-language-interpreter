package statement;

import toyLanguageInterpreter.exceptions.StatementException;
import toyLanguageInterpreter.model.PrgState;
import toyLanguageInterpreter.model.adt.MyIDictionary;
import toyLanguageInterpreter.model.adt.MyIStack;
import toyLanguageInterpreter.model.expressions.ArithExp;
import toyLanguageInterpreter.model.expressions.ValueExp;
import toyLanguageInterpreter.model.expressions.VarExp;
import toyLanguageInterpreter.model.statements.*;
import toyLanguageInterpreter.model.types.IntType;
import toyLanguageInterpreter.model.types.Type;
import toyLanguageInterpreter.model.values.IntValue;

public class WaitStatement implements IStmt {
    private final int number;

    public WaitStatement(int number)
    {
        this.number = number;
    }

    @Override
    public PrgState execute(PrgState currentState) throws StatementException {
        MyIStack<IStmt> stk = currentState.getExeStack();
        if(number!=0)
        {
            stk.push(new CompStmt(new PrintStmt(new ValueExp(new IntValue(number))), new WaitStatement(number - 1)));
        }
        return null;
    }

    @Override
    public String toString()
    {
        return "Wait("+this.number+")";
    }

    @Override
    public IStmt deepCopy() {
        return new WaitStatement(number);
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementException {
        return typeEnv;
    }
}







//private static IStmt createExample13() {
//    return new CompStmt(new VarDeclStmt(new IntType(), "v"),
//            new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(20))),
//                    new CompStmt(new WaitStatement(10),
//                            new PrintStmt(new ArithExp(new ValueExp(new IntValue(10)), new VarExp("v"),3)))));
//
//}

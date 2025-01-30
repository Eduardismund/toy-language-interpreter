package toyLanguageInterpreter.model.statements.CountDownLatchStatements;

import toyLanguageInterpreter.exceptions.ADTException;
import toyLanguageInterpreter.exceptions.MyException;
import toyLanguageInterpreter.exceptions.StatementException;
import toyLanguageInterpreter.model.PrgState;
import toyLanguageInterpreter.model.adt.MyIDictionary;
import toyLanguageInterpreter.model.statements.IStmt;
import toyLanguageInterpreter.model.types.IntType;
import toyLanguageInterpreter.model.types.Type;
import toyLanguageInterpreter.model.values.IntValue;

public class AwaitStatement implements IStmt {
    private String varName;

    public AwaitStatement(String varName) {
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState currentState) throws StatementException, ADTException {
        synchronized (currentState.getLatchTable()) {
            final var foundIndex = currentState.getSymTable().lookup(this.varName);
            if (!foundIndex.getType().equals(new IntType())) {
                throw new StatementException("ERROR: The variable " + this.varName + " is not an integer. [AWAIT ERROR]");
            }
            final var foundIndexInteger = (IntValue) foundIndex;
            int foundIndexInt = foundIndexInteger.getVal();

            if (!currentState.getLatchTable().isLatchAddressUsed(foundIndexInt)) {
                throw new StatementException("ERROR: The index " + foundIndexInt + " held by variable " + this.varName + " is not an index in the Latch Table[AWAIT ERROR].");
            }
            if (currentState.getLatchTable().getLatchCounter(foundIndexInt) > 0) {
                currentState.getExeStack().push(this);
            }
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new AwaitStatement(this.varName);
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ADTException {
        Type varType = typeEnv.lookup(this.varName);
        if (!varType.equals(new IntType())) {
            throw new StatementException("ERROR: The variable " + this.varName + " is not an integer. [AWAIT ERROR]");
        }
        return typeEnv;
    }

    @Override
    public String toString() {
        return "await(" + this.varName + ")";
    }
}

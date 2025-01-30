package toyLanguageInterpreter.model.statements.CountDownLatchStatements;


import toyLanguageInterpreter.exceptions.*;
import toyLanguageInterpreter.model.PrgState;
import toyLanguageInterpreter.model.adt.MyIDictionary;
import toyLanguageInterpreter.model.expressions.Exp;
import toyLanguageInterpreter.model.statements.IStmt;
import toyLanguageInterpreter.model.types.IntType;
import toyLanguageInterpreter.model.types.Type;
import toyLanguageInterpreter.model.values.IntValue;

public class NewLatchStatement implements IStmt {
    private String varName;
    private Exp expression;

    public NewLatchStatement(String varName, Exp expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState currentState) throws StatementException, ADTException, MyException, InterpreterException, ExpressionException {
        final var varValue = currentState.getSymTable().lookup(this.varName);
        if (!varValue.getType().equals(new IntType())) {
            throw new StatementException("ERROR: The given variable (" + this.varName + ") is not an integer: it cannot hold the address of a countdown latch.");
        }
        synchronized (currentState.getLatchTable()) {
            final var num1 = this.expression.eval(currentState.getSymTable(), currentState.getHeap());
            if (!num1.getType().equals(new IntType())) {
                throw new StatementException("ERROR: The given countdown latch counter is not an integer (" + this.expression.toString() + " given).");
            }
            IntValue num1Integer = (IntValue) num1;
            int num1Int = num1Integer.getVal();

            int addressUsed = currentState.getLatchTable().addNewLatch(num1Int);
            currentState.getSymTable().put(this.varName, new IntValue(addressUsed));
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NewLatchStatement(this.varName, this.expression);
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementException, ADTException, ExpressionException {
        Type varType = typeEnv.lookup(this.varName);
        if (!varType.equals(new IntType())) {
            throw new StatementException("ERROR: The given variable (" + this.varName + ") is not an integer: it cannot hold the address of a countdown latch.");
        }
        Type expressionType = this.expression.typeCheck(typeEnv);
        if (!expressionType.equals(new IntType())) {
            throw new StatementException("ERROR: The given countdown latch counter is not an integer (" + this.expression.toString() + " given).");
        }
        return typeEnv;
    }

    @Override
    public String toString() {
        return "newLatch(" + this.varName + ", " + this.expression.toString() + ")";
    }

}

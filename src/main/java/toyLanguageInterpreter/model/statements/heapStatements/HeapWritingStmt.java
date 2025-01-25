package toyLanguageInterpreter.model.statements.heapStatements;

import toyLanguageInterpreter.exceptions.ADTException;
import toyLanguageInterpreter.exceptions.ExpressionException;
import toyLanguageInterpreter.exceptions.InterpreterException;
import toyLanguageInterpreter.exceptions.StatementException;
import toyLanguageInterpreter.exceptions.MyException;
import toyLanguageInterpreter.model.PrgState;
import toyLanguageInterpreter.model.adt.MyIDictionary;
import toyLanguageInterpreter.model.expressions.Exp;
import toyLanguageInterpreter.model.statements.IStmt;
import toyLanguageInterpreter.model.types.RefType;
import toyLanguageInterpreter.model.types.Type;
import toyLanguageInterpreter.model.values.RefValue;
import toyLanguageInterpreter.model.values.Value;

import java.io.FileNotFoundException;

public class HeapWritingStmt implements IStmt {
    private String varName;
    private Exp exp;

    public HeapWritingStmt(String varName, Exp exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, MyException, StatementException, ExpressionException, InterpreterException, FileNotFoundException, ADTException {
        final var symTable = state.getSymTable();
        final var heap = state.getHeap();

        if(!symTable.isDefined(varName)) {
            throw new StatementException("Variable '" + varName + "' not found in the symTable");
        }

        Value val = symTable.lookup(varName);

        if(!(val.getType() instanceof RefType)){
            throw new StatementException("Variable '" + varName + "' is not a reference type");
        }
        final var v = (RefValue)val;
        int address = v.getAddress();

        if(!heap.isDefined(address)) {
            throw new StatementException("Variable '" + varName + "' is not defined");
        }

        Value vi = exp.eval(symTable, heap);

        if(!(vi.getType().equals(heap.lookup(address).getType()))){
            throw new StatementException("Expression is not of variable type");
        }

        heap.put(address, vi);
        state.setHeap(heap);
        state.setSymbolTable(symTable);

        return null;

    }

    @Override
    public String toString() {
        return "writeHeap(" + this.varName + ", " + this.exp.toString() + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new HeapWritingStmt(varName, exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws ADTException, ExpressionException, StatementException {
        final var typeCheck = typeEnv.lookup(varName);
        final var typeExpr = exp.typeCheck(typeEnv);

        if(!typeCheck.equals(new RefType(typeExpr))) {
            throw new StatementException("The types of the variable and the type of the expressions do not match");
        }

        return typeEnv;
    }
}

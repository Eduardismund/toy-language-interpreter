package toyLanguageInterpreter.model.statements.heapStatements;

import toyLanguageInterpreter.exceptions.ADTException;
import toyLanguageInterpreter.exceptions.ExpressionException;
import toyLanguageInterpreter.exceptions.InterpreterException;
import toyLanguageInterpreter.exceptions.StatementException;
import toyLanguageInterpreter.exceptions.MyException;
import toyLanguageInterpreter.model.PrgState;
import toyLanguageInterpreter.model.adt.dictionary.MyIDictionary;
import toyLanguageInterpreter.model.expressions.Exp;
import toyLanguageInterpreter.model.statements.IStmt;
import toyLanguageInterpreter.model.types.RefType;
import toyLanguageInterpreter.model.types.Type;
import toyLanguageInterpreter.model.values.RefValue;
import toyLanguageInterpreter.model.values.Value;

import java.io.FileNotFoundException;

public class HeapAllocationStmt implements IStmt {
    private String varName;
    private Exp exp;

    public HeapAllocationStmt(String varName, Exp exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, MyException, StatementException, ExpressionException, InterpreterException, FileNotFoundException, ADTException {
        final var symTale = state.getSymTable();
        final var heap = state.getHeap();

        if(!symTale.isDefined(varName)) {
            throw new StatementException("Variable " + varName + " is not defined");
        }

        final var varType = symTale.lookup(varName);

        if(!(varType.getType() instanceof RefType)) {
            throw new StatementException("Variable " + varName + " is not a Ref type");
        }

        Value val = exp.eval(symTale, heap);
        if(!val.getType().equals(((RefType) varType.getType()).getInner())) {
            throw new StatementException("Variable " + varName + " is not of type " + val.getType());
        }

        int next = heap.getNextFree();
        symTale.update(varName,new RefValue(next, val.getType()));
        heap.put(next,val);
        heap.updateNewFree();

        state.setSymbolTable(symTale);
        state.setHeap(heap);
        return null;
        ///return null;
    }

    @Override
    public String toString() {
        return "new(" + varName + ", " + exp.toString() + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new HeapAllocationStmt(varName, exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws ADTException, ExpressionException, StatementException {
        final var typeVar = typeEnv.lookup(varName);
        final var typeExp = exp.typeCheck(typeEnv);
        if(!(typeVar.equals(new RefType(typeExp)))) {
            throw new StatementException("Heap allocation: Right hand side and left hand side are not the same");
        }
        return typeEnv;
    }
}

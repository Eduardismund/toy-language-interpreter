package toyLanguageInterpreter.model.statements;

import toyLanguageInterpreter.exceptions.ADTException;
import toyLanguageInterpreter.exceptions.ExpressionException;
import toyLanguageInterpreter.exceptions.InterpreterException;
import toyLanguageInterpreter.exceptions.StatementException;
import toyLanguageInterpreter.exceptions.MyException;
import toyLanguageInterpreter.model.PrgState;
import toyLanguageInterpreter.model.expressions.Exp;
import toyLanguageInterpreter.model.types.Type;
import toyLanguageInterpreter.model.values.Value;
import toyLanguageInterpreter.model.adt.MyIDictionary;

import java.lang.reflect.TypeVariable;

public class AssignStmt implements IStmt {
    private String id;
    private Exp exp;


    public AssignStmt(String id, Exp exp){
        this.id = id;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException, StatementException, ExpressionException, InterpreterException, ADTException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        final var heap = state.getHeap();
        if(symTable.isDefined(id)){
            Value val = exp.eval(symTable, heap);
            Type typeId = symTable.lookup(id).getType();

            if(val.getType().equals(typeId)){
                symTable.update(id, val);
            } else{
                throw new StatementException("The types of the assigned variable and the variable " + id + " do not match!");
            }
        } else
        {
            throw new StatementException("the used variable " + id + " was not declared before!");
        }
        state.setSymbolTable(symTable);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new AssignStmt(id, exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws ADTException, ExpressionException, StatementException {
        final var typeVar = typeEnv.lookup(id);
        final var typeExp = exp.typeCheck(typeEnv);
        if(!(typeVar.equals(typeExp))){
            throw new StatementException("The types of the assigned variable and the variable do not match!");
        }
        return typeEnv;
    }

    @Override
    public String toString(){
        return id + " = " + exp.toString();
    }
}

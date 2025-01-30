package toyLanguageInterpreter.model.statements;

import toyLanguageInterpreter.exceptions.ADTException;
import toyLanguageInterpreter.exceptions.ExpressionException;
import toyLanguageInterpreter.exceptions.StatementException;
import toyLanguageInterpreter.model.PrgState;
import toyLanguageInterpreter.model.adt.dictionary.MyIDictionary;
import toyLanguageInterpreter.model.expressions.Exp;
import toyLanguageInterpreter.model.expressions.RelExp;
import toyLanguageInterpreter.model.types.Type;

public class SwitchStatement implements IStmt {
    private Exp exp, exp1, exp2;
    private IStmt stmt1, stmt2, stmt3;

    public SwitchStatement(Exp exp, Exp exp1, IStmt stmt1, Exp exp2, IStmt stmt2, IStmt stmt3) {
        this.exp = exp;
        this.exp1 = exp1;
        this.stmt1 = stmt1;
        this.exp2 = exp2;
        this.stmt2 = stmt2;
        this.stmt3 = stmt3;
    }

    @Override
    public PrgState execute(PrgState currentState) throws StatementException {
        RelExp ifCond1 = new RelExp("==", this.exp, this.exp1);
        RelExp ifCond2 = new RelExp("==",this.exp, this.exp2);

        IfStmt innerIfStmt = new IfStmt(ifCond2, this.stmt2, this.stmt3);
        IfStmt outerIfStmt = new IfStmt(ifCond1, this.stmt1, innerIfStmt);

        currentState.getExeStack().push(outerIfStmt);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new SwitchStatement(this.exp.deepCopy(), this.exp1.deepCopy(), this.stmt1.deepCopy(), this.exp2.deepCopy(),
                this.stmt2.deepCopy(), this.stmt3.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws ADTException, ExpressionException, StatementException {
        Type expType = this.exp.typeCheck(typeEnv);
        Type exp1Type = this.exp1.typeCheck(typeEnv);
        Type exp2Type = this.exp2.typeCheck(typeEnv);
        if (!(expType.equals(exp1Type) && exp1Type.equals(exp2Type))) {
            throw new StatementException("TYPE CHECK ERROR: The comparison expressions given in the switch statement (" +
                                  this.exp.toString() + ", " + this.exp1.toString() + ", " + this.exp2.toString() + " given) do not match in type.");
        }

        this.stmt1.typeCheck(typeEnv.shallowCopy());
        this.stmt2.typeCheck(typeEnv.shallowCopy());
        this.stmt3.typeCheck(typeEnv.shallowCopy());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "switch(" + this.exp.toString() + ") (case " + this.exp1.toString() + ": " + this.stmt1.toString() + ") " +
               "(case " + this.exp2.toString() + ": " + this.stmt2.toString() + ") (default: " + this.stmt3.toString() + ")";
    }
}

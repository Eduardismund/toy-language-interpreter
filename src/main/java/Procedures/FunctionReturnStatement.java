//package Procedures;
//
//import toyLanguageInterpreter.exceptions.ADTException;
//import toyLanguageInterpreter.exceptions.InterpreterException;
//import toyLanguageInterpreter.exceptions.StatementException;
//import toyLanguageInterpreter.model.PrgState;
//import toyLanguageInterpreter.model.adt.dictionary.MyIDictionary;
//import toyLanguageInterpreter.model.statements.IStmt;
//import toyLanguageInterpreter.model.types.Type;
//
//import javax.swing.plaf.nimbus.State;
//
//public class FunctionReturnStatement implements IStmt {
//    @Override
//    public PrgState execute(PrgState state) throws StatementException {
//        try {
//            state.getAllSymTables().pop();
//        } catch (ADTException | InterpreterException e) {
//            throw new RuntimeException(e);
//        }
//
//        return null;
//    }
//
//    @Override
//    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) {
//        return typeEnv;
//    }
//
//    @Override
//    public IStmt deepCopy() {
//        return new FunctionReturnStatement();
//    }
//
//    @Override
//    public String toString() {
//        return "return";
//    }
//}

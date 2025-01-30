//package Procedures;
//
//import toyLanguageInterpreter.exceptions.*;
//import toyLanguageInterpreter.model.PrgState;
//import toyLanguageInterpreter.model.adt.dictionary.MyDictionary;
//import toyLanguageInterpreter.model.adt.dictionary.MyIDictionary;
//import toyLanguageInterpreter.model.adt.list.MyIList;
//import toyLanguageInterpreter.model.adt.list.MyList;
//import toyLanguageInterpreter.model.expressions.Exp;
//import toyLanguageInterpreter.model.statements.IStmt;
//import toyLanguageInterpreter.model.types.Type;
//import toyLanguageInterpreter.model.values.Value;
//
//import java.util.List;
//import java.util.Vector;
//
//public class FunctionCallStatement implements IStmt {
//    private String functionName;
//    private MyIList<Exp> parameters;
//
//    public FunctionCallStatement(String functionName, List<Exp> parameters) {
//        this.functionName = functionName;
//        this.parameters = new MyList<Exp>();
//
//        for (int i = 0; i < parameters.size(); ++i) {
//            this.parameters.add(parameters.get(i));
//        }
//    }
//
//    @Override
//    public PrgState execute(PrgState state) throws StatementException, ADTException, MyException, InterpreterException, ExpressionException {
//        try {
//            final var functionEntry = state.getProcedureTable().lookup(functionName);
//            if (functionEntry == null)
//                throw new StatementException(String.format("Function '%s' does not exist!", functionName));
//
//            List<String> paramNames = functionEntry.getKey();
//            IStmt functionBody = functionEntry.getValue();
//
//            List<Value> paramValues = new Vector<Value>();
//            for (int i = 0; i < parameters.size(); ++i) {
//                Exp expression = (Exp) parameters.get(i);
//                paramValues.add(expression.eval(state.getSymTable(), state.getHeap()));
//
//            }
//
//            MyIDictionary<String, Value> newSymbolsTable = new MyDictionary<>();
//            int size = paramNames.size();
//            for (int i = 0; i < size; ++i)
//                newSymbolsTable.put(paramNames.get(i), paramValues.get(i));
//
//            state.getAllSymTables().push(newSymbolsTable);
//            state.getExeStack().push(new FunctionReturnStatement());
//            state.getExeStack().push(functionBody);
//        } catch (StatementException e) {
//            throw new StatementException(e.getMessage());
//        }
//
//        return null;
//    }
//
//    @Override
//    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnv) throws StatementException {
//        return typeEnv;
//    }
//
//    @Override
//    public IStmt deepCopy() {
//        List<Exp> newParams = new Vector<Exp>();
//        for (int i = 0; i < parameters.size(); ++i) {
//            try {
//                newParams.add(parameters.get(i));
//            } catch (ADTException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        return new FunctionCallStatement(functionName, newParams);
//    }
//
//    @Override
//    public String toString() {
//        StringBuilder result = new StringBuilder("call " + functionName + "(");
//        for (int i = 0; i < parameters.size() - 1; ++i) {
//            try {
//                result.append(parameters.get(i).toString()).append(", ");
//            } catch (ADTException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        if (!parameters.isEmpty()) {
//            try {
//                result.append(parameters.get(parameters.size() - 1).toString());
//                result.append(")");
//            } catch (ADTException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        return result.toString();
//    }
//}
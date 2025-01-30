//package Procedures;
//
//
//import toyLanguageInterpreter.exceptions.ADTException;
//import toyLanguageInterpreter.exceptions.InterpreterException;
//import toyLanguageInterpreter.model.adt.dictionary.MyIDictionary;
//import toyLanguageInterpreter.model.adt.stack.MyStack;
//import toyLanguageInterpreter.model.values.Value;
//
//public class MySymbolTablesStack extends MyStack<MyIDictionary<String, Value>> {
//    public MySymbolTablesStack deepCopy() {
//        MySymbolTablesStack newStack = new MySymbolTablesStack();
//        MyStack<MyIDictionary<String, Value>> tempStack = new MyStack<>();
//
//        while (!this.stack.empty())
//            tempStack.push(this.stack.pop());
//
//        while (!tempStack.isEmpty()) {
//            try {
//                stack.push(tempStack.peek());
//            } catch (InterpreterException e) {
//                throw new RuntimeException(e);
//            }
//            try {
//                newStack.push(tempStack.pop().deepCopy());
//            } catch (ADTException | InterpreterException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        return newStack;
//    }
//
//}

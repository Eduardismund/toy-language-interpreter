package toyLanguageInterpreter.model;

import toyLanguageInterpreter.exceptions.*;
import toyLanguageInterpreter.model.adt.*;
import toyLanguageInterpreter.model.statements.CompStmt;
import toyLanguageInterpreter.model.statements.IStmt;
import toyLanguageInterpreter.model.statements.NopStmt;
import toyLanguageInterpreter.model.types.Type;
import toyLanguageInterpreter.model.values.StringValue;
import toyLanguageInterpreter.model.values.Value;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, Value> symTable;
    private MyIList<Value> out;
    private IStmt originalProgram;
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    private MyIHeap<Integer,Value> heap;
    private int id;
    private static int currentId = 1;

    public Boolean isNotCompleted(){
        return !this.exeStack.isEmpty();
    }

    public synchronized void setId(){
        this.id =currentId;
        currentId++;
    }
    public PrgState oneStep() throws MyException, ADTException, StatementException, ExpressionException, InterpreterException, FileNotFoundException {
        if(exeStack.isEmpty()) throw new MyException("prgstate stack is empty");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }

    public PrgState(MyIStack<IStmt> exeStack, MyIDictionary<String, Value> symTable, MyIList<Value> out,
                    IStmt originalProgram, MyIDictionary<StringValue, BufferedReader> fileTable, MyIHeap<Integer,Value> heap) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.originalProgram = originalProgram.deepCopy();
        this.exeStack.push(originalProgram);
        this.fileTable = fileTable;
        this.heap = heap;
        setId();
    }

    public Integer getId_Thread(){
        return this.id;
    }

    public MyIHeap<Integer,Value> getHeap(){
        return heap;
    }

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public MyIDictionary<String, Value> getSymTable() {
        return symTable;
    }

    public MyIList<Value> getOut() {
        return out;
    }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    public List<IStmt> distinctStataments() {
        MyTree<IStmt> tree =  new MyTree<IStmt>();
        List<IStmt> inOrderList=new LinkedList<IStmt>();
        if(!getExeStack().toListS().isEmpty()) {
            tree.setRoot(toTree( (IStmt)getExeStack().toListS().get(0)));
            tree.inorderTraversal(inOrderList, tree.getRoot());
        }
        return inOrderList;
    }

    private Node<IStmt> toTree(IStmt stmt) {
        Node node;
        if (stmt instanceof CompStmt){
            CompStmt comptStmt = (CompStmt) stmt;
            node = new Node<>(new NopStmt());
            node.setLeft(new Node<>(comptStmt.getFirst()));
            node.setRight(toTree( comptStmt.getSecond()));
        }
        else {
            node = new Node<>(stmt);
        }
        return node;

    }

    @Override
    public String toString() {
        String result = "------- Current Program State ------\n";
        result += "ID = " + id + "\n";
        result += "executionStack = \n" + exeStack + "\n";
        result += ", symbolTable = " + symTable + "\n";
        result += ", output = " + out + "\n";
        result += ", fileTable = " + fileTable + "\n";
        result += ", heap = " + heap.toString() + "\n";
        result += ", originalProgram = " + originalProgram + "\n\n";
        return result;
    }

    public void resetProgramState(){
        this.out = new MyList<>();
        this.exeStack = new MyStack<>();
        this.fileTable = new MyDictionary<>();
        this.symTable = new MyDictionary<>();
        this.exeStack.push(originalProgram);
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return this.fileTable;
    }

    public void setHeap(MyIHeap<Integer, Value> heap) {
        this.heap = heap;
    }
    public void setSymbolTable(MyIDictionary<String, Value> symTable) {
        this.symTable = symTable;
    }

    public void setFileTable(MyIDictionary<StringValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public void setExecutionStack(MyIStack<IStmt> stack) {
        this.exeStack = stack;
    }

    public void setOutput(MyIList<Value> out) {
        this.out = out;
    }
    public void typeCheck() throws InterpreterException, StatementException, ADTException, ExpressionException {
        MyIDictionary<String, Type> typeEnvironment = new MyDictionary<>();
        originalProgram.typeCheck(typeEnvironment);
    }
}

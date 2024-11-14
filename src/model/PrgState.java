package model;

import model.adt.*;
import model.statements.CompStmt;
import model.statements.IStmt;
import model.statements.NopStmt;
import model.values.StringValue;
import model.values.Value;

import java.io.BufferedReader;
import java.util.LinkedList;
import java.util.List;

public class PrgState {
    private MyIExeStack<IStmt> exeStack;
    private MyISymTable<String, Value> symTable;
    private MyIOut<Value> out;
    private IStmt originalProgram;
    private MyIFilename<StringValue, BufferedReader> fileTable;

    public PrgState(MyIExeStack<IStmt> exeStack, MyISymTable<String, Value> symTable, MyIOut<Value> out,
                    IStmt originalProgram, MyIFilename<StringValue, BufferedReader> fileTable) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.originalProgram = originalProgram.deepCopy();
        this.exeStack.push(originalProgram);
        this.fileTable = fileTable;
    }

    public MyIExeStack<IStmt> getExeStack() {
        return exeStack;
    }

    public MyISymTable<String, Value> getSymTable() {
        return symTable;
    }

    public MyIOut<Value> getOut() {
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
        result += "executionStack = " + distinctStataments() + "\n";
        result += ", symbolTable = " + symTable + "\n";
        result += ", output = " + out + "\n";
        result += ", fileTable = " + fileTable + "\n";
        result += ", originalProgram = " + originalProgram + "\n\n";
        return result;
    }

    public void resetProgramState(){
        this.out = new MyOut<>();
        this.exeStack = new MyExeStack<>();
        this.fileTable = new MyFilename<>();
        this.symTable = new MySymTable<>();
        this.exeStack.push(originalProgram);
    }

    public MyIFilename<StringValue, BufferedReader> getFileTable() {
        return this.fileTable;
    }


    public void setSymbolTable(MyISymTable<String, Value> symTable) {
        this.symTable = symTable;
    }

    public void setFileTable(MyIFilename<StringValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public void setExecutionStack(MyIExeStack<IStmt> stack) {
        this.exeStack = stack;
    }

    public void setOutput(MyIOut<Value> out) {
        this.out = out;
    }
}

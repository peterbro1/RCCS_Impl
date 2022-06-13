package me.gmx.process.thread;

import me.gmx.parser.CCSTransitionException;
import me.gmx.process.CCSTransition;
import me.gmx.process.nodes.Label;
import me.gmx.process.nodes.LabelKey;
import me.gmx.process.process.Process;

import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;


public class ReversibleThreadMemory {

    private LinkedHashMap<LabelKey, CCSTransition> reversibilityTable;
    private Stack<LabelKey> stack;

    public ReversibleThreadMemory(){
        reversibilityTable = new LinkedHashMap<>();
        stack = new Stack<>();
    }

    //TODO: do the cloning here?
    public void remember(Process f, Process t, Label l){
        CCSTransition transition = new CCSTransition(f,t,l);
        LabelKey key = new LabelKey(l);
        reversibilityTable.put(key,transition);
        stack.push(key);
    }
    public void remember(Process f, Label l, LabelKey key){
        CCSTransition transition = new CCSTransition(f.clone(),null,l);
        reversibilityTable.put(key,transition);
        stack.push(key);
    }


    public void remember(Process from, Label label){
        CCSTransition transition = new CCSTransition(from.clone(), null, label);
        LabelKey key = new LabelKey(label);
        reversibilityTable.put(key,transition);
        stack.push(key);
    }

    public LabelKey recentHistory(){
        return stack.peek();
    }

    public void clear(){
        stack.clear();
        reversibilityTable.clear();
    }

    public CCSTransition pop(){
        LabelKey k = stack.pop();
        CCSTransition t = reversibilityTable.get(k);
        cleanup();
        return t;
    }

    public boolean isEmpty(){
        return stack.isEmpty();
    }

    public boolean containsKey(LabelKey key){
        return stack.contains(key);
    }

    public CCSTransition lookup(LabelKey key){
        for (LabelKey l : stack){
            if (l.equals(key))
                return reversibilityTable.get(key);
        }
        return null;
    }

    public Process rewindTo(LabelKey key) throws Exception{
        if (!containsKey(key))
            throw new Exception("Could not rewind"); //TODO: improve
        while(!stack.isEmpty()){
            LabelKey k = stack.pop();
            if (k.equals(key))
                return reversibilityTable.get(k).from;
        }
        return null;
    }


    /**
     * Trims the stack and list, deleting all memory elements not on the stack
     */
    public void cleanup(){
        for (LabelKey key : stack)
            if (!reversibilityTable.containsKey(key))
                reversibilityTable.remove(key);
    }


}

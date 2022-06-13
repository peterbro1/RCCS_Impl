package me.gmx.process.thread;

import me.gmx.process.CCSTransition;
import me.gmx.process.nodes.Label;
import me.gmx.process.nodes.LabelKey;

import java.util.Hashtable;
import java.util.Stack;

/**
 * Something like a table maybe? Not really sure how this is going to work.
 * Maybe something like key -> {(process 1), (process 2)}
 */
public class ReversibleThreadMemory {

    private Hashtable<LabelKey, CCSTransition> reversibilityTable;
    private static Stack<CCSTransition> stack;

    public ReversibleThreadMemory(){
        reversibilityTable = new Hashtable<>();
        stack = new Stack<>();
    }

    //TODO: Implement? May not be needed
    public static void addReversibilityStep(Process f, Process t, Label l){
        stack.push(new CCSTransition(f, t, l));
    }

    public static CCSTransition lookup(LabelKey key){
        for (CCSTransition t : stack){
            if (t.key.equals(key))
                return t;
        }
        return null;
    }


}

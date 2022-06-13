package me.gmx.process.process;

import me.gmx.RCCS;
import me.gmx.process.nodes.Label;
import me.gmx.process.nodes.LabelKey;
import me.gmx.process.nodes.ProgramNode;
import me.gmx.process.thread.ReversibleThreadMemory;
import me.gmx.util.SetUtil;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public abstract class Process extends ProgramNode{

    Set<Label> restrictions = new HashSet<>();

    protected ReversibleThreadMemory memory = new ReversibleThreadMemory();

    public Process(){}

    public void setMemory(ReversibleThreadMemory memory){
        this.memory = memory;
    }

    public void addRestrictions(Collection<Label> labels){
        for (Label l : labels){
            restrictions.add(l);
        }
    }

    /**
     * Save a transition to memory through the action of the given label
     * This is the method that actually 'acts' on a label
     * @param label
     */
    protected void rememberTransition(Label label){
        Process p = this.clone();
        memory.remember(p,this.actOn(label),label, new LabelKey(label));
    }

    public Collection<Label> getRestriction(){
        return restrictions;
    }

    public Process(Collection<Label> restrictions){
        this.restrictions.addAll(restrictions);
    }

    /**
     * Determines whether process can act on given label, without actually acting on it.
     * @param label label to act on
     * @return True if given label is able to be acted on, false otherwise
     */
    public boolean canAct(Label label){
        RCCS.log(String.format("Checking if %s can act on %s",represent(),label.origin()));
        return getActionableLabels().contains(label);
    }

    /**
     * Internal act method to differentiate different processes behavior when given
     * a certain label to act on
     * @param label label to act on
     * @return process after having been acted on given label
     */
    protected abstract Process actOn(Label label);


    /**
     * Act on a given label. This is the only method that should be called outside
     * of subclasses.
     * @param label label to act on
     * @return will return this, after having acted on the given label
     */
    public Process act(Label label){
        if (label instanceof LabelKey){
            if (memory.containsKey((LabelKey) label)){
                try {
                    return memory.rewindTo((LabelKey) label);
                }catch(Exception e){
                    e.printStackTrace();
                    }
                }
        }
        rememberTransition(label);
        return this;
    }

    /**
     * Internal represent method to be called by subclasses. This sets the 'format' for
     * all subclasses to take regarding keys and other syntax.
     * @param base Subclass representation
     * @return
     */
    protected String represent(String base){
        String s = "";
        s += String.format("%s",base);
        s+= getRestriction().isEmpty() ? "" : String.format("\\{%s}",SetUtil.csvSet(getRestriction()));
        return s;
    }
    /**
     * Base superclass method for getting labels. Only adds any keys
     * @return
     */
    public Collection<Label> getActionableLabels(){
        Set<Label> l = new HashSet<>();
        if (!memory.isEmpty())
            l.add(memory.recentHistory());
        return l;
    }

    public abstract String origin();


    /**
     * Clones process, but shares memory.
     * @return A pseudo deep clone of this, sharing the same memory object
     */
    protected abstract Process clone();

    public abstract Collection<Process> getChildren();

    /**
     * A formatted version of this process to be printed. This is the only method that
     * should be called to print to screen unless you will be comparing processes
     * @return
     */
    public abstract String represent();



}

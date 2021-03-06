package me.gmx.process.process;

import me.gmx.RCCS;
import me.gmx.process.nodes.Label;
import me.gmx.process.nodes.LabelKey;
import me.gmx.process.nodes.ProgramNode;
import me.gmx.process.nodes.TauLabelNode;
import me.gmx.util.RCCSFlag;
import me.gmx.util.SetUtil;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public abstract class Process extends ProgramNode {

    //Storing a key to previous life
    LabelKey key = null;

    //Actual process of previous life
    Process previousLife = null;

    //Passthru key for summation processes
    protected LabelKey ghostKey = null;

    protected boolean isGhost = false;

    Set<Label> restrictions = new HashSet<>();

    public boolean displayKey = !RCCS.config.contains(RCCSFlag.HIDE_KEYS);
    public Process(){}

    /**
     * Removes restrictions from given process. Because of the way label equality
     * checking works, the removeAll method in collection does not work.
     * @param labels Base set of labels to apply restrictions to
     * @return Collection of labels with restrictions removed
     */
    protected Collection<Label> withdrawRestrictions(Collection<Label> labels){
        Iterator<Label> iter = labels.iterator();
        while (iter.hasNext()){
            Label l = iter.next();
            for (Label r : getRestriction()){
                if (r.getClass().equals(l.getClass())) //Make sure 'a\{a} works
                    if (r.getChannel().equals(l.getChannel()))
                        iter.remove();
            }
        }
        return labels;
    }

    //Very annoying that java cannot process collections as ... streams :(
    public void addRestrictions(Collection<Label> labels){
        restrictions.addAll(labels);
    }

    public Collection<Label> getRestriction(){
        return restrictions;
    }

    public Process(Collection<Label> restrictions){
        this.restrictions.addAll(restrictions);
    }

    public Process(Collection<Label> restrictions, Process previousLife, LabelKey key){
        this.restrictions.addAll(restrictions);
        this.previousLife = previousLife;
        this.key = key;
    }

    public abstract Process clone();

    /**
     * Set past life of this process to the given process
     * Note that it should be good practice to clone a process before giving it to another
     * in the form of a past life.
     * @param p process (hopefully a clone) to set as past life
     */
    protected void setPastLife(Process p){
        this.previousLife = p;
    }

    /**
     * Set CCSK key to provided LabelKey
     * @param key key to set
     */
    protected void setKey(LabelKey key){
        this.key = key;
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

    @Deprecated
    public boolean isAnnotated(){
        return getActionableLabels().stream().anyMatch(LabelKey.class::isInstance);
    }

    /**
     * Act on a given label. This is the only method that should be called outside
     * of subclasses.
     * @param label label to act on
     * @return will return this, after having acted on the given label
     */
    public Process act(Label label){
        if (label instanceof LabelKey) {
            if (ghostKey == null && hasKey()) //Just a regular process? Is it reversible?
                if (getKey().equals(label)) //Make sure keys match
                    return previousLife; //Rewind!
            //Ghost key present?
            if (label.equals(ghostKey)) { //Is the key correct?
                ghostKey = null;
                return previousLife; //Okay, rewind!
            }else{
                return this.actOn(label); //Key incorrect? passthru!
            }

        }else if (label instanceof TauLabelNode){
            //TODO: ?
        }
        return this.actOn(label);
    }


    /**
     * Returns if this process has a CCSK key assigned to it
     * @return true if there is a key assigned to this process
     */
    public boolean hasKey(){
        return this.key != null;
    }

    /**
     * Returns the assigned key
     * @return this process's key, can be null
     */
    public LabelKey getKey(){
        return this.key;
    }

    /**
     * A formatted version of this process to be printed. This is the only method that
     * should be called to print to screen unless you will be comparing processes
     * @return String to be displayed to user that represents this process
     */
    public abstract String represent();

    /**
     * Internal represent method to be called by subclasses. This sets the 'format' for
     * all subclasses to take regarding keys and other syntax.
     * @param base Subclass representation
     * @return Internal string to be called and replaced
     */
    protected String represent(String base){
        String s = "";
        s += (hasKey() && displayKey) ? String.format("%s%s"
                , getKey().origin()
                , base)
                : String.format("%s",base);
        s+= getRestriction().isEmpty() ? "" : String.format("\\{%s}",SetUtil.csvSet(getRestriction()));

        return s;
    }

    public abstract Collection<Process> getChildren();

    /**
     * Base superclass method for getting labels. Only adds any keys
     * @return If hasKey, then key, otherwise empty set
     */
    public Collection<Label> getActionableLabels(){
        Set<Label> l = new HashSet<>();
        if (hasKey())
            l.add(getKey());

        return l;
    }

    protected abstract Collection<Label> getActionableLabelsStrict();

    public abstract String origin();



}

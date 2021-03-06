package me.gmx.process.process;

import me.gmx.process.nodes.Label;

import java.util.Collection;
import java.util.Collections;

public class ProcessImpl extends Process implements ActionableProcess{

    public ProcessImpl(String s) {
        this.origin = s;
    }

    @Override
    public Process clone() {
        return new ProcessImpl(origin);
    }

    //TODO: implement
    @Override
    public void execute(){

    }

    @Override
    public boolean canAct(Label label) {
        return false;
    }

    @Override
    public Process actOn(Label label) {
        return new NullProcess();
    }

    public String represent() {
        return super.represent(origin());
    }

    @Override
    public Collection<Process> getChildren() {
        return Collections.emptySet();
    }

    @Override
    public Collection<Label> getActionableLabels() {
        Collection<Label> l = getActionableLabelsStrict();
        return withdrawRestrictions(l);
    }

    @Override
    protected Collection<Label> getActionableLabelsStrict() {
        return super.getActionableLabels();
    }

    @Override
    public String origin(){
        return origin;
    }

}

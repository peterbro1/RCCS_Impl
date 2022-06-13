package me.gmx.process.process;

import me.gmx.process.nodes.Label;
import me.gmx.process.thread.ActionableProcess;

import java.util.Collection;
import java.util.Collections;

public class ProcessImpl extends Process implements ActionableProcess {

    public ProcessImpl(String s) {
        this.origin = s;
    }

    @Override
    public Process clone() {
        ProcessImpl p = new ProcessImpl(origin());
        p.addRestrictions(this.getRestriction());
        p.annotation = annotation;
        return p;
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
        return super.getActionableLabels();
    }

    @Override
    public String origin(){
        return origin;
    }



}

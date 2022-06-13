package me.gmx.process.thread;

import me.gmx.RCCS;
import me.gmx.parser.CCSTransitionException;
import me.gmx.process.ProcessTemplate;
import me.gmx.process.nodes.Label;
import me.gmx.process.nodes.LabelKey;
import me.gmx.process.process.Process;

import java.util.Collection;
import java.util.LinkedList;

public class ProcessContainer {

    private Process parentProcess;
    private CCSTree tree;
    public ProcessContainer(Process p){
        parentProcess = p;
        tree = new CCSTree(p);

    }


    public Collection<Label> getActionableLabels(){
        Collection<Label> nodes = parentProcess.getActionableLabels();

        return nodes;
    }

    public String prettyString(){
        return parentProcess.represent();
    }

    public boolean canAct(Label node){
        return getActionableLabels().contains(node);
    }


    public ProcessContainer actOn(Label node){
        if (node instanceof LabelKey){
            //TODO: reverse
        }
        if (parentProcess.canAct(node)) {
            parentProcess = parentProcess.act(node);
            return this;
        }
        throw new CCSTransitionException(node);
    }



}

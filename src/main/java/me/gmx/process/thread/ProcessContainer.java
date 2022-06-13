package me.gmx.process.thread;

import me.gmx.parser.CCSTransitionException;
import me.gmx.process.ProcessTemplate;
import me.gmx.process.nodes.Label;
import me.gmx.process.nodes.LabelKey;
import me.gmx.process.process.Process;

import java.util.Collection;
import java.util.LinkedList;

public class ProcessContainer {

    private ReversibleThreadMemory memory;
    private Process parentProcess;

    public ProcessContainer(Process p){
        parentProcess = p;
        memory = new ReversibleThreadMemory();
    }


    public Collection<Label> getActionableLabels(){
        Collection<Label> nodes = parentProcess.getActionableLabels();

        if (!memory.isEmpty())
            nodes.add(memory.recentHistory());

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
            try {
                parentProcess = memory.rewindTo((LabelKey) node);
                return this;
            }catch (Exception e){
                throw new CCSTransitionException(parentProcess, node);
            }
        }
        if (parentProcess.canAct(node)) {
            memory.remember(parentProcess.clone(), node);
            parentProcess = parentProcess.act(node);
            return this;
        }
        throw new CCSTransitionException(node);
    }



}

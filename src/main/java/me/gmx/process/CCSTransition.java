package me.gmx.process;

import me.gmx.process.nodes.Label;
import me.gmx.process.nodes.LabelKey;

/**
 * Basically a storage to hold a transition step. This is to be used in the central
 * reversibility table. It will be stored with a key to keep track of things. I'm pretty tired,
 * so this may not be feasible. We will see tomorrow.
 */


public class CCSTransition {

    public Process from, to;
    public Label label;
    public LabelKey key;

    public CCSTransition(Process f, Process t, Label l){
        this.from = f;
        this.to = t;
        this.label = l;
        this.key = new LabelKey(l);
    }


}

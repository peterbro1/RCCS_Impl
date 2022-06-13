package me.gmx.process;

import me.gmx.process.nodes.Label;
import me.gmx.process.nodes.LabelKey;
import me.gmx.process.process.Process;

import java.util.Collection;
import java.util.HashSet;

/**
 * Basically a storage to hold a transition step. This is to be used in the central
 * reversibility table. It will be stored with a key to keep track of things. I'm pretty tired,
 * so this may not be feasible. We will see tomorrow.
 */

public class CCSTransition {
    public me.gmx.process.process.Process from, to;
    public Label label;
    public Collection<LabelKey> dependsOn;

    public CCSTransition(me.gmx.process.process.Process f, me.gmx.process.process.Process t, Label l){
        this.from = f;
        this.to = t;
        this.label = l;
        dependsOn = new HashSet<LabelKey>();
    }

    public void addDependence(LabelKey key){
        dependsOn.add(key);
    }



}

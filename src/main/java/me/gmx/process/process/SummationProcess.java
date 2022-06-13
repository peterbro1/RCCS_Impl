package me.gmx.process.process;

import me.gmx.parser.CCSGrammar;
import me.gmx.parser.CCSTransitionException;
import me.gmx.process.nodes.Label;
import me.gmx.process.nodes.LabelKey;
import me.gmx.process.nodes.TauLabelNode;
import me.gmx.util.SetUtil;

import java.util.Collection;
import java.util.Collections;

public class SummationProcess extends ComplexProcess{

    public SummationProcess(Process left, Process right) {
        super(left,right,CCSGrammar.OP_SUMMATION);
    }

    /**
     * Return only one of the sides, TODO not sure how this should work. Right now it prioritizes the left side
     * @param label Label to act on
     * @return self-process, having acted on label
     */

    @Override
    public Process actOn(Label label) {
        if (left.canAct(label)) {
            Process p = left.act(label);
            return p;
        }else if (right.canAct(label)){
            Process p = right.act(label);
            return p;
        }else throw new CCSTransitionException(this,label);
    }

    @Override
    public Collection<Label> getActionableLabels(){
        Collection<Label> s = super.getActionableLabels();
        Collection<Label> l = left.getActionableLabels();
        Collection<Label> r = right.getActionableLabels();
        for (Label ll: l)
            for (Label rl : r){
                ll.addSynchronizationLock(rl);
                rl.addSynchronizationLock(ll);
            }

        s.addAll(l);
        s.addAll(r);
        return s;
    }

    @Override
    public SummationProcess clone() {
        SummationProcess p = new SummationProcess(left.clone(), right.clone());
        p.addRestrictions(restrictions);
        return p;
    }

}

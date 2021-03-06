package me.gmx.process.process;

import me.gmx.parser.CCSGrammar;
import me.gmx.parser.CCSTransitionException;
import me.gmx.process.nodes.Label;
import me.gmx.process.nodes.LabelKey;
import me.gmx.process.nodes.TauLabelNode;
import me.gmx.util.SetUtil;

import java.util.*;

public class ConcurrentProcess extends ComplexProcess{
    /**
     * @param left - left side me.gmx.process
     * @param right - right side me.gmx.process
     */
    public ConcurrentProcess(Process left, Process right) {
        super(left,right, CCSGrammar.OP_CONCURRENT);
        displayKey = false; //Don't want concurrent processes to show keys, regardless of circumstance
    }
    //Note: Concurrent processes will never need to hold a key, because data is not destroyed at
    //the complex-process level in this situation.
    @Override
    public Process actOn(Label label) {
        if (left.canAct(label)) {
            left = left.act(label);
        }
        if (right.canAct(label)) {
            right = right.act(label);
        }

        return this;
    }

    public boolean hasKey(){
        return false;
    }

    @Override
    public ConcurrentProcess clone() {
        ConcurrentProcess p = new ConcurrentProcess(left.clone(), right.clone());
        p.isGhost = isGhost;
        p.setPastLife(previousLife);
        p.setKey(key);
        p.addRestrictions(restrictions);
        return p;
    }

    /**
     * Returns a set of labels that can be acted on, including tau matches. Theoretically, this is the only
     * process that should be able to support synchronizations.
     * @return Set of labels that can be acted on, including tau matches
     */
    @Override
    public Collection<Label> getActionableLabels(){
        Collection<Label> l = getActionableLabelsStrict();
        l.addAll(SetUtil.getTauMatches(l));
        return SetUtil.removeUnsyncableKeys(this, withdrawRestrictions(l));
    }

    protected Collection<Label> getActionableLabelsStrict(){
        Collection<Label> ll, l, r;

        ll = super.getActionableLabels();
        l = left.getActionableLabels();
        r = right.getActionableLabels();
        ll.addAll(l);
        ll.addAll(r);
        return ll;
    }

}

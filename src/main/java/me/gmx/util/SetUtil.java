package me.gmx.util;

import me.gmx.RCCS;
import me.gmx.process.nodes.*;
import me.gmx.process.process.ComplexProcess;
import me.gmx.process.process.ConcurrentProcess;
import me.gmx.process.process.Process;
import me.gmx.process.process.SummationProcess;

import java.security.KeyPair;
import java.util.*;

public class SetUtil {


    public static String csvSet(Collection<Label> set){
        if (set.isEmpty())
            return "";
        StringBuilder sb = new StringBuilder();
        for (Label o : set){
            sb.append(o.origin());
            sb.append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    /**
     * Returns a hashset of TauLabelNodes representing matching complements of labels provided in the given collection
     * @param nodes Collection of Labels to find tau matches from
     * @return Set of TauLabelNode that apply to the given collection
     */
    public static Collection<TauLabelNode> getTauMatches(Collection<Label> nodes){
        Set<TauLabelNode> tau = new HashSet<>();
        for (Label node : nodes){
            if (node instanceof ComplementLabelNode){
                //Cool, there is a complement in the set. Let's see if any matches
                for (Label innerNode : nodes)
                    if (innerNode != node && !(innerNode instanceof LabelKey))
                        if (node.isComplementOf(innerNode))
                            if (node.canSynchronize(innerNode) && innerNode.canSynchronize(node)) {
                                //Cool, we found a complement, let's add it to our map.
                                TauLabelNode n = new TauLabelNode(node, innerNode); //Don't want duplicates
                                if (tau.contains(n) || nodes.contains(n))
                                    n.destroy();
                                else tau.add(n);
                            }
            }
        }
        return tau;
    }

    public static Collection<Label> removeUnsyncableKeys(ComplexProcess p, Collection<Label> labels){
        Iterator<Label> iter = labels.iterator();
        while (iter.hasNext()){
            Label l = iter.next();
            if (!(l instanceof LabelKey))//we only care about keys
                continue;
            LabelKey key = (LabelKey) l;
            if (!(key.from instanceof TauLabelNode))
                continue; //don't care about regular keys

            if (!SetUtil.recursiveIsSyncable(p,key))//both sides need to be able to do it
                iter.remove();
        }
        return labels;
    }

    public static boolean recursiveIsSyncable(Process p, LabelKey key){
        if (!(p instanceof ComplexProcess))
            return false;
        ComplexProcess cp = (ComplexProcess) p;

        if (cp.left.canAct(key) && cp.right.canAct(key))
            return true;
        else return (recursiveIsSyncable(cp.left, key)
                || recursiveIsSyncable(cp.right, key));
    }

}
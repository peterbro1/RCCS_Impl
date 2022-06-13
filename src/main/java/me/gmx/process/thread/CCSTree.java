package me.gmx.process.thread;

import me.gmx.process.nodes.Label;
import me.gmx.process.nodes.LabelKey;

import java.util.*;

public class CCSTree {

    private CCSTreeNode root;
    public CCSTree(Process p){
        root = new CCSTreeNode(p);
    }

    public boolean containsNode(LabelKey key){
        return containsNodeInternal(root, key);
    }

    private boolean containsNodeInternal(CCSTreeNode current, LabelKey key){
        for (Map.Entry<LabelKey, CCSTreeNode> entry : current.getLinks().entrySet()){
            if (entry.getKey().equals(key))
                return true;
        }
        for (CCSTreeNode n : current.getChildren())
            containsNodeInternal(n,key);
        return false;
    }


    public class CCSTreeNode{

        private CCSTreeNode parent;
        private Process p;
        private HashMap<LabelKey, CCSTreeNode> children;
        public CCSTreeNode(Process p){
            this.p = p;
            children = new HashMap<>();
        }

        public CCSTreeNode(Process p, CCSTreeNode parent){
            this.p = p;
            this.parent = parent;
            children = new HashMap<>();
        }

        public void addChild(Process p, Label l){
            children.put(new LabelKey(l), new CCSTreeNode(p,this));
        }

        public Collection<CCSTreeNode> getChildren(){
            return this.children.values();
        }

        public HashMap<LabelKey, CCSTreeNode> getLinks(){
            return children;
        }

        public CCSTreeNode getParent(){
            return parent;
        }
    }

}

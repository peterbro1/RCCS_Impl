package me.gmx.process.thread;

import me.gmx.RCCS;
import me.gmx.process.nodes.LabelKey;
import me.gmx.process.process.Process;

import java.util.*;

public class CCSTree {

    private CCSTreeNode root;
    private int maxDepth;

    public CCSTree(Process p){
        root = new CCSTreeNode(p);
        maxDepth=0;
    }

    public CCSTreeNode getRootNode(){
        return root;
    }

    public List<CCSTreeNode> getNodes(){
        CCSTreeNode current = root;
        ArrayList<CCSTreeNode> nodes = new ArrayList<>();
        nodes.addAll(getNodes(current));
        return nodes;
    }

    private List<CCSTreeNode> getNodes(CCSTreeNode current){
        ArrayList<CCSTreeNode> nodes = new ArrayList<>();
        for (CCSTreeNode child : current.getChildren()){
            nodes.add(child);
            nodes.addAll(getNodes(child));
        }
        return nodes;
    }

    public void display(){
        int i = 0;
        CCSTreeNode current = root;
        RCCS.log(String.format("Traversing node: %s", current.getData().represent()));
        System.out.println(current.display());
        for (CCSTreeNode node : current.getChildren()){
            RCCS.log(String.format("Traversing node: %s", node.getData().represent()));
            System.out.println(node.display());
        }
    }

    public class CCSTreeNode{
        private Process data = null;
        private HashMap<LabelKey, CCSTreeNode> children = new HashMap<>();
        private CCSTreeNode parent = null;
        public int depth;

        public CCSTreeNode(Process data) {
            this.data = data;
            this.depth = 0;
        }

        public CCSTreeNode addChild(CCSTreeNode child, LabelKey key) {
            child.setParent(this);
            child.setDepth(this.depth+1);
            this.children.put(key, child);
            return child;
        }

        public CCSTreeNode addChild(Process data, LabelKey key) {
            CCSTreeNode newChild = new CCSTreeNode(data);
            newChild.setParent(this);
            newChild.setDepth(depth+1);
            return this.addChild(newChild, key);
        }

        public void addChildren(HashMap<LabelKey, CCSTreeNode> children) {
            for(CCSTreeNode t : children.values()) {
                t.setParent(this);
                t.setDepth(depth+1);
            }
            this.children.putAll(children);
        }

        public void setDepth(int depth){
            this.depth = depth;
        }

        public List<CCSTreeNode> getChildren() {
            return children.values().stream().toList();
        }

        public boolean isLeafNode(){
            return children.isEmpty();
        }

        public Process getData() {
            return data;
        }

        public void setData(Process data) {
            this.data = data;
        }

        private void setParent(CCSTreeNode parent) {
            this.parent = parent;
        }

        public CCSTreeNode getParent() {
            return parent;
        }

        public String display(){
            StringBuilder b = new StringBuilder();
            b.append(String.format("%d | %s \n", depth, getData().represent()));
            for (Map.Entry<LabelKey, CCSTreeNode> entry : children.entrySet()){
                b.append(String.format("-%s-> %s \n"
                        , entry.getKey().origin()
                        , entry.getValue().getData().represent()));
            }
            return b.toString();
        }
    }

}

package me.gmx.process.nodes;

import java.util.UUID;

public class LabelKey extends Label {

    Label from;
    public LabelKey(Label node){
        this.id = UUID.randomUUID();
        this.from = node;
    }

    public boolean sameOrigin(LabelKey k){
        return k.from.equals(from);
    }

    /**
     * To replicate UUIDs for tau transitions
     * @param node
     * @param id
     */
    public LabelKey(Label node, UUID id){
        this.id = id;
        this.from = node;
    }

    @Override
    public String toString(){
        return this.id.toString();
    }


    @Override
    public String origin() {
        return String.format("[%s]",from.origin());
    }

    public LabelKey clone(){
        return new LabelKey((Label)from, from.getId());
    }
}

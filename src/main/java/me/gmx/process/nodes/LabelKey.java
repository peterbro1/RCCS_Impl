package me.gmx.process.nodes;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class LabelKey extends Label {

    Label from;
    public LabelKey(Label node){
        this.id = node.getId();
        this.from = node;
    }

    public boolean sameOrigin(LabelKey k){
        return k.from.equals(from);
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
        return new LabelKey((Label)from);
    }
}

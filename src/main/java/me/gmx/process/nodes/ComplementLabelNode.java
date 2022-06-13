package me.gmx.process.nodes;

import me.gmx.parser.CCSGrammar;

import java.util.UUID;

/**
 * The "output" portion of a communication channel
 */
public class ComplementLabelNode extends Label{

    public ComplementLabelNode(String s) {
        origin = s;
        grammar = CCSGrammar.OUT_LABEL;
        this.id = UUID.randomUUID();
    }

    private ComplementLabelNode(ComplementLabelNode node){
        origin = node.origin();
        grammar = CCSGrammar.OUT_LABEL;
        id = node.getId();
    }

    public String toString(){
        return this.origin();
    }

    @Override
    public ComplementLabelNode clone(){
        return new ComplementLabelNode(this);
    }

}

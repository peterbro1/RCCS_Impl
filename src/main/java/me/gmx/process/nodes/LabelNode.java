package me.gmx.process.nodes;

import me.gmx.parser.CCSGrammar;

import java.util.UUID;

public class LabelNode extends Label {

    public CCSGrammar grammar;
    public LabelNode(String s) {
        origin = s;
        grammar = CCSGrammar.LABEL;
        this.id = UUID.randomUUID();
    }

    public LabelNode(String s, UUID id){
        origin = s;
        grammar = CCSGrammar.LABEL;
        this.id = id;
    }


}

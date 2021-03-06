package me.gmx.util;

public enum RCCSFlag {
    DEBUG("Prints debug info",
            "--debug"),
    HELP_MSG("Print this help message",
            "--help"),
    UNIQUE_CHANNELS("[broken] Should each channel's identity be dictated by it's unique ID",
            "--uC"),
    DIFFERENTIATE_LABELS("Labels are visibly differentiated by integers",
            "--dL"),
    HIDE_KEYS("Keys are hidden",
            "--hide-keys"),
    KEY_MATCHING_MODE("[broken] Should a label's identity be determined by its unique ID?",
            "--kM"), //0 = true 1 = false
    SUMMATION_STYLE_1("Alternative display mode for summation processes. Reversible summations are not annotated",
            "--sA"),
/*    SUMMATION_STYLE_2("Alternative display mode for summation processes. Reversible summations are annotated",
            "--sB", true),*/
    SUMMATION_STYLE_3("Alternative display mode for summation processes. Reversible summations are hidden after execution",
            "--sC"),
    EXPLICIT_NULL("Labels explicitly require a trailing process. Labels will no longer have an implicit null process attached.",
            "--eN"),
    HIDE_PARENTHESIS("Should parenthesis surrounding complex processes be omitted?",
            "--hP"),
    DISPLAY_NULL("Null processes will be displayed",
            "--dN"),
    IGNORE_UNRECOGNIZED("Unrecognized characters in process are ignored",
            "--iU"),
    KEYS_MATCH_LABELS("Keys will be visibly similar to the label they represent",
            "--kL");


    private String description;
    private String flagFlag;

    RCCSFlag(String desc, String flag){
        description = desc;
        flagFlag = flag;
    }

    public boolean doesMatch(String s){
        return s.equals(flagFlag);
    }

    public String getFlag(){
        return flagFlag;
    }
    public String getDescription(){
        return description;
    }


}
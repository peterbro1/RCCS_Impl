import me.gmx.process.nodes.Label;
import me.gmx.process.nodes.LabelKey;
import me.gmx.process.nodes.LabelNode;
import me.gmx.process.process.ActionPrefixProcess;
import me.gmx.process.process.ProcessImpl;
import me.gmx.process.process.Process;
import me.gmx.process.thread.CCSTree;
import org.junit.jupiter.api.Test;

import java.util.*;

public class StructureTest {

    @Test
    public void uuidTest(){
        ProcessImpl processimpl = new ProcessImpl("P");
        List<Label> prefixes = new ArrayList<Label>();
        prefixes.add(new LabelNode("a"));
        prefixes.add(new LabelNode("b"));
        Process p = new ActionPrefixProcess(processimpl, prefixes);
        System.out.println(p.represent());

        CCSTree tree = new CCSTree(p.clone());

        CCSTree.CCSTreeNode node = tree.getRootNode().addChild(p.clone().act(prefixes.get(0)), new LabelKey(prefixes.get(0)));
        node.addChild(node.getData().clone().act(prefixes.get(1)), new LabelKey(prefixes.get(1)));
        tree.display();


    }

}

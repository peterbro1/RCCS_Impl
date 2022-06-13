package me.gmx;

import me.gmx.process.ProcessTemplate;
import me.gmx.process.nodes.*;
import me.gmx.process.thread.ProcessContainer;
import me.gmx.util.SetUtil;

import java.util.*;

public class CCSInteractionHandler {

    public ProcessTemplate template;
    public Collection<RCCS.CCSFlag> flags;
    public CCSInteractionHandler(ProcessTemplate template, Collection<RCCS.CCSFlag> flags){
        this.template = template;
        this.flags = flags;
    }

    public boolean startInteraction(){
        Scanner scan = new Scanner(System.in);
        ProcessContainer container = new ProcessContainer(template.export());
        while(true){
            ArrayList<Label> actionable = new ArrayList<Label>(container.getActionableLabels());
            if (actionable.isEmpty())
                break;

            System.out.println("------| Actionable Labels |------");
            //Print out labels
            int i = 0;
            for (Label na : actionable)
                System.out.printf("[%d] %s%n",i++,na.origin());
            System.out.println("------------");
            System.out.println(String.format("%s", container.prettyString()));
            System.out.println("Please input the index of the label you'd like to act on:");
            String st = scan.next();
            Label n;
            if (st == "") continue;
            try{
                int in = Integer.parseInt(st);
                n = (Label) actionable.get(in);
            } catch(Exception e){
                System.out.println("Failed to recognize label!");
                continue;
            }

            try{
                System.out.println(String.format("%s -%s-> %s",
                        container.prettyString(),n.origin(),container.actOn(n).prettyString()));
            }catch (Exception e){
                //TODO: improve
                System.out.println("Could not act on label!");
                if (RCCS.DEBUG) e.printStackTrace();
            }




        }

        return true;
    }


}

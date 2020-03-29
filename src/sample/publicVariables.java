package sample;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import java.util.*;

public class publicVariables {

    public static ArrayList<Integer> starts = new ArrayList(100);
    public static ArrayList<Integer> bursts = new ArrayList(100);

    //                         [start,burst,number of process]
    public static ArrayList<ArrayList<Integer>> processData = new ArrayList(100);

    public static int quantum;


    public void makeCombination(){
        for (int x = 0; x < starts.size(); x++)
        {
            ArrayList<Integer> l = new ArrayList();
            l.add(starts.get(x));
            l.add(bursts.get(x));
            processData.add(l);
        }
    }

    public void ordering(){
        int s = processData.size();
        for (int i = 0; i < s; i++) {
            for (int j = 1; j < (s - i); j++) {
                if (processData.get(j - 1).get(0) > processData.get(j).get(0)) {
                    ArrayList<ArrayList<Integer>> l = new ArrayList();
                    l.clear();
                    l.add(processData.get(j - 1));
                    processData.remove(j - 1);

                    try {
                        processData.add(j, l.get(0));
                    } catch (Exception e) {
                        processData.set(j, l.get(0));
                    }
                }
            }
        }
    }


    public Button makeButton(int processIndex,int width)
    {
        Button H = new Button();
        //timeline variable to have a timeline dependent on all processes;
        H.setText("Process " + (processIndex) + "\n" + "\t   ");
        H.setAlignment(Pos.CENTER);
        H.setMinHeight(100);
        H.setMinWidth(width * 50);

        return H;
    }

}


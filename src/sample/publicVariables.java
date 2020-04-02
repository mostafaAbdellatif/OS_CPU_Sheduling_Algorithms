package sample;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import java.util.*;

public class publicVariables {

    public static int start = 0;
    public static int burst = 1;
    public static int priorits = 3;
    public static Boolean permative = Boolean.FALSE;

    public static ArrayList<Integer> starts = new ArrayList(100);
    public static ArrayList<Integer> bursts = new ArrayList(100);
    public static ArrayList<Integer> priority = new ArrayList(100);



    //                         [start,burst,number of process]
    public static ArrayList<ArrayList<Integer>> processData = new ArrayList(100);

    public static int quantum;


    public void makeCombination() {
        for (int x = 0; x < starts.size(); x++) {
            ArrayList<Integer> l = new ArrayList();
            l.add(starts.get(x));
            l.add(bursts.get(x));
            l.add(x + 1);
            l.add(priority.get(x));
            processData.add(l);
        }
    }

    public void ordering(int startORburst) {
        int s = processData.size();
        for (int i = 0; i < s; i++) {
            for (int j = 1; j < (s - i); j++) {
                if (processData.get(j - 1).get(startORburst) > processData.get(j).get(startORburst)) {
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

    public void indexingProcess() {
        for (int x = 0; x < processData.size(); x++) {
            processData.get(x).add(x + 1);
        }
    }

    public Button makeButton(int processIndex, int width) {
        Button H = new Button();
        //timeline variable to have a timeline dependent on all processes;
        H.setText("Process " + (processIndex) + "\n" + "\t   ");
        H.setAlignment(Pos.CENTER);
        H.setMinHeight(100);
        H.setMinWidth(width * 50);

        return H;
    }

    public void orderingStartThenBurst() {
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
                } else if (processData.get(j - 1).get(0) == processData.get(j).get(0)) {
                    if (processData.get(j - 1).get(1) > processData.get(j).get(1)) {
                        ArrayList<ArrayList<Integer>> l = new ArrayList();
                        l.clear();
                        l.add(processData.get(j - 1));
                        processData.remove(j - 1);
                        processData.add(j, l.get(0));
                    }
                }
            }
        }
    }


    public void ordering(int startORburst, int size) {
        int s = size;
        for (int i = 0; i < s; i++) {
            for (int j = 1; j < (s - i); j++) {
                if (processData.get(j - 1).get(startORburst) > processData.get(j).get(startORburst)) {
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

    //to sort the currently existing processes due to priority
    public void priorityOrderTimeline(int index,int siz) {

        if(siz != 1 && index != 1){
            for (int i = 0; i < index; i++) {
                for (int j = 1; j < (index - i); j++) {
                    if (processData.get(j - 1).get(priorits) > processData.get(j).get(priorits)) {
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
    }

    public int findTimelineIndex (int timeline,int siz){
            int index = 0;
            for (int i = 0; i < siz; i++) {
                if(processData.get(i).get(0)<=timeline) {
                    //System.out.println("inside fun index" + index);
                    index ++;
                }
            }
            return index;

    }

}

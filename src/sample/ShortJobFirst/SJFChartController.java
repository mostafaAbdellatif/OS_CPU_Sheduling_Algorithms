package sample.ShortJobFirst;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import sample.publicVariables;

import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayList;

public class SJFChartController {

    publicVariables publicVariablesObject = new publicVariables();

    ArrayList<ArrayList<Integer>> workProcess = new ArrayList(20);

    static Button past;
    static int lastProcess;
    static int width;

    @FXML
    private HBox gantchart;
    @FXML
    private Label averageTime;

    public void initialize() {


// clear previous data from prossessData
        publicVariablesObject.processData.clear();
        workProcess.clear();

// combine all process data together [start,burst,index]
        publicVariablesObject.makeCombination();

        /////////////////////Non-Permative//////////////////////////
        if (publicVariablesObject.permative == Boolean.FALSE) {
//1- order all processes according to arrival time
// (if 2 have same arrival time then order accordding to shortest burst)
            publicVariablesObject.orderingStartThenBurst();

            System.out.println(publicVariablesObject.processData);

            int timeLine = publicVariablesObject.processData.get(0).get(0);

            int size = publicVariablesObject.processData.size();

            for (int x = 0; x < size; x++) {
                try {
//2- make button for each process
                    Button H = publicVariablesObject.makeButton(publicVariablesObject.processData.get(0).get(2), publicVariablesObject.processData.get(0).get(1));
                    gantchart.getChildren().add(H);

                    timeLine += publicVariablesObject.processData.get(0).get(1);
                    publicVariablesObject.processData.remove(0);
                    int oSize = 0;

                    for (int m = 0; m < publicVariablesObject.processData.size(); m++) {
                        if (publicVariablesObject.processData.get(m).get(0) <= timeLine) {
                            oSize += 1;
                        }
                    }
                    publicVariablesObject.ordering(1, oSize);
                }catch (Exception e){
                    continue;
                }
            }
        }


        //////////////////////Permative//////////////////////////////
        else {

//1- order acoording to start yo burst
            publicVariablesObject.orderingStartThenBurst();
//2- save timeline as a counter according to first arrival time
            int timeLine = publicVariablesObject.processData.get(0).get(0);

            System.out.println(publicVariablesObject.processData);

            int size = publicVariablesObject.processData.size();
            int maxArrival = publicVariablesObject.processData.get(size - 1).get(0);
            int minArrival = publicVariablesObject.processData.get(0).get(0);
//3- loop till all arrival times are done
            for (int t = minArrival; t <= maxArrival; t++) {
//4- if timeline equals to arrival time of process, add it to arraylist(workprocess)
                if (publicVariablesObject.starts.contains(timeLine)) {
                    for (int x = 0; x < size; x++) {
                        if (timeLine == publicVariablesObject.processData.get(x).get(0)) {
                            workProcess.add(publicVariablesObject.processData.get(x));
                        }
                    }
                }
//5- order processes add to workprocess acording to burst time only
                ordering(1, workProcess);
//6- remove 1 from burst time of first process in workProsses
                timeLine = removeOneBurst(timeLine);

//7- check if this process was also past process,if -->then mix 2 buttons together
                if (lastProcess != workProcess.get(0).get(2)) {
                    Button H = publicVariablesObject.makeButton(workProcess.get(0).get(2), 1);
                    past = H;
                    lastProcess = workProcess.get(0).get(2);
                    width = 1;
                    gantchart.getChildren().add(H);
                } else {
                    width += 1;
                    gantchart.getChildren().remove(past);
                    Button H = publicVariablesObject.makeButton(lastProcess, width);
                    past = H;
                    gantchart.getChildren().add(H);
                }

                System.out.println(workProcess);
//8- delete process has zero burst time
                zeroBurstRemover();
            }
//9- continue all processes as non-permative as all processes get ordered according to bursts
            int wpSize = workProcess.size();
            for (int x = 0; x < wpSize; x++) {

                formButton(x);

                int m = workProcess.get(x).get(1) - workProcess.get(x).get(1);
                workProcess.get(x).set(1, m);
                System.out.println(workProcess);

            }

        }
    }

    public void zeroBurstRemover(){
        int size = workProcess.size();
        for(int x=0;x<size;x++)
        {
            try {
                if (workProcess.get(x).get(1) == 0)
                {
                    workProcess.remove(x);
                }
                }catch (Exception e) {
                    continue;
            }
        }
    }

    public int removeOneBurst(int timeLine){
        int m = workProcess.get(0).get(1) - 1 ;
        workProcess.get(0).set(1, m);
        timeLine +=1;
        return timeLine;
    }

    public void ordering(int startORburst,ArrayList<ArrayList<Integer>> wp){
        int s = wp.size();
        for (int i = 0; i < s; i++) {
            for (int j = 1; j < (s - i); j++) {
                if (wp.get(j - 1).get(startORburst) > wp.get(j).get(startORburst)) {
                    ArrayList<ArrayList<Integer>> l = new ArrayList();
                    l.clear();
                    l.add(wp.get(j - 1));
                    wp.remove(j - 1);

                    try {
                        wp.add(j, l.get(0));
                    } catch (Exception e) {
                        wp.set(j, l.get(0));
                    }
                }
            }
        }
    }

    public void formButton(int x) {
        if(lastProcess != workProcess.get(x).get(2))  //if last process not equal this process
        {
            Button H = publicVariablesObject.makeButton(workProcess.get(x).get(2), workProcess.get(x).get(1));
            past = H;
            lastProcess = workProcess.get(x).get(2);
            width = workProcess.get(x).get(1);
            gantchart.getChildren().add(H);
        }
        else
        {
            width += workProcess.get(x).get(1);
            gantchart.getChildren().remove(past);
            Button H = publicVariablesObject.makeButton(lastProcess,width);
            past = H;
            gantchart.getChildren().add(H);
        }
    }
}

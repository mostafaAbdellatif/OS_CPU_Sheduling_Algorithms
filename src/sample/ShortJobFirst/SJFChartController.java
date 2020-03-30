package sample.ShortJobFirst;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import sample.publicVariables;
import java.util.ArrayList;

public class SJFChartController {

    publicVariables publicVariablesObject = new publicVariables();

    @FXML
    private HBox gantchart;
    @FXML
    private Label averageTime;

    public void initialize() {

// clear previous data from prossessData
        publicVariablesObject.processData.clear();

// combine all process data together [start,burst,index]
        publicVariablesObject.makeCombination();

        /////////////////////Non-Permative//////////////////////////
        if (publicVariablesObject.permative == Boolean.FALSE) {
//1- order all processes according to arrival time
// (if 2 have same arrival time then order accordding to shortest burst)
            publicVariablesObject.orderingStartThenBurst();

            System.out.println(publicVariablesObject.processData);

            for (int x = 0; x < publicVariablesObject.processData.size(); x++) {

//2- make button for each process
                Button H = publicVariablesObject.makeButton(publicVariablesObject.processData.get(x).get(2), publicVariablesObject.processData.get(x).get(1));
                gantchart.getChildren().add(H);

//3- subtract burst-burst to make burst time of each process equal zero
                int m = publicVariablesObject.processData.get(x).get(1) - publicVariablesObject.processData.get(x).get(1);
                publicVariablesObject.processData.get(x).set(1, m);
            }
        }

        //////////////////////Permative//////////////////////////////
        else if (publicVariablesObject.permative == Boolean.TRUE) {
//1- order all processes according to arrival time
// (if 2 have same arrival time then order accordding to shortest burst)
            publicVariablesObject.orderingStartThenBurst();

            System.out.println(publicVariablesObject.processData);

//2- make arraylist to store difference between arrival times (x - (x-1))
            ArrayList<Integer> diff = new ArrayList();

            for (int x = 0; x < publicVariablesObject.starts.size() - 1; x++) {
                int m = publicVariablesObject.starts.get(x + 1) - publicVariablesObject.starts.get(x);
                diff.add(m);
            }

            for (int x = 0; x < diff.size(); x++) {

//3- subtract process's burst time - first diff, so you can stop process and take second process
                int m1 = publicVariablesObject.processData.get(0).get(1) - diff.get(x);
//4- make button for process
                if (publicVariablesObject.processData.get(0).get(1) > 0) {
                    Button H = publicVariablesObject.makeButton(publicVariablesObject.processData.get(0).get(2), diff.get(x));
                    gantchart.getChildren().add(H);
                }

//5- set new burst time
                publicVariablesObject.processData.get(0).set(1, m1);

//6- order again according to first process and add one only (not all processes)
//ordering( according to burst , number of elements you need to compare)
                publicVariablesObject.ordering(publicVariablesObject.burst, (2 + x));

                System.out.println(publicVariablesObject.processData);
            }

//7- all processes are added so no more arrival times
//8- make buttons as non-permative according to shortest burst
            for (int x = 0; x < publicVariablesObject.processData.size(); x++) {
                Button H = publicVariablesObject.makeButton(publicVariablesObject.processData.get(x).get(2), publicVariablesObject.processData.get(x).get(1));
                gantchart.getChildren().add(H);

                int m = publicVariablesObject.processData.get(x).get(1) - publicVariablesObject.processData.get(x).get(1);
                publicVariablesObject.processData.get(x).set(1, m);
            }

            System.out.println(publicVariablesObject.processData);

        }

    }

}

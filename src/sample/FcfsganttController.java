package sample;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.Collections;


public class FcfsganttController {


    public static ArrayList<Float> starts = new ArrayList(100);
    public static ArrayList<Float> bursts = new ArrayList(100);
    public static ArrayList<Integer> index = new ArrayList(100);
    @FXML
    private HBox gantchart;
    @FXML
    private Label avgWaitingTime;

    public void initialize() {
        System.out.println(starts);
        System.out.println(bursts);
        float temp = 0;
        int g = 0;
        int s = starts.size();
        float avgWaitTime = 0;
        // intializeIndexArray(index);
        for (int i = 0; i < s; i++) {
            index.add(i, i);
        }
        System.out.println(index);
        // i will make bubble sort to sort the starts and burst with each other;
        for (int i = 0; i < s; i++) {
            for (int j = 1; j < (s - i); j++) {
                if (starts.get(j - 1) > starts.get(j)) {
                    //swap elements
                    temp = starts.get(j - 1);
                    starts.set(j - 1, starts.get(j));
                    starts.set(j, temp);
                    temp = bursts.get(j - 1);
                    bursts.set(j - 1, bursts.get(j));
                    bursts.set(j, temp);
                    g = index.get(j - 1);
                    index.set(j - 1, index.get(j));
                    index.set(j, g);
                }
            }
        }
        float timeline = starts.get(0);
        //gantt drawing;
        for (int i = 0; i < starts.size(); i++) {
            Button H = new Button();
            //timeline variable to have a timeline dependent on all processes;
            H.setText("Process " + (index.get(i)+1) + "\n" + timeline + "\t   " + (bursts.get(i)+timeline));
            avgWaitTime += timeline - starts.get(i);
            timeline+=bursts.get(i);
            H.setAlignment(Pos.CENTER);
            H.setMinHeight(100);
            H.setMinWidth((bursts.get(i))*50);
            gantchart.getChildren().add(H);
        }
        avgWaitingTime.setText(String.valueOf(avgWaitTime/starts.size()));
        System.out.println(starts);
        System.out.println(bursts);
        System.out.println((avgWaitTime/starts.size()));


    }

}
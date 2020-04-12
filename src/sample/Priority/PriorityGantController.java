package sample.Priority;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import sample.publicVariables;
import sample.publicVariables;

public class PriorityGantController {
    publicVariables publicVariablesObject = new publicVariables();

    @FXML
    private HBox gantchart;
    @FXML
    private Label avgWaitingTime;

    public void initialize() {
        // clear previous data from prossessData
        publicVariablesObject.processData.clear();

// combine all process data together [start,burst,index,priority]
        publicVariablesObject.makeCombination();
        //order data according to arrival time
        //System.out.println(publicVariablesObject.processData.get(0));
        //System.out.println(publicVariablesObject.processData.get(1));
        //System.out.println(publicVariablesObject.processData.get(2));
        publicVariablesObject.ordering(0);
        int size = publicVariablesObject.processData.size();
        int numberOfProcess = size;
        //will be float
        int timeline = publicVariablesObject.processData.get(0).get(0);
        int index = 0;
        float avgWaitTime = 0;

                               //non  permative//

        if (!publicVariablesObject.permative) {
            while (size != 0) {
                index = publicVariablesObject.findTimelineIndex(timeline, size);
                publicVariablesObject.priorityOrderTimeline(index, size);
                Button H = new Button();
                //timeline variable to have a timeline dependent on all processes;
                if(timeline >= publicVariablesObject.processData.get(0).get(0)) {
                    H.setText("Process " + publicVariablesObject.processData.get(0).get(2) + "\n" + timeline + "\t   " + ((publicVariablesObject.processData.get(0).get(1)) + timeline));
                    avgWaitTime += timeline - publicVariablesObject.processData.get(0).get(0);
                    timeline += publicVariablesObject.processData.get(0).get(1);
                }
                else{
                    Button idle = idleButton(publicVariablesObject.processData.get(0).get(0)-timeline);
                    gantchart.getChildren().add(idle);
                    H.setText("Process " + publicVariablesObject.processData.get(0).get(2) + "\n" + publicVariablesObject.processData.get(0).get(0) + "\t   " + ((publicVariablesObject.processData.get(0).get(1)) + publicVariablesObject.processData.get(0).get(0)));
                    //avgWaitTime += timeline - publicVariablesObject.processData.get(0).get(0);
                    timeline = publicVariablesObject.processData.get(0).get(0)+publicVariablesObject.processData.get(0).get(1);
                }
                //timeline += publicVariablesObject.processData.get(0).get(1);
                H.setAlignment(Pos.CENTER);
                H.setMinHeight(100);
                H.setMinWidth((publicVariablesObject.processData.get(0).get(1)) * 50);
                gantchart.getChildren().add(H);
                publicVariablesObject.processData.remove(0);
                size--;
            }
            avgWaitingTime.setText(String.valueOf(avgWaitTime/numberOfProcess));
            System.out.println(avgWaitTime/numberOfProcess);
        }
                                        //premative//


        else {
            int index_on_cpu = 0;
            int time_on_cpu = 0;
            int previous_on_cpu = 0;
            index = publicVariablesObject.findTimelineIndex(timeline, size);
            publicVariablesObject.priorityOrderTimeline(index, size);
            index_on_cpu = publicVariablesObject.processData.get(0).get(2);
            previous_on_cpu = publicVariablesObject.processData.get(0).get(2);
            while (size != 0) {
                /*
                System.out.println("prev on cpu" + previous_on_cpu);
                System.out.println("index on cpu" + index_on_cpu);
                System.out.println("timeline" + timeline);
                System.out.println("time on cpu" + time_on_cpu);
                System.out.println("begin " + publicVariablesObject.processData);
                 */
                int check = 0;
                check = findIndex(previous_on_cpu, size);
                if (previous_on_cpu == index_on_cpu || check == -1) {
                    timeline = timeline + 1;
                    //added to make sure the process dont enter cpu unless its time for it
                    if(timeline<=publicVariablesObject.processData.get(0).get(0)) {


                        Button idle = idleButton(publicVariablesObject.processData.get(0).get(0) - (timeline-1));
                        gantchart.getChildren().add(idle);
                        while (timeline <= publicVariablesObject.processData.get(0).get(0)) {
                            timeline++;
                        }

                    }
                    time_on_cpu = time_on_cpu + 1;
                    if (time_on_cpu == publicVariablesObject.processData.get(0).get(1)) { //remove process it finished
                        Button H = new Button();
                        H.setText("Process " + publicVariablesObject.processData.get(0).get(2) + "\n" + (timeline - time_on_cpu) + "\t   " + (timeline));
                        H.setAlignment(Pos.CENTER);
                        H.setMinHeight(100);
                        H.setMinWidth((publicVariablesObject.processData.get(0).get(1)) * 100);
                        avgWaitTime += timeline-time_on_cpu-(publicVariablesObject.processData.get(0).get(0));
                        gantchart.getChildren().add(H);
                        publicVariablesObject.processData.remove(0);
                        time_on_cpu = 0;
                        size--;
                    }
                } else {
                    int previous_on_cpu_index = findIndex(previous_on_cpu, size);
                    int burst = publicVariablesObject.processData.get(previous_on_cpu_index).get(1) - time_on_cpu;
                    if (burst == 0) {
                        Button H = new Button();
                        H.setText("Process " + publicVariablesObject.processData.get(previous_on_cpu_index).get(2) + "\n" + (timeline - time_on_cpu) + "\t   " + timeline);
                        H.setAlignment(Pos.CENTER);
                        H.setMinHeight(100);
                        //H.setMinWidth((publicVariablesObject.processData.get(previous_on_cpu_index).get(1)) * 50);
                        H.setMinWidth(time_on_cpu * 100);
                        gantchart.getChildren().add(H);
                        avgWaitTime += timeline-time_on_cpu-(publicVariablesObject.processData.get(previous_on_cpu_index).get(0));
                        publicVariablesObject.processData.remove(previous_on_cpu_index);
                        time_on_cpu = 0;
                        size--;

                    } else {
                        publicVariablesObject.processData.get(previous_on_cpu_index).set(1, burst);
                        Button H = new Button();
                        H.setText("Process " + publicVariablesObject.processData.get(previous_on_cpu_index).get(2) + "\n" + (timeline - time_on_cpu) + "\t   " + timeline);
                        H.setAlignment(Pos.CENTER);
                        H.setMinHeight(100);
                        //H.setMinWidth((publicVariablesObject.processData.get(previous_on_cpu_index).get(1)) * 50);
                        H.setMinWidth(time_on_cpu * 100);
                        avgWaitTime += timeline-time_on_cpu-(publicVariablesObject.processData.get(previous_on_cpu_index).get(0));
                        gantchart.getChildren().add(H);
                        time_on_cpu = 0;

                    }

                }
                if (size == 0) {
                    avgWaitingTime.setText(String.valueOf(avgWaitTime/numberOfProcess));
                    break;
                }
                previous_on_cpu = index_on_cpu;//they are names not indices so we dont know their locations
                index = publicVariablesObject.findTimelineIndex(timeline, size);
                publicVariablesObject.priorityOrderTimeline(index, size);
                index_on_cpu = publicVariablesObject.processData.get(0).get(2);

            }
        }
    }

    //3alshan a3raf aqaren b asamy el process w a3mel track lel index bta3hom lw ra7o f ay 7eta
    public int findIndex(int nameProcess, int siz) {
        for (int i = 0; i < siz; i++) {
            if (publicVariablesObject.processData.get(i).get(2) == nameProcess) {
                return i;
            }
        }
        return -1;
    }

    public Button idleButton(int width) {
        Button H = new Button();
        //timeline variable to have a timeline dependent on all processes;
        H.setText("idle" + "\n" +"  "+ String.valueOf(width) );
        H.setAlignment(Pos.CENTER);
        H.setMinHeight(100);
        H.setMinWidth(width * 50);

        return H;
    }


}






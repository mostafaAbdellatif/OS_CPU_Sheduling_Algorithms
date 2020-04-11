package sample.RoundRobin;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import sample.publicVariables;

import java.util.ArrayList;

public class RRChartController2 {

    publicVariables pbvo = new publicVariables();
    static ArrayList<ArrayList<Integer>> workProcess = new ArrayList(20);

    static Button past;
    static int lastProcess;
    static int width;

    @FXML
    private HBox gantchart;
    @FXML
    private Label averageTime;

    public void initialize() {

        pbvo.processData.clear();

        pbvo.makeCombination();  //add start, burst,process name together

        pbvo.ordering(0); //order according to start time

        int totalTime = 0;
        for (int x = 0; x < pbvo.processData.size(); x++) {
            pbvo.processData.get(x).add(0);             //add new element in process data about avgTime(arrival - beginTime)
            totalTime += pbvo.processData.get(x).get(1);
        }

        int timeLine = pbvo.processData.get(0).get(0);


        for(int m=0;m<1000;m++) {
            try {
                addProcesses(timeLine);

                if( !workProcess.isEmpty() )
                {
                    setAvg(timeLine);

                    int diff = workProcess.get(0).get(1) - pbvo.quantum;
                    int width;
                    if (diff >= 0) {
                        width = pbvo.quantum;
                    } else {
                        width = workProcess.get(0).get(1);
                    }

                    //formButton(width, 0);
                    formButtonTime(width,0,timeLine,timeLine+width);

                    timeLine += width;

                    workProcess.get(0).set(1, workProcess.get(0).get(1) - width); // set new burst time
                    workProcess.get(0).set(0, timeLine);  //set new timeline as arrival time

                    putProcessAtEnd();  //put the latest process u worked on at end

                    workProcess.clear();
                }
                else
                {
                    timeLine += 1;
                    makeIdeal();
                }

            }catch (Exception e)
            {
                continue;
            }
        }


        printAvg();

    }

    public void addProcesses(int timeLine)
    {
        for(int x=0;x<pbvo.processData.size();x++)
        {
            if(pbvo.processData.get(x).get(0)<=timeLine) {
                if (pbvo.processData.get(x).get(1) != 0) {
                    workProcess.add(pbvo.processData.get(x));
                }
            }
        }
    }

    public void setAvg(int timeLine)
    {
        int avg = timeLine - workProcess.get(0).get(0);
        int a = pbvo.processData.get(0).get(3) + avg;
        pbvo.processData.get(0).set(3,a);
    }

    public void formButtonTime(int width,int index,int timeline,int finish)
    {
        Button H = makeButton(workProcess.get(index).get(2), width,timeline,finish);
        gantchart.getChildren().add(H);
    }

    public Button makeButton(int index,int width,int timeline,int finish )
    {
        Button H = new Button();
        //timeline variable to have a timeline dependent on all processes;
        H.setText( "p" + index + "\n\n" + timeline +"\t"+ finish );
        H.setAlignment(Pos.CENTER);
        H.setMinHeight(100);
        H.setMinWidth(width * 50);

        return H;
    }

    public void printAvg()
    {
        float totalAvg = 0;
        for(int x=0;x<pbvo.processData.size();x++)
        {
            totalAvg += pbvo.processData.get(x).get(3);
        }

        averageTime.setText(String.valueOf(totalAvg/pbvo.processData.size()));
    }

    public void putProcessAtEnd()
    {
        for(int x=0;x<pbvo.processData.size();x++)
        {
            if(pbvo.processData.get(x).get(2) == workProcess.get(0).get(2))
            {
                pbvo.processData.remove(x);
                pbvo.processData.add(workProcess.get(0));
            }
        }
    }

    public void formIdealButton(int width)
    {
        if( lastProcess != 8080)
        {
            this.width = width;
            Button H = makeButton("Ideal", this.width);
            gantchart.getChildren().add(H);
            past = H;
            lastProcess = 8080;
        }else{
            this.width+=width;
            gantchart.getChildren().remove(past);
            Button H = makeButton("Ideal", this.width);
            gantchart.getChildren().add(H);
            past = H;
        }
    }

    public Button makeButton(String s,int width)
    {
        Button H = new Button();
        //timeline variable to have a timeline dependent on all processes;
        H.setText( s + "\n\n" +"\t"+ String.valueOf(width) );
        H.setAlignment(Pos.CENTER);
        H.setMinHeight(100);
        H.setMinWidth(width * 50);

        return H;
    }

    public void makeIdeal()
    {
        boolean bo = Boolean.FALSE;

        for(int y=0;y<pbvo.processData.size();y++)
        {
            if (pbvo.processData.get(y).get(1)>0)
            {
                bo = Boolean.TRUE;
            }
        }

        if (bo == Boolean.TRUE)
        {
            formIdealButton(1);
        }
    }

}

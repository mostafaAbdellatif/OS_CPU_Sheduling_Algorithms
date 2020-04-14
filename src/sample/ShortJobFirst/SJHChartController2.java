package sample.ShortJobFirst;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import sample.publicVariables;

import java.util.ArrayList;

public class SJHChartController2 {


    publicVariables pbvo = new publicVariables();

    static ArrayList<ArrayList<Integer>> workProcess = new ArrayList(20);

    Button past;
    int lastProcess;
    int width;
    int oldTimeLine;

    @FXML
    private HBox gantchart;
    @FXML
    private Label averageTime;

    public void initialize() {

        pbvo.processData.clear();
        workProcess.clear();

        pbvo.makeCombination();

        int totalTime = 0;
        for (int x = 0; x < pbvo.processData.size(); x++) {
            pbvo.processData.get(x).add(0);
            totalTime += pbvo.processData.get(x).get(1);
        }

        if (pbvo.permative == Boolean.FALSE) {

            //non-permative

            pbvo.orderingStartThenBurst();

            int timeLine = pbvo.processData.get(0).get(0);

            int size = pbvo.processData.size();

            for (int x = 0; x < 1000; x++) {
                addProcesses(timeLine);

                if( !workProcess.isEmpty() )
                {
                    ordering(1, workProcess, workProcess.size());

                    setAvg(timeLine);     //make calculations of average time

                    formButtonTime(workProcess.get(0).get(1),timeLine,timeLine+workProcess.get(0).get(1));

                    timeLine += workProcess.get(0).get(1);

                    workProcess.get(0).set(1, 0);  //make burst  = 0
                    workProcess.get(0).set(0, timeLine);

                    workProcess.clear();
                }
                else
                {
                    timeLine += 1;
                    makeIdeal();
                }

            }

            printAvg();

        }else {

            //permative

            pbvo.orderingStartThenBurst();

            int timeLine = pbvo.processData.get(0).get(0);

            for (int x = 0; x < 1000; x++) {
                try{
                addProcesses(timeLine);

                if( !workProcess.isEmpty() )
                {
                    ordering(1, workProcess, workProcess.size());

                    setAvg(timeLine);

                    formButtonTime(1,timeLine,timeLine+1);

                    timeLine += 1;

                    workProcess.get(0).set(1, workProcess.get(0).get(1) - 1);
                    workProcess.get(0).set(0, timeLine);

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

            printAvg();

        }
            }
}


    public void ordering(int startORburst,ArrayList<ArrayList<Integer>> wp,int size)
    {
        int s = size;
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

    public void addProcesses(int timeLine)
    {
        int size = pbvo.processData.size();

        for (int x = 0; x < size; x++) {
            if (pbvo.processData.get(x).get(0) <= timeLine)
            {
                if(pbvo.processData.get(x).get(1) != 0)
                {
                    workProcess.add(pbvo.processData.get(x));
                }
            }
        }
    }

    public void setAvg(int timeLine)
    {
        int avg = timeLine - workProcess.get(0).get(0);
        for(int y=0;y<pbvo.processData.size();y++)
        {
            if(workProcess.get(0).get(2) == pbvo.processData.get(y).get(2))
            {
                int a = pbvo.processData.get(y).get(3) + avg;
                pbvo.processData.get(y).set(3,a);
            }
        }
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

    public void formButtonTime(int width,int timeline,int finish)
    {
        if( lastProcess != workProcess.get(0).get(2))
        {
            this.width = width;
            Button H = makeButton(workProcess.get(0).get(2),this.width,timeline,finish);
            gantchart.getChildren().add(H);
            past = H;
            oldTimeLine = timeline;
            lastProcess = workProcess.get(0).get(2);
        }else{
            this.width+=width;
            gantchart.getChildren().remove(past);
            Button H = makeButton(workProcess.get(0).get(2),this.width,oldTimeLine,finish);
            gantchart.getChildren().add(H);
            past = H;
        }
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
}

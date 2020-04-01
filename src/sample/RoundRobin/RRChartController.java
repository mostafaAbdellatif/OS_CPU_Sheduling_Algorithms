package sample.RoundRobin;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import sample.publicVariables;

public class RRChartController {

    publicVariables publicVariablesObject = new publicVariables();

    static Button past;
    static int lastProcess;
    static int width;

    @FXML
    private HBox gantchart;
    @FXML
    private Label averageTime;

    public void initialize() {

        publicVariablesObject.processData.clear();

        publicVariablesObject.makeCombination();  //add start, burst,process name together

        System.out.println(publicVariablesObject.processData);

        publicVariablesObject.ordering(publicVariablesObject.start); //order according to start time

        System.out.println(publicVariablesObject.processData);

        System.out.println(publicVariablesObject.processData);

        while (!publicVariablesObject.processData.isEmpty()) {
            int s=publicVariablesObject.processData.size();
            for (int x = 0; x < s; x++)
            {
                int m = publicVariablesObject.processData.get(x).get(1) - publicVariablesObject.quantum;
                if(publicVariablesObject.processData.get(x).get(1)>0)
                {
                    if(m>=0){
                        formButton(x,publicVariablesObject.quantum);
                    }
                    else
                    {
                        formButton(x,publicVariablesObject.processData.get(x).get(1));
                    }

                }
                publicVariablesObject.processData.get(x).set(1, m);
             //  if (m <= 0)
             //  {
                    //  m = 0;
               //     publicVariablesObject.processData.remove(x);
              // }

                System.out.println(publicVariablesObject.processData);
            }

            for (int x = 0; x < s; x++) {
                try{
                if (publicVariablesObject.processData.get(x).get(1) <= 0) {
                    publicVariablesObject.processData.remove(x);
                }
                }catch (Exception e){
                    continue;
                }
            }

        }

    }

    public void formButton(int x,int wid) {
        if(lastProcess != publicVariablesObject.processData.get(x).get(2))  //if last process not equal this process
        {
            Button H = publicVariablesObject.makeButton(publicVariablesObject.processData.get(x).get(2), wid );
            past = H;
            lastProcess = publicVariablesObject.processData.get(x).get(2);
            width = wid;
            gantchart.getChildren().add(H);
        }
        else
        {
            width += wid;
            gantchart.getChildren().remove(past);
            Button H = publicVariablesObject.makeButton(lastProcess,width);
            past = H;
            gantchart.getChildren().add(H);
        }
    }
}
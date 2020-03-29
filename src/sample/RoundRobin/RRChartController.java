package sample.RoundRobin;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import sample.publicVariables;

public class RRChartController {

    publicVariables publicVariablesObject = new publicVariables();

    @FXML
    private HBox gantchart;
    @FXML
    private Label averageTime;

    public void initialize() {

        publicVariables.processData.clear();

        publicVariablesObject.makeCombination();

        System.out.println(publicVariablesObject.processData);

        publicVariablesObject.ordering();

        System.out.println(publicVariablesObject.processData);

        publicVariablesObject.indexingProcess();

        System.out.println(publicVariablesObject.processData);


        while (!publicVariablesObject.processData.isEmpty()) {
            int s=publicVariablesObject.processData.size();
            for (int x = 0; x < s; x++)
            {
                int m = publicVariablesObject.processData.get(x).get(1) - publicVariablesObject.quantum;
                if(publicVariablesObject.processData.get(x).get(1)>0)
                {
                    Button H = publicVariablesObject.makeButton(publicVariablesObject.processData.get(x).get(2), publicVariablesObject.quantum);
                    gantchart.getChildren().add(H);
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
}
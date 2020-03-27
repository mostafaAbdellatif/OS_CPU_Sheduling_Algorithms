package sample;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class FcfswinController {
    Main helloObject = new Main();
    @FXML
    private TextField input;
    @FXML
    private VBox processId;
    @FXML
    private VBox startTime;
    @FXML
    private VBox burstTime;
    @FXML
    private Button enterOk;
    //@FXML
    //private HBox gantchart;
    public ArrayList<TextField> starts = new ArrayList(100);
    public ArrayList<TextField> bursts = new ArrayList(100);
    public void okEntred(ActionEvent event) throws Exception {

        processId.getChildren().clear();
        startTime.getChildren().clear();
        burstTime.getChildren().clear();


        if (!input.getText().isEmpty()) {


            int inputNumber = Integer.parseInt(input.getText());


            for (int x = 1; x <= inputNumber; x++) {
                Label y = new Label("Process" + x);
                y.setFont(new Font("Regular", 18));
                y.setAlignment(Pos.CENTER);
                y.setTextAlignment(TextAlignment.CENTER);
                y.setContentDisplay(ContentDisplay.CENTER);
                processId.getChildren().addAll(y);
                processId.setFillWidth(true);
                TextField start = new TextField();
                start.setFont(new Font("Regular", 12));
                start.setPromptText("Process "+ x + " Start");
                start.setAlignment(Pos.CENTER);
                start.setMinWidth(100);
                start.setMinHeight(27);
                starts.add(x-1,start);
                startTime.getChildren().addAll(start);
                startTime.setFillWidth(true);

                TextField burst = new TextField();
                burst.setFont(new Font("Regular", 12));
                burst.setPromptText("Process "+ x + " Burst");
                burst.setAlignment(Pos.CENTER);
                burst.setMinWidth(100);
                burst.setMinHeight(27);
                bursts.add(x-1,burst);
                burstTime.getChildren().addAll(burst);
                burstTime.setFillWidth(true);
            }
        }
    }
   public FcfsganttController gantController = new FcfsganttController();
    public void endOk (ActionEvent event) throws Exception {
            boolean x = true;
            boolean y = true;
            //to delete all avalues in array and add the new written
            FcfsganttController.starts.clear();
            FcfsganttController.bursts.clear();
            for (int i = 0; i < Integer.parseInt(input.getText()); i++) {

                if (!starts.get(i).getText().isEmpty()) {
                    System.out.println(starts.get(i).getText());
                    float f = Float.parseFloat(starts.get(i).getText());
                    FcfsganttController.starts.add(f);
                } else {
                    starts.get(i).setPromptText("Fill all");
                    x = false;
                }
            }
        for (int i = 0; i < Integer.parseInt(input.getText()); i++) {

            if (!bursts.get(i).getText().isEmpty()) {
                System.out.println(bursts.get(i).getText());
                float k = Float.parseFloat(bursts.get(i).getText());
                FcfsganttController.bursts.add(k);
            } else {
                bursts.get(i).setPromptText("Fill all");
                y = false;
            }
        }
     //we have to come out with true x and y to make sure that all fields are filled;
        if((x && y) == true){
            try {
                FXMLLoader loader=new FXMLLoader(getClass().getResource("fcfsganttwindow.fxml"));
                Parent root = (Parent) loader.load();

                //FcfsganttController gnty = loader.getController();
                Stage stage=new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("GanttChart");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
             //if we want it on same stage
            /*Parent root = FXMLLoader.load(getClass().getResource("fcfsgantt.fxml"));
            helloObject.getStage().setTitle("FSFC");
            helloObject.getStage().setScene(new Scene(root));
        */
        }



    }


}

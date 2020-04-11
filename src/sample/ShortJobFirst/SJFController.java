package sample.ShortJobFirst;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import sample.Main;
import sample.publicVariables;

import java.io.IOException;
import java.util.ArrayList;

public class SJFController {

    publicVariables publicVariablesObject = new publicVariables();
    Main helloObject = new Main();

    @FXML
    private TextField noProcesses;
    @FXML
    private Button okButton;
    @FXML
    private Button beginButton;
    @FXML
    private VBox processId;
    @FXML
    private VBox startTime;
    @FXML
    private VBox burstTime;
    @FXML
    private CheckBox permativeBox;
    @FXML
    private Button BackToHome;


    public ArrayList<TextField> starts = new ArrayList(100);
    public ArrayList<TextField> bursts = new ArrayList(100);


    public void okEntred(ActionEvent event) throws Exception {

        processId.getChildren().clear();
        startTime.getChildren().clear();
        burstTime.getChildren().clear();

        if (!noProcesses.getText().isEmpty()) {

            int inputNumber = Integer.parseInt(noProcesses.getText());

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

    public void beginEntered (ActionEvent event) throws Exception {
        boolean x = true;
        boolean y = true;
        //to delete all avalues in array and add the new written
        publicVariablesObject.starts.clear();
        publicVariablesObject.bursts.clear();

        for (int i = 0; i < Integer.parseInt(noProcesses.getText()); i++) {

            if (!starts.get(i).getText().isEmpty()) {
                int f = Integer.parseInt(starts.get(i).getText());
                publicVariablesObject.starts.add(f);
            } else {
                starts.get(i).setPromptText("Fill all");
                x = false;
            }
        }
        for (int i = 0; i < Integer.parseInt(noProcesses.getText()); i++) {

            if (!bursts.get(i).getText().isEmpty()) {
                int k = Integer.parseInt(bursts.get(i).getText());
                publicVariablesObject.bursts.add(k);
            } else {
                bursts.get(i).setPromptText("Fill all");
                y = false;
            }
        }
        //we have to come out with true x and y to make sure that all fields are filled;
        if((x && y) == true){
            try {
                FXMLLoader loader=new FXMLLoader(getClass().getResource("SJFChart.fxml"));
                Parent root = (Parent) loader.load();
                Stage stage=new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("GanttChart");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void handlePermativeBox(){
        publicVariablesObject.permative = Boolean.FALSE;
        if(permativeBox.isSelected()){
            publicVariablesObject.permative = Boolean.TRUE;
        }
    }


    @FXML
    public void homeEntered(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../start.fxml"));
        helloObject.getStage().setTitle("CPU Scheduler");
        helloObject.getStage().setScene(new Scene(root, 980, 512));
    }


}

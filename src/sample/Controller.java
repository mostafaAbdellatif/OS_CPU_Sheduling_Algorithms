package sample;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class Controller {

    Main helloObject = new Main();


    @FXML
    private Button Fcfs;
    @FXML
    private VBox processId;
    @FXML
    private Button roundRobinButton;


    @FXML
    public void pressFCFSButton(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("fcfswin.fxml"));
        helloObject.getStage().setTitle("FSFC");
        helloObject.getStage().setScene(new Scene(root));

    }

    @FXML
    public void pressroundRobinButtonButton(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("RoundRobin/RRWin.fxml"));
        helloObject.getStage().setTitle("RoundRobin");
        helloObject.getStage().setScene(new Scene(root));

    }

}

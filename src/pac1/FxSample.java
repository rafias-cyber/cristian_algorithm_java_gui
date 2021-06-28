package pac1;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

import javafx.scene.text.Text;

import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

// Implementing the remote interface
public class FxSample extends Application {
    private TextArea window;
    private TextField input;

    public int getTime_server() {
        return time_server;
    }

    private int time_server=10000;
    private final boolean enough = false;
    private final SimpleDateFormat dt = new SimpleDateFormat("hh:mm:ss");
    private Text txtTime;
    private Server ser1;

    /**
     * Purpose of this method is to start whole program. First it create new server object.
     * Later this call method setStage with param stage.
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {

        this.ser1 = new Server(this);
        setStage(stage);

    }


    private TextArea getTextArea()
    {
        TextArea window = new TextArea();
        window.prefHeight(50.0);
        window.setStyle("-fx-text-fill: black; -fx-font-size: 12;");
        window.prefWidth(30.0);
        window.setEditable(false);

        return window;
    }
    private Text getText(){
        Text timeText = new Text();
        timeText.setStyle("-fx-text-fill: black; -fx-font-size: 25;");

        return timeText;
    }
    private BorderPane getRoot()
    {
        BorderPane root = new BorderPane();
        root.setBottom(this.input);
        root.setRight(this.window);
        root.setLeft(this.txtTime);
        return root;
    }

    /**
     * This method create stage and starts timer. Window is Text Area whose purpose is to display logs (connections etc.)
     * @param stage this is provided by javafx in start function
     */
    private void setStage(Stage stage)
    {
        this.window = this.getTextArea();
        this.txtTime = this.getText();
        Scene scene = new Scene(this.getRoot(), 600, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Server");
        startTimer();
        stage.show();
    }

    /**
     * This method starts a clock that provides time to all client.
     */
    private void startTimer(){
        Thread timer = new Thread(() -> {
            while (!enough) {
                time_server+=1000;
                try {
                    // running "long" operation not on UI thread
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    System.out.println("Timer exception: "
                            + ex.getMessage());
                }
                final String time = dt.format(time_server);
                Platform.runLater(() -> {
                    // updating live UI object requires JavaFX App Thread
                    txtTime.setText(time);
                });
            }
        });
        timer.start();
    }

    /**
     * This metod is adding new messeges to GUI
     * @param message - message that will be shown in GUI (logs)
     */
    public void setMessage(String message)
    {
        Platform.runLater(() -> FxSample.this.window.appendText("\n" + message));
    }

    /**
     * Main is seating system property, security manager and launch app.
     * System property must be set in order to communicate by rmi in Lan network.
     * idk why Security manager is necessary but without it it did not work.
     * @param args idk
     */
    public static void main(String[] args)
    {
        System.setProperty("java.rmi.server.hostname","localhost");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        Application.launch(args);

    }
}

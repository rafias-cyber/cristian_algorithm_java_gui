package pac1;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;

public class ClientGui extends Application {
    public int getTime_client() {
        return time_client;
    }

    public void setTime_client(int time_client) {
        this.time_client = time_client;
    }

    private int time_client=0;
    private final boolean enough = false;
    private final SimpleDateFormat dt = new SimpleDateFormat("hh:mm:ss");
    private Text txtTime;
    private Client client;
    private Button b;
    private TextField txtMinDelay;
    private TextField txtMaxDelay;
    private Button b0;

    /**
     *
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.client = new Client(this);

        setStage(stage);
    }

    private BorderPane getRoot()
    {
        BorderPane root = new BorderPane();
        this.txtMinDelay = new TextField();
        this.txtMaxDelay = new TextField();
        this.txtMinDelay.setText("0");
        this.txtMaxDelay.setText("0");
        this.b0 = new Button();
        this.b0.setText("Set param");
        EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                test1();
            }
        };
        this.b0.setOnAction(event1);
        FlowPane tmp1 = new FlowPane();
        tmp1.getChildren().addAll(this.txtMinDelay, this.txtMaxDelay,this.b0);
        root.setLeft(tmp1);
        root.setCenter(this.txtTime);
        root.setBottom(this.b);
        return root;
    }

    /**
     * Some unknown error occurred so I set special method that only calls another method on client obj.
     * Really idk why simple call dont work.
     */
    private void test0(){
        this.client.callServer();
    }
    private void test1() {
        this.client.setDelay_max(Integer.parseInt(this.txtMaxDelay.getText()));
        this.client.setDelay_min(Integer.parseInt(this.txtMinDelay.getText()));
        System.out.println("Params set");
    }
    /**
     * Purpose of this method is to build a stage.
     * Method test0 is called when button is pressed.
     *
     * @param stage
     */
    private void setStage(Stage stage)
    {
        this.txtTime = new Text();
        this.txtTime.setStyle("-fx-text-fill: black; -fx-font-size: 25;");
        this.b = new Button("button");
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                test0();
            }
        };
        b.setOnAction(event);

        Scene scene = new Scene(this.getRoot(), 600, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Client");
        startTimer();
        stage.show();
    }

    /**
     * startTimer start timer on client rly
     */
    private void startTimer(){
        Thread timer = new Thread(() -> {
            while (!enough) {
                time_client+=1000;

                try {
                    // running "long" operation not on UI thread
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    System.out.println("Timer exception: "
                            + ex.getMessage());
                }
                final String time = dt.format(time_client);
                Platform.runLater(() -> {
                    // updating live UI object requires JavaFX App Thread
                    txtTime.setText(time);
                });
            }
        });
        timer.start();
    }
}

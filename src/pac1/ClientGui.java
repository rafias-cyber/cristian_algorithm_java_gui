package pac1;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
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
    private boolean enough = false;
    private SimpleDateFormat dt = new SimpleDateFormat("hh:mm:ss");
    private Text txtTime;
    private Client client;
    private Button b;
    @Override
    public void start(Stage stage) throws Exception {
        this.client = new Client(this);

        setStage(stage);
    }

    private BorderPane getRoot()
    {
        BorderPane root = new BorderPane();
        root.setCenter(this.txtTime);
        root.setBottom(this.b);
        return root;
    }
    private void test0(){
        this.client.callServer();
    }


    private void setStage(Stage stage)
    {
        //this.window = this.getTextArea();
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
    private void startTimer(){
        Thread timer = new Thread(() -> {
            while (!enough) {
                time_client+=1000;

                try {
                    // running "long" operation not on UI thread
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
                final String time = dt.format(time_client);
                Platform.runLater(() -> {
                    // updating live UI object requires JavaFX App Thread
                    txtTime.setText(time);
                    //testt();

                });
            }
        });
        timer.start();
    }
}

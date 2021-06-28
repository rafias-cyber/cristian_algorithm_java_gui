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

    public void setTime_server(int time_server) {
        this.time_server = time_server;
        this.timerIterator =1000;
    }

    private int time_server = 0; // Time on server
    private final boolean enough = false;
    private final SimpleDateFormat dt = new SimpleDateFormat("hh:mm:ss");
    private Text txtTime;
    private Client client;
    private Button b;

    private TextField txtMultMore;
    private TextField txtMultLess;
    public double tmp_holds_mult=1;
    private int timerIterator = 0 ;

    private Thread serverTimer;

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

        this.txtMultLess = new TextField();
        this.txtMultMore = new TextField();
        this.b0 = new Button();
        this.b0.setText("Set param");
        EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                test1();
            }
        };
        this.b0.setOnAction(event1);
        FlowPane tmp1 = new FlowPane();
        tmp1.getChildren().addAll(this.txtMultLess, this.txtMultMore,this.b0);
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
        this.client.setMultLess(Double.parseDouble(this.txtMultLess.getText()));
        this.client.setMultMore(Double.parseDouble(this.txtMultMore.getText()));
        if(tmp_holds_mult==this.client.getMultMore()){
            tmp_holds_mult = this.client.getMultMore();
        }
        else if (tmp_holds_mult==this.client.getMultLess()){
            tmp_holds_mult = this.client.getMultLess();
        }
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
        this.startServerTimer();
        stage.show();
    }

    /**
     * startTimer start timer on client rly
     */
    private void startTimer(){
        Thread timer = new Thread(() -> {
            while (!enough) {

                if(this.time_server==this.time_client){
                    this.tmp_holds_mult=1;
                    this.timerIterator =0;

                }
                System.out.println("Aktualny mnoznik: "+this.tmp_holds_mult);
                System.out.println("Czas klienta: "+this.time_client);
                System.out.println("Czas servera "+this.time_server);
                time_client+=1000;

                try {
                    // running "long" operation not on UI thread
                    Thread.sleep((long) (1000*tmp_holds_mult));
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

    public void startServerTimer(){
         this.serverTimer = new Thread(() -> {
            while (!enough) {

                time_server+=timerIterator;
                try {
                    // running "long" operation not on UI thread
                    Thread.sleep((long) (1000));
                } catch (InterruptedException ex) {
                    System.out.println("Timer exception: "
                            + ex.getMessage());
                }
            }
        });
        this.serverTimer.start();

    }
}

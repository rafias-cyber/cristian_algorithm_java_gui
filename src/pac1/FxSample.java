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
    private boolean enough = false;
    private SimpleDateFormat dt = new SimpleDateFormat("hh:mm:ss");
    private Text txtTime;
    public  TableView tab_viev;
    private List<Computer> listaKlientow = new ArrayList<Computer>();

    private Server ser1;
    @Override
    public void start(Stage stage) throws Exception {

        this.ser1 = new Server(this);
        setStage(stage);

    }
    /*
    private TextField getTextField()
    {
        TextField input = new TextField();
        input.setOnAction(e -> {
            client.sendMessage(input.getText());
            input.setText("");
        });
        return input;
    }
    */

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

        //root.setRight(this.tab_viev);
        root.setLeft(this.txtTime);
        return root;
    }
    private void setStage(Stage stage)
    {
        this.window = this.getTextArea();
        this.txtTime = this.getText();
        this.tab_viev = new TableView();
        prepTable();
        Scene scene = new Scene(this.getRoot(), 600, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Chat");
        startTimer();
        stage.show();
    }
    private void startTimer(){
        Thread timer = new Thread(() -> {
            while (!enough) {
                time_server+=1000;

                try {
                    // running "long" operation not on UI thread
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
                final String time = dt.format(time_server);
                Platform.runLater(() -> {
                    // updating live UI object requires JavaFX App Thread
                    txtTime.setText(time);
                    //testt();

                });
            }
        });
        timer.start();
    }

    public void prepTable(){
        TableColumn<Computer, Integer> column1 = new TableColumn<>("Numer");
        column1.setCellValueFactory(new PropertyValueFactory<>("numer_komp"));

        //this.tab_viev.getColumns().add(column4);


    }
    public void addClient(String adres_ip, Integer czas, Integer drift){
        /*Computer cmp = new Computer(adres_ip,czas,drift);
        if(!this.listaKlientow.contains(cmp)){
            this.listaKlientow.add(new Computer(adres_ip,czas,drift));
        }

        this.tab_viev.getItems().add();

         */
    }

    public void setMessage(String message)
    {
        Platform.runLater(() -> FxSample.this.window.appendText("\n" + message));
    }
    public static void main(String[] args)
    {
        System.setProperty("java.rmi.server.hostname","192.168.0.106");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        Application.launch(args);

    }
}
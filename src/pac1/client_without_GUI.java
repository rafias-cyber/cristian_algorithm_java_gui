package pac1;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class client_without_GUI {
    static Hello obj = null;
    private int time_client=0;
    private boolean enough = false;
    private SimpleDateFormat dt = new SimpleDateFormat("hh:mm:ss");
    private int delay_min =0;
    private int delay_max =0;
    public client_without_GUI() {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Podaj opoznienie w sekundach (min): ");
        this.delay_min = scanner.nextInt();
        System.out.print("Podaj opoznienie w sekundach (max): ");
        this.delay_max = scanner.nextInt();
        scanner.close();
        startTimer();
    }


    public void callServer(){
        try {
            Registry registry = LocateRegistry.getRegistry("192.168.0.106");
            System.out.println("info: Znaleziono rejestr");
            obj = (Hello) registry.lookup("Hello");
            System.out.println("info: Poczaczono z serwerem");
        } catch (Exception e) {
            System.out.println("Client exception1: "
                    + e.getMessage());
        }
        try {
            int res = obj.getTime();
            Random random = new Random();
            TimeUnit.SECONDS.sleep(random.nextInt(delay_max - delay_min) + delay_min);
            System.out.println("info: otrzymano: " +dt.format(res));
            this.time_client = (this.time_client+(int)res)/2;

        } catch (RemoteException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * startTimer start timer on client rly.
     * After 10s method on server is called.
     */
    private void startTimer(){
        Thread timer = new Thread(() -> {
            while (!enough) {
                time_client+=1000;
                if((time_client/1000)%10==0){
                    this.callServer();
                }
                try {
                    // running "long" operation not on UI thread
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
                final String time = dt.format(time_client);
                System.out.println("Aktualny czas: "+time);
            }
        });
        timer.start();
    }
    public static void main(String[] args)
    {
        client_without_GUI cl = new client_without_GUI();
    }
}
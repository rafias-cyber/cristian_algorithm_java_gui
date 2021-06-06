package pac1;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Client {
    static Hello obj = null;
    private final ClientGui gui;

    public void setDelay_min(int delay_min) {
        this.delay_min = delay_min;
    }

    public void setDelay_max(int delay_max) {
        this.delay_max = delay_max;
    }

    private int delay_min = 0;
    private int delay_max = 0;
    private int t0;
    private int t1;
    private SimpleDateFormat dt = new SimpleDateFormat("hh:mm:ss");
    Client(ClientGui cGui) {
        this.gui = cGui;
    }

    /**
     * This calls rmi method on server. Method searches for the register in Lan network and calls getTime method on the server.
     * Server return (by network) actual time.
     */
    public void callServer(){
        try {
            Registry registry = LocateRegistry.getRegistry("localhost");
            System.out.println("info: Znaleziono rejestr");
            obj = (Hello) registry.lookup("Hello");
            System.out.println("info: Poczaczono z serwerem");
        } catch (Exception e) {
            System.out.println("Client exception1: "
                    + e.getMessage());
        }

        try {
            Random random = new Random();
            this.t0 = this.gui.getTime_client();
            int res = obj.getTime();
            if(delay_max!=delay_min) {
                TimeUnit.SECONDS.sleep(random.nextInt(delay_max - delay_min) + delay_min);
            }
            else{
                TimeUnit.SECONDS.sleep(delay_max);
            }
            this.t1 = this.gui.getTime_client();
            System.out.println("info: otrzymano: " +dt.format(res));

            this.gui.setTime_client(( res + ( this.t1 - this.t0)/2));
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

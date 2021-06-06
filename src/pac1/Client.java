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
            int res = obj.getTime();
            Random random = new Random();
            TimeUnit.SECONDS.sleep(random.nextInt(delay_max - delay_min) + delay_min);
            System.out.println("info: otrzymano: " +dt.format(res));
            this.gui.setTime_client((this.gui.getTime_client()+res)/2);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

package pac1;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    static Hello obj = null;
    private final ClientGui gui;

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
            System.out.println("Znalezione rejestr");
            obj = (Hello) registry.lookup("Hello");
            System.out.println("Znaleziono metode");
        } catch (Exception e) {
            System.out.println("Client exception1: "
                    + e.getMessage());
        }

        try {
            int res = obj.getTime();
            System.out.println("otrzymano: " +res);

            this.gui.setTime_client((this.gui.getTime_client()+res)/2);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}

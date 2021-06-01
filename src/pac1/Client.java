package pac1;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    static Hello obj = null;
    private ClientGui gui;

    Client(ClientGui cGui) {
        this.gui = cGui;
    }
    public void callServer(){
        try {
            //Hello obj = (Hello) Naming.lookup("Hello");
            System.out.println("udalo sie podlaczyc ");

            Registry registry = LocateRegistry.getRegistry("localhost");
            obj = (Hello) registry.lookup("Hello");
        } catch (Exception e) {
            System.out.println("Client exception1: "
                    + e.getMessage());
        }

        try {
            int res = obj.getTime();
            System.out.println("otrzymano: " +res);

            this.gui.setTime_client((int)((this.gui.getTime_client()+res)/2));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
    /*
    public static void main(String[] args) {

        try {
            //Hello obj = (Hello) Naming.lookup("Hello");
            System.out.println("udalo sie podlaczyc ");

            Registry registry = LocateRegistry.getRegistry("localhost");
            obj = (Hello) registry.lookup("Hello");
        } catch (Exception e) {
            System.out.println("Client exception1: "
                    + e.getMessage());
        }

        try {
            int res = obj.getTime();
            System.out.println("otrzymano: " +res);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

     */
}

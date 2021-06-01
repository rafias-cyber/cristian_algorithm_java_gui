package pac1;

import javafx.application.Platform;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;

public class Server extends UnicastRemoteObject implements Hello  {
    private FxSample gui;
    private Registry registry = null;
    private SimpleDateFormat dt0 = new SimpleDateFormat("hh:mm:ss");
    public Server(FxSample gui) throws RemoteException {
        super();
        this.gui=gui;



        try {
            registry = LocateRegistry.createRegistry(1099);
            System.out.println("java RMI registry created.");
            registry.rebind("Hello", this);
            System.err.println("Server ready");
        } catch (Exception e) {
            System.out.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }

    }


    @Override
    public void startup(int time) throws RemoteException {

    }

    @Override
    public int getTime()
    {
        String tmp="error";
        try {
             tmp = getClientHost();
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }
        //gui.setMessage("message1");

        this.gui.setMessage("Klient o adresie ip "+tmp+" otrzymal czas " +dt0.format(this.gui.getTime_server()) +";");
        return this.gui.getTime_server();

    }

/*
    public void main(String args[]) {
        rmiRe();
        //this.gui = new FxSample();

        try {
            // Instantiating the implementation class


            // Exporting the object of implementation class
            // (here we are exporting the remote object to the stub)
            Hello stub = (Hello) UnicastRemoteObject.exportObject(this, 0);

            // Binding the remote object (stub) in the registry
            Registry registry = LocateRegistry.getRegistry();

            registry.bind("Hello", stub);
            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }




    }

 */

}


package pac1;

import javafx.application.Platform;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;

public class Server extends UnicastRemoteObject implements Hello  {
    private final FxSample gui;
    private Registry registry = null;
    private final SimpleDateFormat dt0 = new SimpleDateFormat("hh:mm:ss");

    /**
     * This is backend of server.
     * Constructor of class. Main purpose is to create new registry that is working on port 1099.
     * Next Hello interface is rebounded to it.
     *
     * @param gui - gui from FxSample
     * @throws RemoteException
     */
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

    /**
     * Main purpose of getTime method is to send current  time to client by rmi.
     * Furthermore this method calls setMessage form GUI class (FxSample)
     * @return server time to remote host
     */
    @Override
    public int getTime()
    {
        String tmp="error";
        try {
             tmp = getClientHost();
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }
        this.gui.setMessage("Klient o adresie ip "+tmp+" otrzymal czas " +dt0.format(this.gui.getTime_server()) +";");
        return this.gui.getTime_server();

    }

}


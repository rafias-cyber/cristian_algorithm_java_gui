package pac1;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Hello extends Remote {
    void startup(int time) throws RemoteException;
    int getTime() throws RemoteException;
}

package pac1;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Hello extends Remote {
    int getTime() throws RemoteException;
}

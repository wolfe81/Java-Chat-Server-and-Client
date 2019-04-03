/**
 *
 * @author aic 2016
 */
import java.rmi.*;
import java.util.*;

public interface IPrimaryServer extends Remote {
    //add a new server
    void addServer(IServer s) throws RemoteException;
    
    //remove a server from server list
    void removeServer(IServer s) throws RemoteException;

    //return the server list
    ArrayList<IServer> getserverlist() throws RemoteException;

    //return seatmap
    int [] getseatmap() throws RemoteException;
    
    //after a server be able to book a seat, update the map on the primary server
    void updateseatmap(int seatnumber) throws RemoteException;
}

/**
 *
 * @author aic
 */
import java.rmi.*;

public interface IServer extends Remote {
    
    //for server to call to display the message on the clientside
    void sendmessage(String message) throws RemoteException;
    
    //return the client nickname
    String getname() throws RemoteException;

    //return the current seat map
    int[] seatmap() throws RemoteException;
    
    //lock the seat on the current server
    boolean lockseat(int seatnumber) throws RemoteException;
   
    //try to book a seat
    boolean book(int seatnumber) throws RemoteException;
    
    //unlock to book the seat
    void unlocksucess(int seatnumber) throws RemoteException;
    
    //unlock due to conflict
    void unlockfailed(int seatnumber) throws RemoteException;
} 

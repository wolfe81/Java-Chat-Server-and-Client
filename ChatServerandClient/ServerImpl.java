/**
 *
 * @author aic
 */
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;


public class ServerImpl extends UnicastRemoteObject implements IServer{
    private String name;
    private final int SIZE =50;
    int[] seat;
    private IPrimaryServer pserver;
    
    public ServerImpl(String name, IPrimaryServer pserver) throws RemoteException
    {
        super();
        this.name = name;
        this.pserver = pserver;
	seat = new int[SIZE];
        int[] pserverseatmap = pserver.getseatmap();
        
        //copy the seat map from the primary server
        for(int i=0;i<seat.length;i++)
        {
            seat[i] = pserverseatmap[i];
        }    
    }
    	
    //display a message
    public void sendmessage(String message) throws RemoteException
    {
        System.out.println(message);
    }
    
    //return the client nickname
    public String getname() throws RemoteException
    {
        return name;
    }

    //return the current seat map
    public synchronized int[] seatmap() throws RemoteException
    {
	return seat;
    }
    
    //before booking, send the request to all servers to lock the seat first
    private  boolean lockseatrequest(int seatnumber) throws RemoteException
    {
        ArrayList<IServer> serverlist = pserver.getserverlist();
        for(IServer s: serverlist)
        {
            try {
                if (!s.lockseat(seatnumber))
                    return false;
            }
            catch(RemoteException e)
            {
                System.out.println("A server is down.\n" + e.getMessage());
            }
        }
        return true;
    }
    
    //lock the seat on the current server
    public synchronized boolean lockseat(int seatnumber) throws RemoteException
    {
        if(seat[seatnumber] == 0)
        {
            seat[seatnumber] = -1;
            return true;
        }
        else
        {
            return false;
        }
    }
    
    
    public boolean book(int seatnumber) throws RemoteException
    {
        System.out.println("Request:"+seatnumber);
        if(seat[seatnumber]!=0) //seat is reserved by another customer already
            return false;
     
        ArrayList<IServer> serverlist = pserver.getserverlist();
        
        if (lockseatrequest(seatnumber)) //be able to lock the seat on all servers, go ahead to book the seat
        {
            System.out.println("lock on all servers");
            for(IServer s: serverlist)
            {
                try{
                    s.unlocksucess(seatnumber);
                }
                catch(RemoteException e)
                {
                    System.out.println("A server is down.\n" + e.getMessage());
                }
            }
            seat[seatnumber] = 1; //change it to reserved
            pserver.updateseatmap(seatnumber);
            //System.out.println("Done.");
            return true;
        }
        else //failed to lock on some servers, unlock it; cannot book the seat
        {
            for(IServer s: serverlist)
            {
                try{
                    s.unlockfailed(seatnumber);
                }
                catch(RemoteException e)
                {
                    System.out.println("A server is down.\n" + e.getMessage());
                }
            }
            //System.out.println("Failed.");
            return false;
        }
    }
    
    //unlock to book the seat
    public synchronized void unlocksucess(int seatnumber) throws RemoteException
    {
        seat[seatnumber] = 1; //book the seat sucessfully
    }
    
    //unlock due to conflict
    public synchronized void unlockfailed(int seatnumber) throws RemoteException
    {
        seat[seatnumber] = 0; //since cannot process booking, unlock it
    }
}

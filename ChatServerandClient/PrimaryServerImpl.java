/**
 *
 * @author aic
 */
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class PrimaryServerImpl extends UnicastRemoteObject implements IPrimaryServer {
    private ArrayList<IServer> serverlist;
    private final int SIZE =50;
    private int [] seatmap;
    
    public PrimaryServerImpl() throws RemoteException, InterruptedException
    {
        super();
        serverlist = new ArrayList<IServer>();
	seatmap = new int[SIZE];
	for (int i=0;i<50;i++)
		seatmap[i]=0; //0 available 1 reserved -1 locked
    }
    
    //periodcally check all servers are working fine, if not, remove it from the list
    public void healthcheck()throws RemoteException, InterruptedException
    {
        IServer errorserver=null;
        while (true)
        {
            try{

                for(IServer s: serverlist)
                {
                    errorserver = s;
                    s.getname();
                }
            }
            catch(RemoteException e)
            {
                removeServer(errorserver);
            }
            TimeUnit.SECONDS.sleep(30); //sleep 30 seconds
        }
    }

    //add a new server
    public void addServer(IServer s) throws RemoteException
    {
	serverlist.add(s);
    }
    
    //remove a server from server list
    public void removeServer(IServer s) throws RemoteException
    {
	serverlist.remove(s);
    }

    //return the server list
    public ArrayList<IServer> getserverlist() throws RemoteException
    {
	return serverlist;
    }

    //return seatmap
    public synchronized int [] getseatmap() throws RemoteException
    {
	return seatmap;
    }

    //after a server be able to book a seat, update the map on the primary server
    public void updateseatmap(int seatnumber) throws RemoteException
    {
        seatmap[seatnumber] = 1;
    }

}

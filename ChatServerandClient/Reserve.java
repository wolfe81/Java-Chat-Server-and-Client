import java.rmi.* ;
    public interface  Reserve  extends Remote 
	{
           public int reserve(int n, String x)  throws RemoteException ;
		   public String available() throws RemoteException;
    }

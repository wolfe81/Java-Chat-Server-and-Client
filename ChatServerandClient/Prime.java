import java.rmi.* ;
    public interface  Prime  extends Remote
	{
		public String connectServer() throws RemoteException;
		public String connectClient() throws RemoteException;
           //public int reserve(int n, String x)  throws RemoteException ;
		   //public String available() throws RemoteException;
    }

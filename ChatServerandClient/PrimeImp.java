import java.rmi.* ;
import java.rmi.server.* ;
import java.util.*;
  public class PrimeImp extends UnicastRemoteObject implements Prime 
  {
       public PrimeImp() throws RemoteException
	   {
			
       }
	   public Vector servlist = new Vector();
	   public String[] seatlist = new String[50];
	   public String connectServer() throws RemoteException
	   {
		   String st = "rServer" + servlist.size();
		   ReserveImp s = new ReserveImp();
		   servlist.add(s);
		   return st;
	   }
	   
	   public String connectClient()
	   {
		   Random r = new Random();
		   int n = r.nextInt(servlist.size());
		   String s = "rmi://127.0.0.1/rServer" + n;
		   return s;
	   }
	   
  }
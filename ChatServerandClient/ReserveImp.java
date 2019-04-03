import java.rmi.* ;
import java.rmi.server.* ;
import java.util.*;
  public class ReserveImp extends UnicastRemoteObject implements Reserve 
  {
       public ReserveImp() throws RemoteException 
	   {
		
       }
	   
	   private PrimeImp p;
	   
	   public void setPrime(PrimeImp p)
	   {
		   this.p = p;
	   }
	   
	   private int[] seats = new int[50];
	   
       public synchronized int reserve(int n, String x) throws RemoteException
	   {
		   int locked = 0;
		   for (Vector r: p.servlist)
		   {
			   if(((ReserveImp)r).seats[n-1] != 0)
				{
					locked++; //seat is not open, updates locked
				}
				else
				{
					System.out.println(r.seats[n-1]);
					r.seats[n-1] = 1; //sets seat to locked
				}
		   }
		   if (locked == 0) //seat in all servers were open
		   {
			   for (Vector r: p.servlist)
			   {
				   ((ReserveImp)r).seats[n-1] = 3;  //sets seat to name of guest
			   }
			   for (String r: p.seatlist)
			   {
				   System.out.println(r);  //sets seat to name of guest
			   }
			   p.seatlist[n] = x; //updates prime server array to set name at the appropriate seat.
			   return 1; // return true for seat being reserved
		   }
		   else   //seat is not open for all servers. deny request
		   {
			   for (Vector r: p.servlist)
			   {
				   ((ReserveImp)r).seats[n-1] = 0; //seat is reverted back to open on all servers
			   }
			   return 0; // return that we were not able to reserve seat
		   }
		   
	   }
	   
	   public synchronized String available() throws RemoteException
	   {
		   String s = "";
		   for(int i = 1; i <= seats.length; i++)
		   {
			   if (seats[i-1] != 0)
			   {
				   
			   }
			   else
			   {
				   s += i + " ";
			   }
		   }
		   return s;
	   }
  }
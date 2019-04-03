import java.rmi.* ;
import java.util.*;
public class Client 
{
       public static void main(String [] args) throws Exception 
	   {
		 Prime server =(Prime) Naming.lookup("rmi://127.0.0.1/primeserver");
		 String f = server.connectClient();
         Reserve server1 =(Reserve) Naming.lookup(f);  
	     Scanner scan = new Scanner(System.in);
		 boolean more = true;
			while(more)
			{
				System.out.println(server1.available());
				System.out.println("Please choose an available seat: ");
         
				int n = scan.nextInt();
				scan.nextLine(); // moves to next line
				System.out.println("Please enter your name");
				String x = scan.nextLine();
				int reservation = server1.reserve(n, x);
				if (reservation == 1)
				{
					System.out.println("Reservation successful");
				}
				else
				{
					System.out.println("Reservation unsuccessful: please try a different seat");
				}
				System.out.println("Would you like to reserve more seats? Y/N");
				String s = scan.nextLine();
				if (s.equals("N") || s.equals("n"))
				{
					more = false;
				}
			}	
      	 
       }
}

import java.rmi.* ;
public class Server 
{
       public static void main(String [] args) throws Exception
	   {
		   Prime server = (Prime)Naming.lookup("rmi://127.0.0.1/primeserver");
		   
           ReserveImp server1 = new ReserveImp();
           Naming.rebind(server.connectServer(), server1);
		   PrimeImp s = new PrimeImp();
		   server1.setPrime(s);
       }
}
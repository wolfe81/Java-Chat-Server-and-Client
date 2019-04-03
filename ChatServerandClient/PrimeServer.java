import java.rmi.* ;
public class PrimeServer
 {
       public static void main(String [] args) throws Exception
	   {
              Prime server = new PrimeImp();
              Naming.rebind("primeserver", server);
       }
}
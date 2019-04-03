/**
 *
 * @author aic
 * This is a simple implementation 
 */
import java.util.Scanner;
import java.rmi.*;

public class PrimaryServer {
    public static void main(String[] args) {
	try {
            //System.setSecurityManager(new RMISecurityManager());
            
            //start rmiregistry with port 1099
            java.rmi.registry.LocateRegistry.createRegistry(1099);
			 	
            IPrimaryServer s=new PrimaryServerImpl();	
            Naming.rebind("primaryserver", s);
            System.out.println("Primary Server is ready.");
            ((PrimaryServerImpl)s).healthcheck();
	}
        catch (Exception e) {
                    System.out.println("Primary Server failed: " + e);
		}
	}
    
}

package client;

import java.rmi.registry.LocateRegistry;	


public class ClientController {


	public ClientController(){
		try {
			LocateRegistry.getRegistry(1024).lookup("127.0.0.1");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

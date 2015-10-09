package fr.alma.middleware.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import fr.alma.middleware.data.Topic;
import fr.alma.middleware.remote.InterfaceAffichageClient;
import fr.alma.middleware.remote.InterfaceServeurForum;
import fr.alma.middleware.remote.InterfaceSujetDiscussion;

public class MainServer{


	private InterfaceServeurForum isf;
	private InterfaceAffichageClient iac;
	private List<Topic> topicList;

	protected MainServer() throws RemoteException {
		super();
		this.isf = new ServerForum();
		this.iac = new ServerAfficheClient();
	}
    
    public InterfaceServeurForum getIsf()
    {
        return isf;
    }
    
    public InterfaceAffichageClient getIac()
    {
        return iac;
    }
    
        

	public static void main(String[] args) {
		System.out.println("HELLO WORLD !");
		runServer("127.0.0.1", 1024);
		
	}



	public static void runServer(String ip_adress, int port){
		try {
			
			Registry registry =  LocateRegistry.createRegistry(port);
			MainServer obj = new MainServer();
			registry.rebind("forum", obj.getIsf());
			registry.rebind("affichage", obj.getIac());
			
			System.out.println("Serveur lancé à l'adresse "+ip_adress + ":" + port);
		} catch (Exception e) {
			System.out.println("[Serveur] : Erreur : " + e.getMessage());
			e.printStackTrace();
		}
	}
}

package fr.alma.middleware.server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import fr.alma.middleware.remote.InterfaceAffichageClient;
import fr.alma.middleware.remote.InterfaceServeurForum;
import fr.alma.middleware.remote.InterfaceSujetDiscussion;

public class MainServer extends UnicastRemoteObject{


	private InterfaceServeurForum isf;
	private InterfaceAffichageClient iac;
	private InterfaceSujetDiscussion isd;

	protected MainServer() throws RemoteException {
		super();
		this.isf = new ServerForum();
		this.iac = new ServerAfficheClient();
		this.isd = new ServerSujetDiscussion();
		
	}



	public static void main(String[] args) {
		runServer("127.0.0.1", 1024);
		
	}



	public static void runServer(String ip_adress, int port){
		try {
			
			LocateRegistry.createRegistry(port);
			Registry registry = LocateRegistry.getRegistry(port);
			MainServer obj = new MainServer();
			registry.rebind("127.0.0.1", obj);
			
			System.out.println("Serveur lancé à l'adresse "+ip_adress + ":" + port);
		} catch (Exception e) {
			System.out.println("[Serveur] : Erreur : " + e.getMessage());
			e.printStackTrace();
		}
	}
}

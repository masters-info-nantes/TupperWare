package fr.alma.middleware.server;

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
		
	}



	public static void main(String[] args) {
		System.out.println("HELLO WORLD !");
		runServer("127.0.0.1", 1024);
		
	}



	public static void runServer(String ip_adress, int port){
		try {
			
			Registry registry =  LocateRegistry.createRegistry(port);
			MainServer obj = new MainServer();
			registry.rebind(ip_adress, obj);
			
			System.out.println("Serveur lancé à l'adresse "+ip_adress + ":" + port);
		} catch (Exception e) {
			System.out.println("[Serveur] : Erreur : " + e.getMessage());
			e.printStackTrace();
		}
	}
}

package fr.alma.middleware.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class MainServer extends UnicastRemoteObject{



	protected MainServer() throws RemoteException {
		super();
	}



	public static void main(String[] args) {
		runServer("127.0.0.1", 1024);
	}



	public static void runServer(String ip_adress, int port){
		try {
			LocateRegistry.createRegistry(port);
			Registry registry = LocateRegistry.getRegistry(port);
			MainServer obj = new MainServer();
			registry.rebind(ip_adress, obj);
			System.out.println("Serveur lancé à l'adresse "+ip_adress);
		} catch (Exception e) {
			System.out.println("[Serveur] : Erreur : " + e.getMessage());
			e.printStackTrace();
		}
	}
}

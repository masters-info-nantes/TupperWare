package fr.alma.middleware.data;

import java.rmi.RemoteException;

import fr.alma.middleware.remote.InterfaceAffichageClient;

public class Client implements InterfaceAffichageClient{

	
	private String name;
	private String ip;
	
	public String getName(){
		return name;
	}
	
	public String getIP(){
		return ip;
	}

	@Override
	public void affiche(String Message) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	
	
}

package fr.alma.middleware.remote;

import java.rmi.*;


public interface InterfaceSujetDiscussion extends Remote {
	
	public void inscription(InterfaceAffichageClient c)throws RemoteException;
	
	public void desInscription(InterfaceAffichageClient c)throws RemoteException;
	
	public void diffuse(String message)throws RemoteException;
}


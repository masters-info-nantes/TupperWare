package fr.alma.middleware.remote;

import java.rmi.*;

public interface InterfaceSujetDiscussion extends Remote {
	
	public void inscription(InterfaceAffichageClient c,String topicName)throws RemoteException;
	
	public void desInscription(InterfaceAffichageClient c,String topicName)throws RemoteException;
	
	public void diffuse(String message,String topicName)throws RemoteException;
}

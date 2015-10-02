package fr.alma.middleware.server;

import java.rmi.RemoteException;

import fr.alma.middleware.remote.InterfaceSujetDiscussion;
import fr.alma.middleware.remote.InterfaceAffichageClient;

public class ServerSujetDiscussion implements InterfaceSujetDiscussion {

    @Override
	public void inscription(InterfaceAffichageClient c)throws RemoteException
    {
    }
    
	@Override
	public void desInscription(InterfaceAffichageClient c)throws RemoteException
    {
    }
    
	@Override
	public void diffuse(String message)throws RemoteException
    {
    }
    
}
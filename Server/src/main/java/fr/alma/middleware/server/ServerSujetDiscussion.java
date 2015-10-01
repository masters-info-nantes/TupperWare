package fr.alma.middleware.server;

import java.rmi.RemoteException;

import fr.alma.middleware.remote.InterfaceAffichageClient;
import fr.alma.middleware.remote.InterfaceSujetDiscussion;
import fr.alma.middleware.topic.Topic;

public class ServerSujetDiscussion implements InterfaceSujetDiscussion{

	
	private Topic topic;
	
	@Override
	public void inscription(InterfaceAffichageClient c) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void desInscription(InterfaceAffichageClient c)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void diffuse(String message) throws RemoteException {
		topic.writeLineInFile(message);
	}

}

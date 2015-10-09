package fr.alma.middleware.server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import fr.alma.middleware.data.Client;
import fr.alma.middleware.data.Topic;
import fr.alma.middleware.remote.InterfaceAffichageClient;
import fr.alma.middleware.remote.InterfaceSujetDiscussion;

public class ServerSujetDiscussion extends UnicastRemoteObject implements InterfaceSujetDiscussion, Serializable{

	protected ServerSujetDiscussion(String name) throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
		try {
			 t = new Topic(name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ServerSujetDiscussion() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Topic t;


	@Override
	public void diffuse(String message) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inscription(InterfaceAffichageClient c) throws RemoteException {
		// TODO Auto-generated method stub
		//t.addClient(c);
		
	}

	@Override
	public void desInscription(InterfaceAffichageClient c) throws RemoteException {
		// TODO Auto-generated method stub
		//t.removeClient(c);
		
	}
	
	
}

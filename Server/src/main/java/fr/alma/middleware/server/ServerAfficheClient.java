package fr.alma.middleware.server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import fr.alma.middleware.remote.InterfaceAffichageClient;

public class ServerAfficheClient extends UnicastRemoteObject implements InterfaceAffichageClient, Serializable {

	protected ServerAfficheClient() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void affiche(String Message) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getLogsContent(int topic) throws RemoteException {
		return "salut!";
	}

}

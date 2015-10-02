package fr.alma.middleware.server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import fr.alma.middleware.data.Topic;
import fr.alma.middleware.remote.InterfaceServeurForum;
import fr.alma.middleware.remote.InterfaceSujetDiscussion;

public class ServerForum extends UnicastRemoteObject implements InterfaceServeurForum, Serializable {

	protected ServerForum() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> list;
	
	@Override
	public InterfaceSujetDiscussion obtientSujet(String titre)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void proposeSujet(String titre, InterfaceSujetDiscussion sujet)
			throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<String> getTopicsTitle() throws RemoteException {
		list = new ArrayList<String>();
		
		list.add("jambon");
		return list;
	}

	@Override
	public List<String> getUsersList(String topic) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}

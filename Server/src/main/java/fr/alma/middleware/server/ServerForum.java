package fr.alma.middleware.server;

import java.rmi.RemoteException;
import java.util.List;

import fr.alma.middleware.remote.InterfaceServeurForum;
import fr.alma.middleware.remote.InterfaceSujetDiscussion;

public class ServerForum implements InterfaceServeurForum {

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getUsersList(String topic) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}

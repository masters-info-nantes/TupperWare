package server;

import java.rmi.RemoteException;

import remote.InterfaceServeurForum;
import remote.InterfaceSujetDiscussion;

public class ServerForum implements InterfaceServeurForum {

	@Override
	public InterfaceSujetDiscussion obtientSujet(String titre)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String obtientTitresDesSujets() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void proposeSujet(String titre, InterfaceSujetDiscussion sujet)
			throws RemoteException {
		// TODO Auto-generated method stub

	}

}

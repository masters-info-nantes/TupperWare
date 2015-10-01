package fr.alma.middleware.remote;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface InterfaceServeurForum extends Remote {

	public InterfaceSujetDiscussion obtientSujet(String titre)throws RemoteException;

	public List<String> getTopicsTitle() throws RemoteException;

	public void proposeSujet(String titre, InterfaceSujetDiscussion sujet) throws RemoteException;
}


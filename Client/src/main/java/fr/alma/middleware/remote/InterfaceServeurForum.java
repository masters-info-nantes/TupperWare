package fr.alma.middleware.remote;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface InterfaceServeurForum extends Remote {

    public String getTopicContent(String topic) throws RemoteException;

	public List<String> getTopicsTitle() throws RemoteException;

	public void proposeSujet(String titre) throws RemoteException;
}


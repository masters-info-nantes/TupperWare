package fr.alma.middleware.remote;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface InterfaceServeurForum extends Remote {

	public List<String> getTopicsTitle() throws RemoteException;
	public String getTopicContent(String topic) throws Exception;
	public List<String> getUsersList(String topic) throws Exception;
	public void proposeSujet(String titre) throws Exception;
    public InterfaceSujetDiscussion obtientSujet(String titre) throws RemoteException;
    public List<String> getSubscriptionListForClient(InterfaceAffichageClient c) throws RemoteException;


}


package fr.alma.middleware.server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import fr.alma.middleware.data.Client;
import fr.alma.middleware.data.Topic;
import fr.alma.middleware.remote.InterfaceAffichageClient;
import fr.alma.middleware.remote.InterfaceSujetDiscussion;

public class ServerSujetDiscussion extends UnicastRemoteObject implements InterfaceSujetDiscussion, Serializable{

	protected ServerSujetDiscussion() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Topic> topicList = new ArrayList<Topic>();
	
	/* Need to get the topic list */
	@Override
	public void inscription(InterfaceAffichageClient c,String topicName) throws RemoteException {
		// TODO Auto-generated method stub
		for(Topic t : topicList){
			if(t.getName() == topicName){
				t.addClient((Client)c);
			}
		}
	}


	@Override
	public void desInscription(InterfaceAffichageClient c,String topicName) throws RemoteException {
		// TODO Auto-generated method stub
		for(Topic t : topicList){
			if(t.getName() == topicName){
				t.removeClient((Client)c);
			}
		}
	}

	//Write into log file
	@Override
	public void diffuse(String message, String topicName) throws RemoteException {
		// TODO Auto-generated method stub
		for(Topic t : topicList){
			if(t.getName() == topicName){
				t.writeLineInFile(message);
			}
		}
	}
	
	
}

package fr.alma.middleware.controller;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import fr.alma.middleware.remote.InterfaceAffichageClient;
import fr.alma.middleware.remote.InterfaceServeurForum;
import fr.alma.middleware.remote.InterfaceSujetDiscussion;


public class ClientController{

	private InterfaceServeurForum interfaceServerForum;
	private InterfaceAffichageClient interfaceAffichageClient;
	private InterfaceSujetDiscussion interfaceSujetDiscussion;
	
	private List<String> topicList;
	private int currentTab;
	
	public ClientController(){
		Registry registry = null;
		try {
			registry = LocateRegistry.getRegistry(1234);
			interfaceServerForum = (InterfaceServeurForum) registry.lookup("forum");
			interfaceAffichageClient = (InterfaceAffichageClient) registry.lookup("display");
			interfaceSujetDiscussion = (InterfaceSujetDiscussion) registry.lookup("topic");
			
			this.topicList = interfaceServerForum.getTopicsTitle();
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.currentTab = 0;
	}
	
	
	
	public InterfaceServeurForum getISF(){
		return interfaceServerForum;
	}
	
	public InterfaceAffichageClient getAC(){
		return interfaceAffichageClient;
	}
	
	public InterfaceSujetDiscussion getSD(){
		return interfaceSujetDiscussion;
	}
	
	public void connect(){
		
	}
	
	public void disconnect(){
		
	}
	
	public void logon(){
		
	}

	public List<String> getExistingTopics() {
		//some stuff
		return topicList;
	}
	
	//Get topic logs file from server
	public String getLogsContent(int index){
		return null;
	}
	
	public int getCurrentTab(){
		return this.currentTab;
	}
	
}

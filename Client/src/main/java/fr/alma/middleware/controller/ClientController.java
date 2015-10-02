package fr.alma.middleware.controller;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Date;
import java.util.List;

import fr.alma.middleware.remote.InterfaceAffichageClient;
import fr.alma.middleware.remote.InterfaceServeurForum;
import fr.alma.middleware.remote.InterfaceSujetDiscussion;


public class ClientController{

	private InterfaceServeurForum interfaceServerForum;
	private InterfaceAffichageClient interfaceAffichageClient;
	private InterfaceSujetDiscussion interfaceSujetDiscussion;

	private List<String> topicList;
	private List<String> subscriptionsList;
	private int currentTab;

	public ClientController(){
		this.currentTab = 0;
		this.connect();
		
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

	private void connect(){
		try {
			interfaceServerForum = (InterfaceServeurForum) LocateRegistry.getRegistry(1024).lookup("forum");
			interfaceAffichageClient = (InterfaceAffichageClient) LocateRegistry.getRegistry(1024).lookup("affichage");
			//interfaceSujetDiscussion = (InterfaceSujetDiscussion) LocateRegistry.getRegistry(1024).lookup("sujet");
            //interfaceAffichageClient = (InterfaceAffichageClient) registry.lookup("affichage");

		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void disconnect(){

	}

	public void joinTopic(){

	}

	public List<String> getExistingTopics() {
		//some stuff
		List<String> list = null;
		try {
			list =  interfaceServerForum.getTopicsTitle();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 0; i < list.size(); i++){
			System.out.println(list.get(i));
		}
	
		return list;
	}

	//Get topic logs file from server
	public String getLogsContent(String logsFile){
		String logs = "";
		try {
			logs = interfaceAffichageClient.getLogsContent(logsFile);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return logs;
	}

	public int getCurrentTabName(){
		return this.currentTab;
	}
	
	public List<String> getSubcriptionsList(){
		return this.subscriptionsList;
	}
	
	public String getSubcriptionByIndex(int i){
		return this.subscriptionsList.get(i);
		
	}

	
	public void write(String message, String name){
		try {
			interfaceSujetDiscussion.diffuse(
			"["+ new Date().toString() +"]" + "<" + name + "<" + message
			);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}

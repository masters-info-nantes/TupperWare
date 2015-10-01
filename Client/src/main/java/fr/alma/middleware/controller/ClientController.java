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
	private int currentTab;

	public ClientController(){
		this.currentTab = 0;
		connect();
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
		try {
			LocateRegistry.getRegistry(1024).lookup("127.0.0.1");
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
		return list;
	}

	//Get topic logs file from server
	public String getLogsContent(int index){
		String logs = "";
		try {
			logs = interfaceAffichageClient.getLogsContent(index);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return logs;
	}

	public int getCurrentTab(){
		return this.currentTab;
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

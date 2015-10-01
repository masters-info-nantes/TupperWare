package fr.alma.middleware.controller;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

import fr.alma.middleware.remote.InterfaceAffichageClient;
import fr.alma.middleware.remote.InterfaceServeurForum;
import fr.alma.middleware.remote.InterfaceSujetDiscussion;


public class ClientController{

	private InterfaceServeurForum interfaceServerForum;
	private InterfaceAffichageClient interfaceAffichageClient;
	private InterfaceSujetDiscussion interfaceSujetDiscussion;
	
	private List<Subject> topicList;
	private int currenTab;
	
	public ClientController(){
		this.topicList = new ArrayList<Subject>();
		this.currenTab = 0;
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

	public List<Subject> getExistingTopics() {
		//some stuff
		return topicList;
	}
	
	//Get topic logs file from server
	public String getLogsContent(int index){
		return null;
	}
	
	public int getCurrentTab(){
		return this.currenTab;
	}
	
}

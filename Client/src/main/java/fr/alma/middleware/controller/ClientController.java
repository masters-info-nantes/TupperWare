package fr.alma.middleware.controller;

import fr.alma.middleware.remote.InterfaceAffichageClient;
import fr.alma.middleware.remote.InterfaceServeurForum;
import fr.alma.middleware.remote.InterfaceSujetDiscussion;


public class ClientController{

	private InterfaceServeurForum interfaceServerForum;
	private InterfaceAffichageClient interfaceAffichageClient;
	private InterfaceSujetDiscussion interfaceSujetDiscussion;
	
	
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
	
}

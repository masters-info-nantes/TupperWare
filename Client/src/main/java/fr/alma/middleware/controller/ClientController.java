package fr.alma.middleware.controller;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.alma.middleware.remote.InterfaceAffichageClient;
import fr.alma.middleware.remote.InterfaceServeurForum;


public class ClientController implements InterfaceAffichageClient{


	private Calendar calendar = Calendar.getInstance();

	private int hours = calendar.get(Calendar.HOUR_OF_DAY);
	private int minutes = calendar.get(Calendar.MINUTE);

	private InterfaceServeurForum interfaceServerForum;
	private InterfaceAffichageClient interfaceAffichageClient;

	private List<String> topicList;
	private List<String> subscriptionsList;
	private String username;
	private String currentTopicName;

	public ClientController(){
		calendar.setTime(new Date());
		this.connect();
		topicList = getExistingTopics();


	}



	public InterfaceServeurForum getISF(){
		return interfaceServerForum;
	}

	public InterfaceAffichageClient getAC(){
		return interfaceAffichageClient;
	}

	private void connect(){
		try {
			interfaceServerForum = (InterfaceServeurForum) LocateRegistry.getRegistry(1024).lookup("forum");
			interfaceAffichageClient = (InterfaceAffichageClient) LocateRegistry.getRegistry(1024).lookup("affichage");
			topicList = interfaceServerForum.getTopicsTitle();
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

	public int getTopicListSize(){
		return this.topicList.size();
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
			logs = interfaceServerForum.obtientSujet(currentTopicName).getLogsContent();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return logs;
	}

	public String getCurrentTopicName(){
		return this.currentTopicName;
	}

	public List<String> getSubcriptionsList(){
		return this.subscriptionsList;
	}

	public String getSubcriptionByIndex(int i){
		return this.subscriptionsList.get(i);

	}


	public void write(String message, String topicName){
		System.out.println("["+ hours + ":" + minutes +"]" + " <" + this.username + "> : " + message
				);
		try {
			interfaceServerForum.obtientSujet(currentTopicName).diffuse(
					"["+ hours + ":" + minutes +"]" + " <" + this.username + "> " + message
					);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}



	public String getName() {
		return username;
	}



	public boolean isSubscribeOn(String selectedItem) {
		if(topicList.contains(selectedItem)){
			return true;	
		}else{
			return false;
		}

	}



	public void setUsername(String username) {
		this.username = username;
	}



	public void subscribe() {
		try {
			interfaceServerForum.obtientSujet(currentTopicName).inscription(interfaceAffichageClient);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}



	public void unSubscribe() throws RemoteException {
		interfaceServerForum.obtientSujet(currentTopicName).desInscription(interfaceAffichageClient);
	}



	public void setCurrentTopic(String topicName) {
		this.currentTopicName = topicName;
	}



	@Override
	public void affiche(String Message, String topicName) throws RemoteException {
		System.out.println("affiche:"+Message);
		//TODO: Doit appeler la vue pour ajouter le contenu dans la textbox 
		
	}




}

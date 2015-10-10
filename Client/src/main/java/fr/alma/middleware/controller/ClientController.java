package fr.alma.middleware.controller;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;

import fr.alma.middleware.remote.InterfaceAffichageClient;
import fr.alma.middleware.remote.InterfaceServeurForum;


public class ClientController extends UnicastRemoteObject implements InterfaceAffichageClient, Serializable {


	private Calendar calendar = Calendar.getInstance();

	private int hours = calendar.get(Calendar.HOUR_OF_DAY);
	private int minutes = calendar.get(Calendar.MINUTE);

	private InterfaceServeurForum interfaceServerForum;
	private InterfaceAffichageClient interfaceAffichageClient;

	private List<String> topicList;
	private List<String> subscriptionsList;
	private String username;
	private String currentTopicName;

	public ClientController() throws Exception{
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
			//interfaceAffichageClient = (InterfaceAffichageClient) LocateRegistry.getRegistry(1024).lookup("affichage");
			topicList = interfaceServerForum.getTopicsTitle();
            subscriptionsList = new ArrayList<String>();
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
			logs = interfaceServerForum.obtientSujet(logsFile).getLogsContent();
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
            System.out.println("TOPIC"+topicName);
			interfaceServerForum.obtientSujet(topicName).diffuse(
					"["+ hours + ":" + minutes +"]" + " <" + this.username + "> " + message
					);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}



	public String getName() {
		return username;
	}

	public void showSubscriptionList() throws RemoteException{
		for(int i = 0; i < interfaceServerForum.getSubscriptionListForClient(this).size(); i++){
			System.out.print(interfaceServerForum.getSubscriptionListForClient(this).get(i)+", ");
		}
	}

	public boolean isAlreadySubscribeOn(String selectedItem) throws RemoteException {
		
		showSubscriptionList();
		if(!interfaceServerForum.getSubscriptionListForClient(this).contains(selectedItem)){
			currentTopicName = selectedItem;
            subscribe();
			return false;	
		}else{
			unSubscribe();
			return true;
		}

	}



	public void setUsername(String username) {
		this.username = username;
	}



	public void subscribe() {
		try {
			interfaceServerForum.obtientSujet(currentTopicName).inscription(this);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}



	public void unSubscribe() throws RemoteException {
		interfaceServerForum.obtientSujet(currentTopicName).desInscription(this);
	}



	public void setCurrentTopic(String topicName) {
        System.out.println("CURRENT:"+topicName);
		this.currentTopicName = topicName;
	}



	@Override
	public void affiche(String Message, String topicName) throws RemoteException {
		//TODO: Doit appeler la vue pour ajouter le contenu dans la textbox 
		
	}







}

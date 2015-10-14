package fr.alma.middleware.server;

import java.io.File;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import fr.alma.middleware.data.Topic;
import fr.alma.middleware.remote.InterfaceAffichageClient;
import fr.alma.middleware.remote.InterfaceServeurForum;
import fr.alma.middleware.remote.InterfaceSujetDiscussion;

public class ServerForum extends UnicastRemoteObject implements InterfaceServeurForum, Serializable {

	protected ServerForum() throws RemoteException {
		super();

		try {
			this.list = listLogsFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final long serialVersionUID = 1L;
	private List<Topic> list = new ArrayList<Topic>();

	@Override
	public InterfaceSujetDiscussion obtientSujet(String titre)
			throws RemoteException {
		for(Topic t : this.list)
		{
			if(t.getName().equals(titre))
			{
				return t;
			}
		}
		throw new RemoteException();
	}

	@Override
	public void proposeSujet(String titre)
			throws Exception {
		if(this.list.contains(titre))
		{
			throw new Exception("Topic already exists");
		}
		else
		{
			try{
				this.addTopic(new Topic(titre));
				System.out.println("Create new topic :"+titre);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

	}

	@Override
	public List<String> getTopicsTitle() throws RemoteException {
		List<String> topicsTitle = new ArrayList<String>();
		for(int i = 0; i < list.size(); i++){
			topicsTitle.add(list.get(i).getName());
		}

		return topicsTitle;
	}

	@Override
	public List<String> getUsersList(String topic) throws Exception {
		List localList = new ArrayList();
		for(int i = 0; i < list.size(); i++){
			if(list.get(i).getName().equals(topic))
			{
				localList.addAll(list.get(i).getClientList());
				return localList;
			}
		}
		throw new Exception("Topic doesn't exist");
	}

	public void addTopic(Topic t){
		list.add(t);
	}

	public String getTopicContent(String topic) throws Exception {
		for(int i = 0; i < list.size(); i++){
			if(list.get(i).getName().equals(topic))
			{
				return list.get(i).getLogsContent();
			}
		}
		throw new Exception("Topic doesn't exist");
	}

	@Override
	public List<String> getSubscriptionListForClient(InterfaceAffichageClient c)	throws RemoteException {
		List<String> listToReturn = new ArrayList<String>();
		//for each topic in topiclist
		//if topic.get(i).contains(clientName)
		//add topic.getName in return list
		for(int i = 0; i < list.size(); i++){
			if(list.get(i).getClientList().contains(c)){
				listToReturn.add(list.get(i).getName());
			}
		}
		return listToReturn;
	}


    private List<Topic> listLogsFile(){

    	List<Topic> list = new ArrayList<Topic>();
    	
        File directory = new File("logs");

        //get all the files from a directory
        File[] fList = directory.listFiles();

        for (File file : fList){
        	
            if (file.isFile() && file.getName().endsWith(".logs")){
                try {
                	//Add topic name from file without logs extension
					list.add(new Topic(file.getName().substring(0,file.getName().length()-5)));
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        }
        
        return list;

    }



}

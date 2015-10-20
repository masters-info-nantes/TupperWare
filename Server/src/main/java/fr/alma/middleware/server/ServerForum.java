package fr.alma.middleware.server;

import java.io.File;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import fr.alma.middleware.data.Topic;
import fr.alma.middleware.remote.InterfaceAffichageClient;
import fr.alma.middleware.remote.InterfaceServeurForum;
import fr.alma.middleware.remote.InterfaceSujetDiscussion;

public class ServerForum extends UnicastRemoteObject implements InterfaceServeurForum, Serializable {

	protected ServerForum() throws RemoteException {
		super();

		try {
			this.set = listLogsFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final long serialVersionUID = 1L;
	private Set<Topic> set = new HashSet<Topic>();

	@Override
	public InterfaceSujetDiscussion obtientSujet(String titre)
			throws RemoteException {
		for(Topic t : this.set)
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
		if(this.set.contains(titre))
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
		Iterator<Topic> iterator = set.iterator();
		while (iterator.hasNext()) {
			topicsTitle.add(iterator.next().getName());
		}

		return topicsTitle;
	}

	@Override
	public List<String> getUsersList(String topic) throws Exception {
		Object o;
		Iterator<Topic> iterator = set.iterator();
		while(iterator.hasNext()){
			Topic t = iterator.next();
			if(t.getName().equals(topic)){
				o = t.getClientList().toArray();
				return (List)o;
			}
		}
		throw new Exception("Topic doesn't exist");
	}

	public void addTopic(Topic t){
		set.add(t);
	}

	public String getTopicContent(String topic) throws Exception {
		
		Iterator<Topic> iterator = set.iterator();
		while (iterator.hasNext()) {
			Topic t = iterator.next();
			if(t.getName().equals(topic)){
				return t.getLogsContent();
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
		Iterator<Topic> iterator = set.iterator();
		while (iterator.hasNext()) {
			Topic t = iterator.next();
			if(t.getClientList().contains(c)){
				listToReturn.add(t.getName());
			}
		}
		
		return listToReturn;
	}


    private Set<Topic> listLogsFile(){

    	Set<Topic> set = new HashSet<Topic>();
    	
        File directory = new File("logs");

        //get all the files from a directory
        File[] fList = directory.listFiles();

        for (File file : fList){
        	
            if (file.isFile() && file.getName().endsWith(".logs")){
                try {
                	//Add topic name from file without logs extension
					set.add(new Topic(file.getName().substring(0,file.getName().length()-5)));
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        }
        
        return set;

    }



}

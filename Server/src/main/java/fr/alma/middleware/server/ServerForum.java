package fr.alma.middleware.server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import fr.alma.middleware.data.Topic;
import fr.alma.middleware.remote.InterfaceServeurForum;
import fr.alma.middleware.remote.InterfaceSujetDiscussion;

public class ServerForum extends UnicastRemoteObject implements InterfaceServeurForum, Serializable {

	protected ServerForum() throws RemoteException {
		super();
		
		try {
			this.list.add(new Topic("Musique"));
			this.list.add(new Topic("Cinema"));
			this.list.add(new Topic("Sport"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final long serialVersionUID = 1L;
	private List<Topic> list = new ArrayList<Topic>();
	
	@Override
	public InterfaceSujetDiscussion obtientSujet(String titre)
			throws RemoteException {
		return null;
	}

	@Override
	public void proposeSujet(String titre)
			throws RemoteException {
		if(this.list.contains(titre))
        {
            throw new Exception("Topic already exists");
        }
        else
        {
            try{
                this.addTopic(new Topic(titre));
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
			System.out.println(list.get(i).getName());
		}
		
		return topicsTitle;
	}

	@Override
	public List<String> getUsersList(String topic) throws RemoteException {
        List localList = new ArrayList();
		for(int i = 0; i < list.size(); i++){
            if(list.get(i).getName().equals(topic))
            {
                return localList.addAll(list.get(i).getClientList());
            }
        }
		throw new Exception("Topic doesn't exist");
	}
	
	public void addTopic(Topic t){
		list.add(t);
	}
    
    public String getTopicContent(String topic) throws RemoteException {
		for(int i = 0; i < list.size(); i++){
            if(list.get(i).getName().equals(topic))
            {
                return list.getLogsContent();
            }
        }
		throw new Exception("Topic doesn't exist");
        
    }


}

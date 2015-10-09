package fr.alma.middleware.server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import fr.alma.middleware.data.Client;
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
			throws RemoteException {
		if(this.list.contains(titre))
        {
            throw new RemoteException();
        }
        else
        {
            try{
                this.list.add(new Topic(titre));
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
		List<String> usersName = new ArrayList<String>();
		if(this.list.contains(topic)){
			for(Topic t : this.list){
				if(t.getName() == topic){
					for(Client c : t.getClientList()){
						usersName.add(c.getName());
						System.out.println(c.getName());
					}
				}
			}
		}else{
			throw new RemoteException();
		}
		return usersName;
	}
	
	public void addTopic(Topic t){
		list.add(t);
	}

}

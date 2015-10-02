package data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Set;

import fr.alma.middleware.remote.InterfaceAffichageClient;
import fr.alma.middleware.remote.InterfaceSujetDiscussion;

/* Plusieurs solutions pour la persistence:
 * 
 * 		Chaque client écrit dans le fichier de logs (affichage sur l'écran ensuite)
 *		
 */

public class Topic implements InterfaceSujetDiscussion{


	private String name;
	private Set<Client> clientList;
	private BufferedWriter fichier;
	private File file;

	public Topic(String name){
		this.name = name;
		this.clientList = new HashSet<Client>();
		this.file = new File(name + ".logs");

		try {
			fichier = new BufferedWriter(new FileWriter(name + ".logs"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public String getName(){
		return name;
	}


	public Set<Client> getClientList(){
		return clientList;
	}

	public boolean addClient(Client c){
		if(!clientList.contains(c)){
			clientList.add(c);
			return true;
		}
		else {
			return false;
		}
	}

	public void removeClient(String name){
		if(clientList.contains(name)){
			clientList.remove(name);
		}
	}


	public synchronized void writeLineInFile(String m){
		try {
			fichier.write(m);
			fichier.newLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void closeLogsFile(){
		try {
			fichier.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void inscription(InterfaceAffichageClient c) throws RemoteException {
		if(!clientList.contains(c)){
			clientList.add((Client) c);
		}
	}


	@Override
	public void desInscription(InterfaceAffichageClient c)
			throws RemoteException {
		if(clientList.contains((Client)c)){
			clientList.remove((Client)c);
		}
	}


	@Override
	public void diffuse(String message) throws RemoteException {
		synchronized(message){
			try {
				fichier.write(message);
				fichier.newLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


}

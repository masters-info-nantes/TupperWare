package fr.alma.middleware.data;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;
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

public class Topic extends UnicastRemoteObject implements InterfaceSujetDiscussion, Serializable {


	private String name;

	private BufferedWriter writer;
	private BufferedReader reader;
	private Set<InterfaceAffichageClient> clientList;

	private File file;

	public Topic(String name) throws Exception{
		this.name = name;
		this.clientList = new HashSet<InterfaceAffichageClient>();
        //Check if the log directory is present, if not, create it
        File dir = new File("logs");
        if (!dir.exists()) {
            try{
                dir.mkdir();
            }
            catch(SecurityException se){
                System.out.println("Error: Can't create the logs directory!");
            }       
        }
        //Open the log files or create them
		this.file = new File("logs/" + name + ".logs");

		try {
			writer = new BufferedWriter(new FileWriter("logs/" + name + ".logs"));
			reader = new BufferedReader(new FileReader("logs/" + name + ".logs"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    //Needed for the list.contains method
    public boolean equals(Topic obj)
    {
        if(obj.name.equals(this.name))
           return true;
        return false;
    }


	public String getName(){
		return name;
	}


	public Set<InterfaceAffichageClient> getClientList(){
		return clientList;
	}

	public boolean addClient(InterfaceAffichageClient c){
		if(!clientList.contains(c)){
			clientList.add(c);
			return true;
		}
		else {
			return false;
		}
	}

	public void removeClient(InterfaceAffichageClient c){
		if(clientList.contains(c)){
			clientList.remove(c);
		}
	}


	public synchronized void writeLineInFile(String m){
		try {
			writer.write(m);
			writer.newLine();
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            //if (reader != null)closeLogsFile();
		}
	}
    
    public String getLogsContent(){
        String result = "";
		try { 
			String sCurrentLine;

			while ((sCurrentLine = reader.readLine()) != null) {
				System.out.println(sCurrentLine);
                result += sCurrentLine;
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
            //if (reader != null)closeLogsFile();
		}
		return result;
	}

	public void closeLogsFile(){
		try {
			reader.close();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void inscription(InterfaceAffichageClient c) throws RemoteException {
		// TODO Auto-generated method stub
		this.addClient(c);
	}

	@Override
	public void desInscription(InterfaceAffichageClient c) throws RemoteException {
		// TODO Auto-generated method stub
		this.removeClient(c);
	}

	@Override
	public void diffuse(String message) throws RemoteException {
		this.writeLineInFile(message);
        System.out.println("Diffusé à: "+this.getClientList().size()+" client");
		for(InterfaceAffichageClient c : this.getClientList())
		{
			c.affiche(message,this.name);
		}
		System.out.println(message);
		
	}


	

}

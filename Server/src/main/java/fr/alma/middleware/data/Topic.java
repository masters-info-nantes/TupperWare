package fr.alma.middleware.data;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import fr.alma.middleware.remote.InterfaceAffichageClient;

/* Plusieurs solutions pour la persistence:
 * 
 * 		Chaque client écrit dans le fichier de logs (affichage sur l'écran ensuite)
 *		
 */

public class Topic {


	private String name;
	private Set<Client> clientList;
	private BufferedWriter writer;
	private BufferedReader reader;
	private File file;

	public Topic(String name) throws Exception{
		this.name = name;
		this.clientList = new HashSet<Client>();
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

	public void removeClient(InterfaceAffichageClient c){
		if(clientList.contains(c)){
			clientList.remove(c);
		}
	}


	public synchronized void writeLineInFile(String m){
		try {
			writer.write(m);
			writer.newLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)closeLogsFile();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
    
    public String getLogsContent() throws RemoteException {

		try {
            String result = "";
			String sCurrentLine;

			while ((sCurrentLine = reader.readLine()) != null) {
				System.out.println(sCurrentLine);
                result += sCurrentLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)closeLogsFile();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
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


	

}

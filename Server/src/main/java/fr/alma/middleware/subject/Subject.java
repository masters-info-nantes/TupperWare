package fr.alma.middleware.subject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javafx.scene.control.TextArea;

/* Plusieurs solutions pour la persistence:
 * 
 * 		Chaque client écrit dans le fichier de logs (affichage sur l'écran ensuite)
 *		
 */

public class Subject {

	
	private String name;
	private Set<String> clientList;
	private BufferedWriter fichier;
	private TextArea textArea;
	private File file;
	
	public Subject(String name){
		this.name = name;
		this.clientList = new HashSet<String>();
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
	
	
	public Set<String> getClientList(){
		return clientList;
	}
	
	public boolean addClient(String name){
		if(!clientList.contains(name)){
			clientList.add(name);
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
	
	
	public synchronized void writeLineInFile(Message m){
		try {
			fichier.write(m.toString());
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
	
	
}

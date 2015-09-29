package fr.alma.middleware.subject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

public class Subject {

	
	private String name;
	private Set<String> clientList;
	private PrintWriter pWriter;
	
	public Subject(String name){
		this.name = name;
		this.clientList = new HashSet<String>();
		
		try {
			pWriter = new PrintWriter(name+".logs", "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
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
	
	
	public void writeLineInFile(String s){
		pWriter.println(s);
	}
	
	public void closeLogsFile(){
		pWriter.close();
	}
	
	
}

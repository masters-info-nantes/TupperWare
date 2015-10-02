package fr.alma.middleware.data;

import java.util.Date;

public class Message {

	private String clientPseudo;
	private Date dateMessage;
	private String messageContent;
	private Topic subject;
	
	
	public String toString(){
		return "[" + dateMessage + "] <" + clientPseudo + " : " + messageContent;
		
	}
	
	
}

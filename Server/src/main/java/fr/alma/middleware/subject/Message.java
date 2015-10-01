package fr.alma.middleware.subject;

import java.util.Date;

public class Message {

	private String clientPseudo;
	private Date dateMessage;
	private String messageContent;
	private Subject subject;
	
	
	public String toString(){
		return "[" + dateMessage + "] <" + clientPseudo + " : " + messageContent;
		
	}
	
	
}

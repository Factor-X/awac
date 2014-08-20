/*
 *
 * Instant Play Framework
 * EasyChat
 *                       
 *
 * Copyright (c) 2013/2014 RIMSHOT ITS SPRL.
 * Author Gaston Hollands
 *
 */

package eu.factorx.awac.util.email.messages;

public class EmailMessage {
    
	final String toAddress;
	final String subject;
	final String content;
	
	public EmailMessage (String to, String subject, String content) {
        this.toAddress = to;
        this.subject = subject;
        this.content = content;
    }
		
    public String getType() {
    	return "email";
    }
    
    public String getToAddress() {
		return toAddress;
	}
    
	public String getSubject() {
		return subject;
	}

	public String getContent() {
		return content;
	}    
}
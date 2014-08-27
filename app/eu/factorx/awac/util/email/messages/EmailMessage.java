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

import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.code.type.LanguageCode;

public class EmailMessage {

	private String toAddress;
	private String subject;
	private String content;

	public EmailMessage(String toAddress, String subject, String content) {
		this.toAddress = toAddress;
		this.subject = subject;
		this.content = content;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "EmailMessage{" +
				"toAddress='" + toAddress + '\'' +
				", subject='" + subject + '\'' +
				", content='" + content + '\'' +
				'}';
	}
}
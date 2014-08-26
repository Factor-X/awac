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
	private InterfaceTypeCode fromInterface = null;
	private LanguageCode languageCode = null;

	public EmailMessage(String toAddress, String subject, String content) {
		this.toAddress = toAddress;
		this.subject = subject;
		this.content = content;
	}

	public EmailMessage(String toAddress, String subject, String content, InterfaceTypeCode fromInterface, LanguageCode languageCode) {
		this.toAddress = toAddress;
		this.subject = subject;
		this.content = content;
		this.fromInterface = fromInterface;
		this.languageCode = languageCode;
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

	public InterfaceTypeCode getFromInterface() {
		return fromInterface;
	}

	public LanguageCode getLanguageCode() {
		return languageCode;
	}
}
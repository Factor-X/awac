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

import java.util.List;

public class EmailMessage {

	private String toAddress;
	private String subject;
	private String content;
	private List<String> attachmentFilenameList;

	public EmailMessage(String toAddress, String subject, String content) {
		this.toAddress = toAddress;
		this.subject = subject;
		this.content = content;
		this.attachmentFilenameList=null;
	}

	public EmailMessage(String toAddress, String subject, String content,List<String> attachmentList) {
		this.toAddress = toAddress;
		this.subject = subject;
		this.content = content;
		this.attachmentFilenameList = attachmentList;
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


	public List<String> getAttachmentFilenameList() {
		return attachmentFilenameList;
	}

	public void setAttachmentFilenameList(List<String> attachmentFilenameList) {
		this.attachmentFilenameList = attachmentFilenameList;
	}

	@Override
	public String toString() {
		return "EmailMessage{" +
				"toAddress='" + toAddress + '\'' +
				", subject='" + subject + '\'' +
				", content='" + content + '\'' +
				", attachmentFilenameList=" + attachmentFilenameList +
				'}';
	}


}
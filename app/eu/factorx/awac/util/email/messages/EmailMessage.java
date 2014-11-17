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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EmailMessage {

	private List<String> toAddress;
	private String subject;
	private String content;
	private HashMap<String, ByteArrayOutputStream> attachmentFilenameList;
    private HashMap<String, ByteArrayInputStream> byteArrayinputStreamList;

	public EmailMessage(String toAddress, String subject, String content) {
		this.toAddress = new ArrayList<>();
        this.toAddress.add(toAddress);
		this.subject = subject;
		this.content = content;
		this.attachmentFilenameList=null;
	}

    public EmailMessage(List<String> toAddress, String subject, String content) {
        this.toAddress = toAddress;
        this.subject = subject;
        this.content = content;
    }

    public EmailMessage(String toAddress, String subject, String content,HashMap<String, ByteArrayOutputStream> attachmentList) {
        this.toAddress = new ArrayList<>();
        this.toAddress.add(toAddress);
		this.subject = subject;
		this.content = content;
		this.attachmentFilenameList = attachmentList;
	}

    public EmailMessage(String test, String toAddress, String subject, String content,HashMap<String, ByteArrayInputStream> attachmentList) {
        this.toAddress = new ArrayList<>();
        this.toAddress.add(toAddress);
        this.subject = subject;
        this.content = content;
        this.byteArrayinputStreamList = attachmentList;
    }



    public void addEmails(List<String> admins) {
        toAddress.addAll(admins);
    }

    public List<String> getToAddress() {
        return toAddress;
    }

    public void setToAddress(List<String> toAddress) {
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

    public HashMap<String, ByteArrayOutputStream> getAttachmentFilenameList() {
        return attachmentFilenameList;
    }

    public void setAttachmentFilenameList(HashMap<String, ByteArrayOutputStream> attachmentFilenameList) {
        this.attachmentFilenameList = attachmentFilenameList;
    }

    public HashMap<String, ByteArrayInputStream> getByteArrayinputStreamList () {
        return byteArrayinputStreamList;
    }

    public void setByteArrayinputStreamList (HashMap<String, ByteArrayInputStream> byteArrayinputStreamList) {
        this.byteArrayinputStreamList = byteArrayinputStreamList;
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
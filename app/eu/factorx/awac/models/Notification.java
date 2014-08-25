package eu.factorx.awac.models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.joda.time.DateTime;

@Entity
public class Notification extends AuditedAbstractEntity {

	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	private NotificationKind kind;
	private String messageFr;
	private String messageNl;
	private String messageEn;
	private DateTime since;
	private DateTime until;

	public Notification() {
	}

	public NotificationKind getKind() {
		return kind;
	}

	public void setKind(NotificationKind kind) {
		this.kind = kind;
	}

	public String getMessageFr() {
		return messageFr;
	}

	public void setMessageFr(String messageFr) {
		this.messageFr = messageFr;
	}

	public String getMessageNl() {
		return messageNl;
	}

	public void setMessageNl(String messageNl) {
		this.messageNl = messageNl;
	}

	public String getMessageEn() {
		return messageEn;
	}

	public void setMessageEn(String messageEn) {
		this.messageEn = messageEn;
	}

	public DateTime getSince() {
		return since;
	}

	public void setSince(DateTime since) {
		this.since = since;
	}

	public DateTime getUntil() {
		return until;
	}

	public void setUntil(DateTime until) {
		this.until = until;
	}
}

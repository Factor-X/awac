package eu.factorx.awac.dto.myrmex.get;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.models.NotificationKind;
import org.joda.time.DateTime;

public class NotificationDTO extends DTO {

	private NotificationKind kind;
	private String messageFr;
	private String messageNl;
	private String messageEn;
	private DateTime since;
	private DateTime until;
	private DateTime creation;

	public NotificationDTO() {
		kind = NotificationKind.INFO;
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

	public DateTime getCreation() {
		return creation;
	}

	public void setCreation(DateTime creation) {
		this.creation = creation;
	}
}

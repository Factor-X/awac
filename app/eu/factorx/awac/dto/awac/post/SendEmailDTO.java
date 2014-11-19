package eu.factorx.awac.dto.awac.post;

import eu.factorx.awac.dto.DTO;

/**
 * Created by florian on 19/11/14.
 */
public class SendEmailDTO extends DTO {

	private String calculatorKey;
	private boolean onlyOrganizationSharedData;
	private String emailTitle;
	private String emailContent;

	public SendEmailDTO() {
	}

	public String getCalculatorKey() {
		return calculatorKey;
	}

	public void setCalculatorKey(String calculatorKey) {
		this.calculatorKey = calculatorKey;
	}

	public boolean isOnlyOrganizationSharedData() {
		return onlyOrganizationSharedData;
	}

	public void setOnlyOrganizationSharedData(boolean onlyOrganizationSharedData) {
		this.onlyOrganizationSharedData = onlyOrganizationSharedData;
	}

	public String getEmailTitle() {
		return emailTitle;
	}

	public void setEmailTitle(String emailTitle) {
		this.emailTitle = emailTitle;
	}

	public String getEmailContent() {
		return emailContent;
	}

	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}

	@Override
	public String toString() {
		return "SendEmailDTO{" +
				"emailContent='" + emailContent + '\'' +
				", emailTitle='" + emailTitle + '\'' +
				", onlyOrganizationSharedData=" + onlyOrganizationSharedData +
				", calculatorKey='" + calculatorKey + '\'' +
				'}';
	}
}

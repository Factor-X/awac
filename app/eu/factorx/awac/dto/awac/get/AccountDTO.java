package eu.factorx.awac.dto.awac.get;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.myrmex.get.PersonDTO;
import eu.factorx.awac.models.account.Person;
import eu.factorx.awac.models.business.Organization;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import play.data.validation.Constraints;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

public class AccountDTO extends DTO {


	private OrganizationDTO organization;
	private PersonDTO person;
	private String identifier;
	private String password;
	private Boolean active = Boolean.TRUE;
	private Boolean needChangePassword = Boolean.FALSE;
	private InterfaceTypeCode interfaceCode;
	private Boolean isAdmin = false;

	public OrganizationDTO getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationDTO organization) {
		this.organization = organization;
	}

	public PersonDTO getPerson() {
		return person;
	}

	public void setPerson(PersonDTO person) {
		this.person = person;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getNeedChangePassword() {
		return needChangePassword;
	}

	public void setNeedChangePassword(Boolean needChangePassword) {
		this.needChangePassword = needChangePassword;
	}

	public InterfaceTypeCode getInterfaceCode() {
		return interfaceCode;
	}

	public void setInterfaceCode(InterfaceTypeCode interfaceCode) {
		this.interfaceCode = interfaceCode;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
}

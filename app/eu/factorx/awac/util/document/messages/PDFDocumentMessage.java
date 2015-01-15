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

package eu.factorx.awac.util.document.messages;

import eu.factorx.awac.dto.awac.get.DownloadFileDTO;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.code.type.LanguageCode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.*;

public class PDFDocumentMessage {


	private String periodKey;
	private String comparedPeriodKey;
	private InterfaceTypeCode interfaceCode;
	private LanguageCode lang;
	private List<Scope> scopes;
	private Map<String, Double> typicalResultValues;
	private Map<String, Double> idealResultValues;
	private DownloadFileDTO generatedDTO;
	private String UUID;

	public String getPeriodKey() {
		return periodKey;
	}

	public void setPeriodKey(String periodKey) {
		this.periodKey = periodKey;
	}

	public String getComparedPeriodKey() {
		return comparedPeriodKey;
	}

	public void setComparedPeriodKey(String comparedPeriodKey) {
		this.comparedPeriodKey = comparedPeriodKey;
	}

	public InterfaceTypeCode getInterfaceCode() {
		return interfaceCode;
	}

	public void setInterfaceCode(InterfaceTypeCode interfaceCode) {
		this.interfaceCode = interfaceCode;
	}

	public LanguageCode getLang() {
		return lang;
	}

	public void setLang(LanguageCode lang) {
		this.lang = lang;
	}

	public List<Scope> getScopes() {
		return scopes;
	}

	public void setScopes(List<Scope> scopes) {
		this.scopes = scopes;
	}

	public Map<String, Double> getTypicalResultValues() {
		return typicalResultValues;
	}

	public void setTypicalResultValues(Map<String, Double> typicalResultValues) {
		this.typicalResultValues = typicalResultValues;
	}

	public Map<String, Double> getIdealResultValues() {
		return idealResultValues;
	}

	public void setIdealResultValues(Map<String, Double> idealResultValues) {
		this.idealResultValues = idealResultValues;
	}

	public DownloadFileDTO getGeneratedDTO() {
		return generatedDTO;
	}

	public void setGeneratedDTO(DownloadFileDTO generatedDTO) {
		this.generatedDTO = generatedDTO;
	}

	public void setUUID(String UUID) {
		this.UUID = UUID;
	}

	public String getUUID() {
		return UUID;
	}
}

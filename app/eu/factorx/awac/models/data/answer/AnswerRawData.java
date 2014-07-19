package eu.factorx.awac.models.data.answer;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AnswerRawData implements Serializable {

	private static final long serialVersionUID = 1L;

	private String stringData1 = null;

	private String stringData2 = null;

	private Boolean booleanData = null;

	private Long longData = null;

	private Double doubleData = null;

	public AnswerRawData() {
		super();
	}

	public AnswerRawData(String stringData1, String stringData2, Boolean booleanData, Long longData, Double doubleData) {
		super();
		this.stringData1 = stringData1;
		this.stringData2 = stringData2;
		this.booleanData = booleanData;
		this.longData = longData;
		this.doubleData = doubleData;
	}

	public AnswerRawData(String stringData1) {
		super();
		this.stringData1 = stringData1;
	}

	public AnswerRawData(String stringData1, String stringData2) {
		super();
		this.stringData1 = stringData1;
		this.stringData2 = stringData2;
	}

	public AnswerRawData(Boolean booleanData) {
		super();
		this.booleanData = booleanData;
	}

	public AnswerRawData(Long longData) {
		super();
		this.longData = longData;
	}

	public AnswerRawData(Double doubleData) {
		super();
		this.doubleData = doubleData;
	}

	public String getStringData1() {
		return stringData1;
	}

	public void setStringData1(String stringData1) {
		this.stringData1 = stringData1;
	}

	public String getStringData2() {
		return stringData2;
	}

	public void setStringData2(String stringData2) {
		this.stringData2 = stringData2;
	}

	public Boolean getBooleanData() {
		return booleanData;
	}

	public void setBooleanData(Boolean booleanData) {
		this.booleanData = booleanData;
	}

	public Long getLongData() {
		return longData;
	}

	public void setLongData(Long longData) {
		this.longData = longData;
	}

	public Double getDoubleData() {
		return doubleData;
	}

	public void setDoubleData(Double doubleData) {
		this.doubleData = doubleData;
	}

}

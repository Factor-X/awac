package eu.factorx.awac.models.code.conversion;

import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.type.ActivityTypeCode;

import javax.persistence.Basic;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = CodeToActivityTypeCodeEquivalence.FIND_BY_CODE,
		query = "select eq.activityTypeCode from CodeToActivityTypeCodeEquivalence eq where eq.codeList = :codeList and eq.codeKey = :codeKey")
public class CodeToActivityTypeCodeEquivalence extends CodesEquivalence {

	public static final String FIND_BY_CODE = "CodeToActivityTypeCodeEquivalence.findByCode";
	private static final long serialVersionUID = 1L;
	@Embedded
	@Basic(optional = false)
	private ActivityTypeCode activityTypeCode;

	public CodeToActivityTypeCodeEquivalence() {
		super();
	}

	/**
	 * @param activityTypeCode
	 */
	public CodeToActivityTypeCodeEquivalence(CodeList codeList, String codeKey, ActivityTypeCode activityTypeCode) {
		super();
		this.codeList = codeList;
		this.codeKey = codeKey;
		this.activityTypeCode = activityTypeCode;
	}

	public ActivityTypeCode getActivityTypeCode() {
		return activityTypeCode;
	}

	public void setActivityTypeCode(ActivityTypeCode activityTypeCode) {
		this.activityTypeCode = activityTypeCode;
	}

}

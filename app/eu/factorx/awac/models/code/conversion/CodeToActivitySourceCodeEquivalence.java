package eu.factorx.awac.models.code.conversion;

import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.type.ActivitySourceCode;

import javax.persistence.Basic;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = CodeToActivitySourceCodeEquivalence.FIND_BY_CODE,
		query = "select eq.activitySourceCode from CodeToActivitySourceCodeEquivalence eq where eq.codeList = :codeList and eq.codeKey = :codeKey")
public class CodeToActivitySourceCodeEquivalence extends CodesEquivalence {

	public static final String FIND_BY_CODE = "CodeToActivitySourceCodeEquivalence.findByCode";
	private static final long serialVersionUID = 1L;
	@Embedded
	@Basic(optional = false)
	private ActivitySourceCode activitySourceCode;

	public CodeToActivitySourceCodeEquivalence() {
		super();
	}

	/**
	 * @param activitySourceCode
	 */
	public CodeToActivitySourceCodeEquivalence(CodeList codeList, String codeKey, ActivitySourceCode activitySourceCode) {
		super();
		this.codeList = codeList;
		this.codeKey = codeKey;
		this.activitySourceCode = activitySourceCode;
	}

	public ActivitySourceCode getActivitySourceCode() {
		return activitySourceCode;
	}

	public void setActivitySourceCode(ActivitySourceCode activitySourceCode) {
		this.activitySourceCode = activitySourceCode;
	}

}

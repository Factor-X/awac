package eu.factorx.awac.models.code.conversion;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import eu.factorx.awac.models.AbstractEntity;
import eu.factorx.awac.models.code.CodeList;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class CodesEquivalence extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	@Basic(optional = false)
	protected CodeList codeList;

	@Basic(optional = false)
	protected String codeKey;

	protected CodesEquivalence() {
		super();
	}

	/**
	 * @param codeList
	 * @param codeKey
	 */
	protected CodesEquivalence(CodeList codeList, String codeKey) {
		super();
		this.codeList = codeList;
		this.codeKey = codeKey;
	}

	public CodeList getCodeList() {
		return codeList;
	}

	public void setCodeList(CodeList codeList) {
		this.codeList = codeList;
	}

	public String getCodeKey() {
		return codeKey;
	}

	public void setCodeKey(String codeKey) {
		this.codeKey = codeKey;
	}

}
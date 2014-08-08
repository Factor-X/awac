package eu.factorx.awac.models.code.conversion;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import eu.factorx.awac.models.AbstractEntity;
import eu.factorx.awac.models.code.CodeList;

@Entity
@NamedQueries({
		@NamedQuery(name = CodesEquivalence.FIND_ALL_SUBLISTS_DATA,
				query = "select ce from CodesEquivalence ce where ce.codeKey = ce.referencedCodeKey order by ce.id"),
		@NamedQuery(name = CodesEquivalence.FIND_BY_CODE_AND_TARGET_CODELIST,
				query = "select eq from CodesEquivalence eq where eq.codeList = :codeList and eq.codeKey = :codeKey and eq.referencedCodeList = :targetCodeList") })
public class CodesEquivalence extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	public static final String FIND_ALL_SUBLISTS_DATA = "CodesEquivalence.findAllSublistsData";

	/**
	 * @param codeList: a {@link CodeList}
	 * @param codeKey: a {@link String}
	 * @param targetCodeList: a {@link CodeList}
	 */
	public static final String FIND_BY_CODE_AND_TARGET_CODELIST = "CodesEquivalence.findByCodeAndTargetCodeList";

	@Enumerated(EnumType.STRING)
	@Basic(optional = false)
	protected CodeList codeList;

	@Basic(optional = false)
	protected String codeKey;

	@Enumerated(EnumType.STRING)
	@Basic(optional = false)
	protected CodeList referencedCodeList;

	@Basic(optional = false)
	protected String referencedCodeKey;

	protected CodesEquivalence() {
		super();
	}

	/**
	 * @param codeList
	 * @param codeKey
	 * @param referencedCodeList
	 * @param referencedCodeKey
	 */
	public CodesEquivalence(CodeList codeList, String codeKey, CodeList referencedCodeList, String referencedCodeKey) {
		super();
		this.codeList = codeList;
		this.codeKey = codeKey;
		this.referencedCodeList = referencedCodeList;
		this.referencedCodeKey = referencedCodeKey;
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

	public CodeList getReferencedCodeList() {
		return referencedCodeList;
	}

	public void setReferencedCodeList(CodeList referencedCodeList) {
		this.referencedCodeList = referencedCodeList;
	}

	public String getReferencedCodeKey() {
		return referencedCodeKey;
	}

	public void setReferencedCodeKey(String referencedCodeKey) {
		this.referencedCodeKey = referencedCodeKey;
	}

	@Override
	public String toString() {
		return "CodesEquivalence [codeList=" + codeList + ", codeKey=" + codeKey + ", referencedCodeList=" + referencedCodeList
				+ ", referencedCodeKey=" + referencedCodeKey + "]";
	}

}

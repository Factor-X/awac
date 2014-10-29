package eu.factorx.awac.models.code.conversion;

import javax.persistence.*;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;

@Entity
@NamedQueries({
		@NamedQuery(name = CodesEquivalence.FIND_ALL_SUBLISTS_DATA,
				query = "select ce from CodesEquivalence ce where ce.codeKey = ce.referencedCodeKey order by ce.id"),
		@NamedQuery(name = CodesEquivalence.FIND_SUBLIST_CODE_LABELS,
				query = "select new eu.factorx.awac.models.code.label.CodeLabel(ce.codeList, ce.codeKey, cl.labelEn, cl.labelFr, cl.labelNl) from CodesEquivalence ce, CodeLabel cl where ce.referencedCodeList = cl.codeList and ce.referencedCodeKey = cl.key and ce.codeList = :codeList order by ce.id",
				hints = {@QueryHint(name = "org.hibernate.cacheable", value = "true")}),
		@NamedQuery(name = CodesEquivalence.FIND_BY_CODE_AND_TARGET_CODELIST,
				query = "select eq from CodesEquivalence eq where eq.codeList = :codeList and eq.codeKey = :codeKey and eq.referencedCodeList = :referencedCodeList"),
		@NamedQuery(name = CodesEquivalence.COUNT_SUBLIST_EQUIVALENCES,
				query = "select count(ce.id) from CodesEquivalence ce where ce.codeKey = ce.referencedCodeKey and ce.codeList = :codeList and ce.referencedCodeList = :referencedCodeList"),
		@NamedQuery(name = CodesEquivalence.REMOVE_ALL, query = "delete from CodesEquivalence ce where ce.id is not null"),
})
public class CodesEquivalence extends AuditedAbstractEntity {

	private static final long serialVersionUID = 1L;

	public static final String FIND_ALL_SUBLISTS_DATA = "CodesEquivalence.findAllSublistsData";

	/**
	 * @param codeList: a {@link CodeList}
	 * @param codeKey: a {@link String}
	 * @param referencedCodeList: a {@link CodeList}
	 */
	public static final String FIND_BY_CODE_AND_TARGET_CODELIST = "CodesEquivalence.findByCodeAndTargetCodeList";

	/**
	 * Select all equivalences existing for given <code>codeList</code> parameter, and returns the list of linked {@link CodeLabel}s.
	 * 
	 * @param codeList: a {@link CodeList}
	 * 
	 */
	public static final String FIND_SUBLIST_CODE_LABELS = "CodesEquivalence.findCodeLabelsBySublist";

	public static final String COUNT_SUBLIST_EQUIVALENCES = "CodesEquivalence.countSublistEquivalences";

	public static final String REMOVE_ALL = "CodesEquivalence.removeAll";

	public static final String CODE_LIST_PROPERTY_NAME = "codeList";
	public static final String CODE_KEY_PROPERTY_NAME = "codeKey";
	public static final String REFERENCED_CODE_LIST_PROPERTY_NAME = "referencedCodeList";

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

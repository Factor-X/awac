package eu.factorx.awac.models.code.conversion;

import javax.persistence.*;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import org.hibernate.annotations.QueryHints;

@Entity
@NamedQueries({
		@NamedQuery(name = CodesEquivalence.FIND_ALL_SUBLISTS_DATA,
				query = "select ce from CodesEquivalence ce where ce.codeKey = ce.referencedCodeKey order by ce.orderIndex"),
		@NamedQuery(name = CodesEquivalence.FIND_SUBLIST_CODE_LABELS,
				query = "select new eu.factorx.awac.models.code.label.CodeLabel(ce.codeList, ce.codeKey, cl.labelEn, cl.labelFr, cl.labelNl, ce.orderIndex) from CodesEquivalence ce, CodeLabel cl where ce.referencedCodeList = cl.codeList and ce.referencedCodeKey = cl.key and ce.codeList = :codeList order by ce.orderIndex",
				hints = {@QueryHint(name = QueryHints.CACHEABLE, value = "true")}),
		@NamedQuery(name = CodesEquivalence.FIND_BY_CODE_AND_TARGET_CODELIST,
				query = "select eq from CodesEquivalence eq where eq.codeList = :codeList and eq.codeKey = :codeKey and eq.referencedCodeList = :referencedCodeList"),
		@NamedQuery(name = CodesEquivalence.COUNT_SUBLIST_EQUIVALENCES,
				query = "select count(ce.id) from CodesEquivalence ce where ce.codeKey = ce.referencedCodeKey and ce.codeList = :codeList and ce.referencedCodeList = :referencedCodeList"),
		@NamedQuery(name = CodesEquivalence.COUNT_LINKED_LIST_EQUIVALENCES,
				query = "select count(ce.id) from CodesEquivalence ce where ce.codeList = :codeList and ce.codeKey <> ce.referencedCodeKey"),
		@NamedQuery(name = CodesEquivalence.FIND_ALL_LINKED_LISTS_DATA,
				query = "select ce from CodesEquivalence ce where ce.codeKey <> ce.referencedCodeKey"),
		@NamedQuery(name = CodesEquivalence.REMOVE_ALL, query = "delete from CodesEquivalence ce where ce.id is not null")
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

	public static final String COUNT_LINKED_LIST_EQUIVALENCES = "CodesEquivalence.countLinkedListEquivalences";

	public static final String FIND_ALL_LINKED_LISTS_DATA = "CodesEquivalence.findAllLinkedListsData";

	public static final String CODE_LIST_PROPERTY_NAME = "codeList";
	public static final String CODE_KEY_PROPERTY_NAME = "codeKey";
	public static final String REFERENCED_CODE_LIST_PROPERTY_NAME = "referencedCodeList";

	@Enumerated(EnumType.STRING)
	@Basic(optional = false)
	protected CodeList codeList;

	@Basic(optional = false)
	protected String codeKey;

	@Basic(optional = true)
	protected Integer orderIndex;

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

	/**
	 * @param codeList
	 * @param codeKey
	 * @param orderIndex
	 * @param referencedCodeList
	 * @param referencedCodeKey
	 */
	public CodesEquivalence(CodeList codeList, String codeKey, Integer orderIndex, CodeList referencedCodeList, String referencedCodeKey) {
		super();
		this.codeList = codeList;
		this.codeKey = codeKey;
		this.orderIndex = orderIndex;
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

	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		CodesEquivalence that = (CodesEquivalence) o;

		if (!codeKey.equals(that.codeKey)) return false;
		if (codeList != that.codeList) return false;
		if (orderIndex != null ? !orderIndex.equals(that.orderIndex) : that.orderIndex != null) return false;
		if (!referencedCodeKey.equals(that.referencedCodeKey)) return false;
		if (referencedCodeList != that.referencedCodeList) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + codeList.hashCode();
		result = 31 * result + codeKey.hashCode();
		result = 31 * result + (orderIndex != null ? orderIndex.hashCode() : 0);
		result = 31 * result + referencedCodeList.hashCode();
		result = 31 * result + referencedCodeKey.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "CodesEquivalence [codeList=" + codeList + ", codeKey=" + codeKey + ", referencedCodeList=" + referencedCodeList
				+ ", referencedCodeKey=" + referencedCodeKey + "]";
	}

}

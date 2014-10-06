package eu.factorx.awac.models.code.conversion;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.code.CodeList;

import javax.persistence.*;

/**
 * This entity convert a code from a code list to an other code from the same list by the define criteria
 */
@Entity
@NamedQueries({
        @NamedQuery(name = CodeConversion.FIND_BY_CODE_LIST_AND_KEY_AND_CRITERION,
                query = "select cc from CodeConversion cc where cc.codeList = :codeList and cc.codeKey = :codeKey and cc.conversionCriterion = :conversionCriterion"),
        @NamedQuery(name = CodeConversion.REMOVE_ALL, query = "delete from CodeConversion cc where cc.id is not null"),


})
public class CodeConversion extends AuditedAbstractEntity {

	private static final long serialVersionUID = 1L;

	public final static String FIND_BY_CODE_LIST_AND_KEY_AND_CRITERION = "CodeConversion.findByCodeListAndKeyAndCriterion";
    public final static String REMOVE_ALL = "CodeConversion.removeAll";

    @Enumerated(EnumType.STRING)
    @Basic(optional = false)
    protected CodeList codeList;

    @Basic(optional = false)
    protected String codeKey;

    @Basic(optional = false)
    protected String referencedCodeKey;

    @Enumerated(EnumType.STRING)
    @Basic(optional = false)
    protected ConversionCriterion conversionCriterion;

    public CodeConversion() {
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

    public String getReferencedCodeKey() {
        return referencedCodeKey;
    }

    public void setReferencedCodeKey(String referencedCodeKey) {
        this.referencedCodeKey = referencedCodeKey;
    }

    public ConversionCriterion getConversionCriterion() {
        return conversionCriterion;
    }

    public void setConversionCriterion(ConversionCriterion conversionCriterion) {
        this.conversionCriterion = conversionCriterion;
    }



    @Override
    public String toString() {
        return "CodeConversion{" +
                "codeList=" + codeList +
                ", codeKey='" + codeKey + '\'' +
                ", referencedCodeKey='" + referencedCodeKey + '\'' +
                ", conversionCriteria=" + conversionCriterion +
                '}';
    }
}

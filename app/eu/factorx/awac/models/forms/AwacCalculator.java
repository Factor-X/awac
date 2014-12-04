package eu.factorx.awac.models.forms;

import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import eu.factorx.awac.models.AuditedAbstractEntity;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.knowledge.Report;

@Entity
@Table(name = "awaccalculator")
@NamedQueries({
        @NamedQuery(name = AwacCalculator.FIND_BY_CODE, query = "select p from AwacCalculator p where p.interfaceTypeCode = :interfaceTypeCode" )
})
public class AwacCalculator extends AuditedAbstractEntity {

	private static final long serialVersionUID = 1L;

	public static final String INTERFACE_TYPE_CODE_PROPERTY = "interfaceTypeCode";
    public static final String FIND_BY_CODE = "AwacCalculator_FIND_BY_KEY";

    @Embedded
	@AttributeOverrides({ @AttributeOverride(name = "key", column = @Column(name = "code")) })
	@Column(unique = true, nullable = false)
	private InterfaceTypeCode interfaceTypeCode;

	@OneToMany(mappedBy = "awacCalculator", cascade = { CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH })
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Report> reports;

    @OneToMany(mappedBy = "awacCalculator", cascade = { CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH })
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Form> forms;


    @Column(name = "fr_enabled")
    private boolean frEnabled;

    @Column(name = "nl_enabled")
    private boolean nlEnabled;

    @Column(name = "en_enabled")
    private boolean enEnabled;



	public AwacCalculator() {
		super();
	}

	/**
	 * @param interfaceTypeCode
	 */
	public AwacCalculator(InterfaceTypeCode interfaceTypeCode) {
		super();
		this.interfaceTypeCode = interfaceTypeCode;
	}

    public List<Form> getForms() {
        return forms;
    }

    public void setForms(List<Form> forms) {
        this.forms = forms;
    }

    public InterfaceTypeCode getInterfaceTypeCode() {
		return interfaceTypeCode;
	}

	public void setInterfaceTypeCode(InterfaceTypeCode interfaceTypeCode) {
		this.interfaceTypeCode = interfaceTypeCode;
	}

	public List<Report> getReports() {
		return reports;
	}

	public void setReports(List<Report> reports) {
		this.reports = reports;
	}


    public boolean isFrEnabled() {
        return frEnabled;
    }

    public void setFrEnabled(boolean frEnabled) {
        this.frEnabled = frEnabled;
    }

    public boolean isNlEnabled() {
        return nlEnabled;
    }

    public void setNlEnabled(boolean nlEnabled) {
        this.nlEnabled = nlEnabled;
    }

    public boolean isEnEnabled() {
        return enEnabled;
    }

    public void setEnEnabled(boolean enEnabled) {
        this.enEnabled = enEnabled;
    }
}

package eu.factorx.awac.models.data;

import eu.factorx.awac.models.AbstractEntity;
import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.forms.Form;
import eu.factorx.awac.models.knowledge.Period;

import javax.persistence.*;


@Entity
@NamedQueries({
		@NamedQuery(name = FormProgress.FIND_BY_PERIOD_AND_SCOPE, query = "select fp from FormProgress fp where fp.period in :period and fp.scope in :scope"),
		@NamedQuery(name = FormProgress.FIND_BY_PERIOD_AND_SCOPE_AND_FORM, query = "select fp from FormProgress fp where fp.period in :period and fp.scope in :scope and fp.form in :form")})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {FormProgress.PERIOD_COLUMN, FormProgress.FORM_COLUMN, FormProgress.SCOPE_COLUMN}))
public class FormProgress extends AbstractEntity {

	/**
	 * @param period : the period wanted
	 * @param scope : the scope wanted
	 */
	public static final String FIND_BY_PERIOD_AND_SCOPE = "FormProgress.findByPeriodAndScope";

	/**
	 * @param period : the period wanted
	 * @param scope : the scope wanted
	 * @param form : the form wanted
	 */
	public static final String FIND_BY_PERIOD_AND_SCOPE_AND_FORM = "FormProgress.findByPeriodAndScopeAndForm";

	//list name of the column
	public static final String PERIOD_COLUMN = "period";
	public static final String SCOPE_COLUMN = "scope";
	public static final String FORM_COLUMN = "form";

	@ManyToOne
	@JoinColumn(name=FormProgress.PERIOD_COLUMN)
	private Period period;

	@ManyToOne
	@JoinColumn(name=FormProgress.FORM_COLUMN)
	private Form form;

	@ManyToOne
	@JoinColumn(name=FormProgress.SCOPE_COLUMN)
	private Scope scope;


	private Integer percentage;

	public FormProgress() {
	}

	public FormProgress(Period period, Form form, Scope scope, Integer percentage) {
		this.period = period;
		this.form = form;
		this.scope = scope;
		this.percentage = percentage;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public Form getForm() {
		return form;
	}

	public void setForm(Form form) {
		this.form = form;
	}

	public Scope getScope() {
		return scope;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}

	public Integer getPercentage() {
		return percentage;
	}

	public void setPercentage(Integer percentage) {
		this.percentage = percentage;
	}

	@Override
	public String toString() {
		return "FormProgress{" +
				"period=" + period +
				", form=" + form +
				", scope=" + scope +
				", percentage=" + percentage +
				'}';
	}
}

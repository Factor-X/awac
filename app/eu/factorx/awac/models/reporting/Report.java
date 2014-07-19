package eu.factorx.awac.models.reporting;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.List;

@MappedSuperclass
public class Report implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<BaseActivityResult> activityResults;

	protected Report() {
		super();
	}

	public Report(List<BaseActivityResult> activityResults) {
		super();
		this.activityResults = activityResults;
	}

	public List<BaseActivityResult> getActivityResults() {
		return activityResults;
	}

	public void setActivityResults(List<BaseActivityResult> activityResults) {
		this.activityResults = activityResults;
	}

}
package eu.factorx.awac.models.reporting;

import java.io.Serializable;
import java.util.List;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Report implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<ActivityResult> activityResults;

	protected Report() {
		super();
	}

	public Report(List<ActivityResult> activityResults) {
		super();
		this.activityResults = activityResults;
	}

	public List<ActivityResult> getActivityResults() {
		return activityResults;
	}

	public void setActivityResults(List<ActivityResult> activityResults) {
		this.activityResults = activityResults;
	}

}
package eu.factorx.awac.service.impl.reporting;

/**
 * This warning is reported when it exist a better method to evaluate the result of a specific activity (alternativeGroupKey), eg when <code>minRank != 1</code>
 */
public class LowRankMeasureWarning extends ReportLogEntry {

	private String alternativeGroupKey;

	public LowRankMeasureWarning() {
	}

	public LowRankMeasureWarning(String alternativeGroupKey) {
		this.alternativeGroupKey = alternativeGroupKey;
	}

	public String getAlternativeGroupKey() {
		return alternativeGroupKey;
	}

	public void setAlternativeGroupKey(String alternativeGroupKey) {
		this.alternativeGroupKey = alternativeGroupKey;
	}

	@Override
	public String toString() {
		return "LowRankMeasureWarning {" + "alternativeGroupKey='" + alternativeGroupKey + '\'' + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		LowRankMeasureWarning that = (LowRankMeasureWarning) o;

		if (!alternativeGroupKey.equals(that.alternativeGroupKey))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = alternativeGroupKey.hashCode();
		return result;
	}
}

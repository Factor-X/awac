package eu.factorx.awac.service.impl.reporting;

/**
 * This warning is reported when it exist a better method to evaluate the result of a specific activity (alternativeGroupKey), eg when <code></code>minRank != 1</code>
 */
public class LowRankMeasureWarning extends ReportLogEntry {

	private String alternativeGroupKey;

	private Integer minRank;

	public LowRankMeasureWarning() {
	}

	public LowRankMeasureWarning(String alternativeGroupKey, Integer minRank) {
		this.alternativeGroupKey = alternativeGroupKey;
		this.minRank = minRank;
	}

	public String getAlternativeGroupKey() {
		return alternativeGroupKey;
	}

	public void setAlternativeGroupKey(String alternativeGroupKey) {
		this.alternativeGroupKey = alternativeGroupKey;
	}

	public Integer getMinRank() {
		return minRank;
	}

	public void setMinRank(Integer minRank) {
		this.minRank = minRank;
	}

	@Override
	public String toString() {
		return "LowRankMeasureWarning {" +
				"alternativeGroupKey='" + alternativeGroupKey + '\'' +
				", minRank=" + minRank +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		LowRankMeasureWarning that = (LowRankMeasureWarning) o;

		if (!alternativeGroupKey.equals(that.alternativeGroupKey)) return false;
		if (!minRank.equals(that.minRank)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = alternativeGroupKey.hashCode();
		result = 31 * result + minRank.hashCode();
		return result;
	}
}

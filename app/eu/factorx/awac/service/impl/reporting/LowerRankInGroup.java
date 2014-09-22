package eu.factorx.awac.service.impl.reporting;

public class LowerRankInGroup extends ReportLogEntry {
	private final String  key;
	private final Integer rank;
	private final String  alternativeGroup;
	private final Integer minRank;

	public LowerRankInGroup(String key, Integer rank, String alternativeGroup, Integer minRank) {

		this.key = key;
		this.rank = rank;
		this.alternativeGroup = alternativeGroup;
		this.minRank = minRank;
	}

	public String getKey() {
		return key;
	}

	public Integer getRank() {
		return rank;
	}

	public String getAlternativeGroup() {
		return alternativeGroup;
	}

	public Integer getMinRank() {
		return minRank;
	}
}

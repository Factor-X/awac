package eu.factorx.awac.dto.awac.get;

public class LowerRankInGroupDTO extends ReportLogEntryDTO {
	private String  key;
	private String  alternativeGroup;
	private Integer minRank;
	private Integer rank;

	public LowerRankInGroupDTO() {
	}

	public LowerRankInGroupDTO(
		String key,
		String alternativeGroup,
		Integer minRank,
		Integer rank
	) {
		this.key = key;
		this.alternativeGroup = alternativeGroup;
		this.minRank = minRank;
		this.rank = rank;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getAlternativeGroup() {
		return alternativeGroup;
	}

	public void setAlternativeGroup(String alternativeGroup) {
		this.alternativeGroup = alternativeGroup;
	}

	public Integer getMinRank() {
		return minRank;
	}

	public void setMinRank(Integer minRank) {
		this.minRank = minRank;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}
}

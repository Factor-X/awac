package eu.factorx.awac.util.data.importer.badImporter.Reader;

/**
 * Created by florian on 1/09/14.
 */
public class DataCell {

	private int col;

	private int row;

	private String content;

	public DataCell(int col, int row, String content) {
		this.col = col;
		this.row = row;
		this.content = content;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "DataCell{" +
				"col=" + col +
				", row=" + row +
				", content='" + content + '\'' +
				'}';
	}
}

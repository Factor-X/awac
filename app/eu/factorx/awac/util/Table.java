package eu.factorx.awac.util;

import java.util.HashMap;
import java.util.Map;

public class Table {

	private Map<String, Object> cells;
	private int                 rowCount;
	private int                 columnCount;

	public Table() {
		cells = new HashMap<>();
		rowCount = 0;
		columnCount = 0;
	}

	public void setCell(int column, int row, Object value) {

		if (column < 0) throw new IllegalArgumentException("column shoud be >= 0");
		if (row < 0) throw new IllegalArgumentException("row shoud be >= 0");

		String key = String.format("%d:%d", column, row);
		if (value == null) {
			if (cells.containsKey(key)) {
				cells.remove(key);

				int rows = 0;
				int columns = 0;
				for (String s : cells.keySet()) {
					String[] parts = s.split(":");
					int c = Integer.parseInt(parts[0]);
					int r = Integer.parseInt(parts[1]);

					if (c >= columns) columns = c + 1;
					if (r >= rows) rows = r + 1;
				}

				rowCount = rows;
				columnCount = columns;

			}
		} else {
			cells.put(key, value);

			if (column >= columnCount) columnCount = column + 1;
			if (row >= rowCount) rowCount = row + 1;
		}

	}


	public Object getCell(int column, int row) {

		if (column < 0) throw new IllegalArgumentException("column shoud be >= 0");
		if (row < 0) throw new IllegalArgumentException("row shoud be >= 0");

		String key = String.format("%d:%d", column, row);
		if (cells.containsKey(key)) {
			return cells.get(key);
		} else {
			return null;
		}

	}


	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}
}

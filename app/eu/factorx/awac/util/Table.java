package eu.factorx.awac.util;

import org.apache.commons.lang3.StringUtils;

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

        if (column < 0) {
            throw new IllegalArgumentException("column shoud be >= 0");
        }
        if (row < 0) {
            throw new IllegalArgumentException("row shoud be >= 0");
        }

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

                    if (c >= columns) {
                        columns = c + 1;
                    }
                    if (r >= rows) {
                        rows = r + 1;
                    }
                }

                rowCount = rows;
                columnCount = columns;

            }
        } else {
            cells.put(key, value);

            if (column >= columnCount) {
                columnCount = column + 1;
            }
            if (row >= rowCount) {
                rowCount = row + 1;
            }
        }

    }


    public Object getCell(int column, int row) {

        if (column < 0) {
            throw new IllegalArgumentException("column shoud be >= 0");
        }
        if (row < 0) {
            throw new IllegalArgumentException("row shoud be >= 0");
        }

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

    public double sum(int x1, int y1, int x2, int y2) {

        double result = 0.0;

        for (int y = y1; y <= y2; y++) {
            for (int x = x1; x <= x2; x++) {
                Object v = getCell(x, y);
                if (v != null && v instanceof Double) {
                    result += (Double) v;
                }
            }
        }

        return result;
    }

    public double max(int x1, int y1, int x2, int y2) {

        double result = Double.NEGATIVE_INFINITY;

        for (int y = y1; y <= y2; y++) {
            for (int x = x1; x <= x2; x++) {
                Object v = getCell(x, y);
                if (v != null && v instanceof Double && (Double) v > result) {
                    result = (Double) v;
                }
            }
        }

        return result;
    }

    public String dump() {
        StringBuilder sb = new StringBuilder();

        int[] sizes = new int[columnCount];

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                String str = String.valueOf(getCell(j, i));
                if (str.length() > sizes[j]) {
                    sizes[j] = str.length();
                }
            }
        }

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                String str = String.valueOf(getCell(j, i));
                str = StringUtils.leftPad(str, sizes[j]);
                if (j > 0) {
                    sb.append(" | ");
                }
                sb.append(str);
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}

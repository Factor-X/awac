package eu.factorx.awac.util.data.importer.badImporter.Reader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florian on 1/09/14.
 */
public class Data {

	private List<DataCell> listData = new ArrayList<>();

	private int nbRows=0;

	private int nbCols=0;


	public Data(List<DataCell> listData) {
		this.listData = listData;
		for(DataCell dataCell : listData){

			if(dataCell.getCol()>nbCols){
				nbCols = dataCell.getCol();
			}

			if(dataCell.getRow()>nbRows){
				nbRows = dataCell.getRow();
			}
		}

	}

	public String getData(int col,int row){
		for(DataCell dataCell : listData){
			if(dataCell.getCol() == col && dataCell.getRow() == row){
				return dataCell.getContent();
			}
		}
		return null;
	}

	public int getNbRows() {
		return nbRows;
	}

	public int getNbCols() {
		return nbCols;
	}

	@Override
	public String toString() {
		return "Data{" +
				"listData=" + listData +
				", nbRows=" + nbRows +
				", nbCols=" + nbCols +
				'}';
	}
}

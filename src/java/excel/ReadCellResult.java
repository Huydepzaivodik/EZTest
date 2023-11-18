package excel;

import excel.converter.ConvertError;
import excel.validator.ConstrainsError;

@SuppressWarnings("unused")
public class ReadCellResult {

	private ConvertError convertError;
	private ConstrainsError constrainsError;
	private Object cellValue;
	private int rowIndex;
	private int colIndex;

	public ConvertError getConvertError() {
		return convertError;
	}

	public void setConvertError(ConvertError convertError) {
		this.convertError = convertError;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public int getColIndex() {
		return colIndex;
	}

	public void setColIndex(int colIndex) {
		this.colIndex = colIndex;
	}

	public Object getCellValue() {
		return cellValue;
	}

	public void setCellValue(Object cellValue) {
		this.cellValue = cellValue;
	}

	public ConstrainsError getConstrainsError() {
		return constrainsError;
	}

	public void setConstrainsError(ConstrainsError constrainsError) {
		this.constrainsError = constrainsError;
	}

	public ReadCellResult constrainsError(ConstrainsError constrainsError) {
		setConstrainsError(constrainsError);
		return this;
	}

	public ReadCellResult cellValue(Object cellValue) {
		setCellValue(cellValue);
		return this;
	}

	public ReadCellResult rowIndex(int rowIndex) {
		setRowIndex(rowIndex);
		return this;
	}

	public ReadCellResult colIndex(int colIndex) {
		setColIndex(colIndex);
		return this;
	}

	@Override
	public String toString() {
		return "ReadCellResult{" +
				"convertError=" + convertError +
				", constrainsError=" + constrainsError +
				", cellValue=" + cellValue +
				", rowIndex=" + rowIndex +
				", colIndex=" + colIndex +
				'}';
	}

	public ReadCellResult convertError(ConvertError convertError) {
		setConvertError(convertError);
		return this;
	}
}

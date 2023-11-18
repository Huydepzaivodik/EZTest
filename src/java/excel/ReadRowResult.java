package excel;

import java.util.Arrays;

@SuppressWarnings("unused")
public class ReadRowResult {

	private ReadCellResult[] readCellResults;

	public ReadCellResult[] getReadCellResults() {
		return readCellResults;
	}

	public void setReadCellResults(ReadCellResult[] readCellResults) {
		this.readCellResults = readCellResults;
	}

	public ReadRowResult readCellResults(ReadCellResult[] readCellResults) {
		setReadCellResults(readCellResults);
		return this;
	}

	@Override
	public String toString() {
		return "ReadRowResult{" +
				"readCellResults=" + Arrays.toString(readCellResults) +
				'}';
	}
}

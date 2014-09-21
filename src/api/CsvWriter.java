package api;

import java.util.List;

public interface CsvWriter {
	public void writeData(Object data);

	public void updateFile(List<Object> accounts) throws Exception;
}

package jlotto.dbutils;
import javax.swing.JTable;


public class DBResultTable extends JTable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance of DBResultTable and creates a new JTable <br></br>
	 * by calling its constructor method with the given parameters
	 * <p></p>
	 * @param rowData the row content of each column of the Table
	 * @param columnNames the header ( column ) names of the Table
	 */
	public DBResultTable(Object[][] rowData, Object[] columnNames) {
		/* call JTable constructor to create a JTable */
		super(rowData, columnNames);
		super.setAutoResizeMode(AUTO_RESIZE_NEXT_COLUMN);
		super.setRowSelectionAllowed(false);
		super.setAutoCreateRowSorter(true);
	}
	
	
}

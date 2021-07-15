package ui;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

//model for time table
public class TimeTableModel extends DefaultTableModel {
    public TimeTableModel(String[] columnNames, int row) {
        super(columnNames, row);
    }

    public String getStatus(int row, int col) {
        System.out.println(((Vector) dataVector.get(row)).get(col));
        return (String) ((Vector) dataVector.get(row)).get(col);
    }
}

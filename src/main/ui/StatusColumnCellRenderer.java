package ui;


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Objects;


//Code for renderer based on: https://stackoverflow.com/questions/5673430/java-jtable-change-cell-color
//Cell renderer for time table
public class StatusColumnCellRenderer extends DefaultTableCellRenderer {
    @SuppressWarnings("checkstyle:EmptyBlock")
    @Override
    public Component getTableCellRendererComponent(JTable table,
                                                   Object value,
                                                   boolean isSelected,
                                                   boolean hasFocus,
                                                   int row,
                                                   int col) {

        //Cells are by default rendered as a JLabel.
        JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

        //Get the status for the current row.
        TimeTableModel tableModel = (TimeTableModel) table.getModel();
        if (Objects.equals(tableModel.getStatus(row, col), "Completed")) {
            l.setBackground(Color.GREEN);
        } else if (Objects.equals(tableModel.getStatus(row, col), "Incomplete")) {
            l.setBackground(Color.RED);
        } else {
            l.setBackground(Color.WHITE);
        }

        //Return the JLabel which renders the cell.
        return l;

    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.roman.greed.gui.pane.structure;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 *
 * @author Roman
 *
 */
public class ColumnFilter extends JScrollPane {

    public ColumnFilter() {
        init();
    }

    public void init() {
        DefaultTableModel tableModel = new DefaultTableModel(new Object[0][0], new Object[0]);
        RowSorter<DefaultTableModel> rowSorter = new TableRowSorter<DefaultTableModel>(tableModel);
        JTable jtable = new JTable(tableModel) {

            @Override
            protected JTableHeader createDefaultTableHeader() {
                return new JTableHeader(columnModel) {

                    @Override
                    public String getToolTipText(MouseEvent e) {
                        Point p = new Point(e.getX(), e.getY());
                        int index = columnModel.getColumnIndexAtX(p.x);
                        int realIndex = columnModel.getColumn(index).getModelIndex();
                        return "tra tra tra " + realIndex;
                    }
                };
            }
        };

        jtable.setRowSorter(rowSorter);
        this.setViewportView(jtable);
        jtable.getColumn("fg\n 123").setHeaderRenderer(new WithFilterHeaderRenderer(jtable));

    }

    public class WithFilterHeaderRenderer extends JScrollPane implements TableCellRenderer {

        private JTable subTable;
        private String caption;

        WithFilterHeaderRenderer(JTable table) {

            caption = table.getColumnName(0);
            subTable = new JTable(new Object[][]{}, new Object[]{caption, ""});
            subTable.getColumnModel().getColumn(1).setMaxWidth(10);
            subTable.setPreferredScrollableViewportSize(new Dimension(0, 0));

            this.setViewportView(subTable);
            this.setVisible(true);
        }

        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            caption = "123";
            if (isSelected) {
                subTable.setPreferredScrollableViewportSize(new Dimension(100, 100));
            } else {
                subTable.setPreferredScrollableViewportSize(new Dimension(0, 0));
            }
            subTable.findComponentAt(12, 12);
            return this;
        }
    }
}

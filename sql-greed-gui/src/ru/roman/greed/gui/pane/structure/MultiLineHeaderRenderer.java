package ru.roman.greed.gui.pane.structure;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * User: Roman
 * DateTime: 02.09.12 12:47
 */
public class MultiLineHeaderRenderer extends JPanel implements TableCellRenderer {

    private int verticalAlignment;
    private int horizontalAlignment;
    private float alignmentX;
    // These attributes may be explicitly set
    // They are defaulted to the colors and attributes
    // of the table header
    private Color foreground;
    private Color background;
    // These attributes have fixed defaults
    private Border headerBorder = UIManager.getBorder("TableHeader.cellBorder");
    private Font font = UIManager.getFont("TableHeader.font");


    public MultiLineHeaderRenderer(int horizontalAlignment,
                                   int verticalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
        this.verticalAlignment = verticalAlignment;
        switch (horizontalAlignment) {
            case SwingConstants.LEFT:
                alignmentX = (float) 0.0;
                break;

            case SwingConstants.CENTER:
                alignmentX = (float) 0.5;
                break;

            case SwingConstants.RIGHT:
                alignmentX = (float) 1.0;
                break;

            default:
                throw new IllegalArgumentException(
                        "Illegal horizontal alignment value");
        }
        setBorder(headerBorder);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(true);

        background = null;
    }

    // Implementation of TableCellRenderer interface
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        removeAll();
        invalidate();

        if (value == null) {
            // Do nothing if no value
            return this;
        }

        // Set the foreground and background colors
        // from the table header if they are not set
        if (table != null) {
            JTableHeader header = table.getTableHeader();
            if (header != null) {
                if (foreground == null) {
                    super.setForeground(header.getForeground());
                }

                if (background == null) {
                    super.setBackground(header.getBackground());
                }
            }
        }

        if (verticalAlignment != SwingConstants.TOP) {
            add(Box.createVerticalGlue());
        }

        Object[] values;
        int length;
        if (value instanceof Object[]) {
            // Input is an array - use it
            values = (Object[]) value;
        } else {
            // Not an array - turn it into one
            values = new Object[1];
            values[0] = value;
        }
        length = values.length;

        // Configure each row of the header using
        // a separate JLabel. If a given row is
        // a JComponent, add it directly..
        for (int i = 0; i < length; i++) {
            Object thisRow = values[i];

            if (thisRow instanceof JComponent) {
                add((JComponent) thisRow);
            } else {
                JLabel l = new JLabel();
                setValue(l, thisRow, i);
                add(l);
            }
        }

        if (verticalAlignment != SwingConstants.BOTTOM) {
            add(Box.createVerticalGlue());
        }
        return this;
    }
    // Configures a label for one line of the header.
    // This can be overridden by derived classes

    protected void setValue(JLabel l, Object value, int lineNumber) {
        if (value != null && value instanceof Icon) {
            l.setIcon((Icon) value);
        } else {
            l.setText(value == null ? "" : value.toString());
        }
        l.setHorizontalAlignment(horizontalAlignment);
        l.setAlignmentX(alignmentX);
        l.setOpaque(false);
        l.setForeground(foreground);
        l.setFont(font);
    }
}

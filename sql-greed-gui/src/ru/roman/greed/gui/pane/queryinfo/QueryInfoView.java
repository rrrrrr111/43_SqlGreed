/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * QueryInfoView.java
 *
 * Created on 05.09.2009, 10:04:59
 */
package ru.roman.greed.gui.pane.queryinfo;

import ru.roman.greed.gui.common.ListTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
 * @author дтоь
 */
public class QueryInfoView extends javax.swing.JFrame {

    private static final String[] TYPES_TABLE_COLUMNS = new String[]{
            "Label", "Type", "DisplaySize", "FullName", "ClassName",
            "TypeName", "CatalogName", "Precision", "Scale",
            "AutoIncrement", "CaseSensitive",
            "Currency","DefinitelyWritable","Nullable","ReadOnly",
            "Searchable","Signed","Writable"
            };
    private static final String[] PROPERTIES_TABLE_COLUMNS = new String[]{
            "Property", "Value" };
    private final ListTableModel<Object> columnInfoModel = new ListTableModel<Object>(TYPES_TABLE_COLUMNS);
    private final ListTableModel<String> cursorInfoModel = new ListTableModel<String>(PROPERTIES_TABLE_COLUMNS);
    private final ListTableModel<String> connInfoModel = new ListTableModel<String>(PROPERTIES_TABLE_COLUMNS);;

    public QueryInfoView() {
        initComponents();
    }

    public void setData(List<Object[]> columnInfo, List<String[]> cursorInfo, List<String[]> connectionInfo, String headerText) {
        this.setTitle(headerText);

        columnInfoModel.setData(columnInfo);
        cursorInfoModel.setData(cursorInfo);
        connInfoModel.setData(connectionInfo);
    }


    public void onDataChanged() {
        columnInfoModel.fireTableDataChanged();
        cursorInfoModel.fireTableDataChanged();
        connInfoModel.fireTableDataChanged();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        GridBagConstraints gbc1;

        jSplitPane1 = new JSplitPane();
        jSplitPane2 = new JSplitPane();
        jPanel2 = new JPanel();
        jLabel2 = new JLabel();
        jScrollPane3 = new JScrollPane();
        jTable3 = new JTable();
        jPanel1 = new JPanel();
        jLabel1 = new JLabel();
        jScrollPane2 = new JScrollPane();
        jTable2 = new JTable();
        jScrollPane1 = new JScrollPane();
        jTable1 = new JTable();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(new Rectangle(0, 0, 0, 0));
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        setLocationByPlatform(true);
        getContentPane().setLayout(new GridBagLayout());

        jSplitPane1.setDividerSize(7);
        jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setName("jSplitPane1"); // NOI18N
        jSplitPane1.setOneTouchExpandable(true);

        jSplitPane2.setDividerLocation(401);
        jSplitPane2.setDividerSize(7);
        jSplitPane2.setName("jSplitPane2"); // NOI18N
        jSplitPane2.setOneTouchExpandable(true);

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setLayout(new BoxLayout(jPanel2, BoxLayout.PAGE_AXIS));

        jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel2.setText("Cursor information");
        jLabel2.setHorizontalTextPosition(SwingConstants.CENTER);
        jLabel2.setName("jLabel2"); // NOI18N
        jPanel2.add(jLabel2);

        jScrollPane3.setName("jScrollPane3"); // NOI18N
        jScrollPane3.setPreferredSize(new Dimension(300, 200));

        jTable3.setName("jTable3"); // NOI18N
        jTable3.setModel(cursorInfoModel);
        jTable3.setCellSelectionEnabled(true);

        jScrollPane3.setViewportView(jTable3);

        jPanel2.add(jScrollPane3);

        jSplitPane2.setLeftComponent(jPanel2);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new BoxLayout(jPanel1, BoxLayout.PAGE_AXIS));

        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setText("Connection information");
        jLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
        jLabel1.setName("jLabel1"); // NOI18N
        jPanel1.add(jLabel1);

        jScrollPane2.setName("jScrollPane2"); // NOI18N
        jScrollPane2.setPreferredSize(new Dimension(300, 200));

        jTable2.setName("jTable2"); // NOI18N
        jTable2.setModel(connInfoModel);
        jTable2.setCellSelectionEnabled(true);

        jScrollPane2.setViewportView(jTable2);
        jPanel1.add(jScrollPane2);
        jSplitPane2.setRightComponent(jPanel1);
        jSplitPane1.setBottomComponent(jSplitPane2);

        jScrollPane1.setName("jScrollPane1"); // NOI18N
        jScrollPane1.setPreferredSize(new Dimension(800, 200));

        jTable1.setName("jTable1"); // NOI18N
        jTable1.setModel(columnInfoModel);
        jTable1.setCellSelectionEnabled(true);

        jScrollPane1.setViewportView(jTable1);
        jSplitPane1.setTopComponent(jScrollPane1);

        gbc1 = new GridBagConstraints();
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.fill = GridBagConstraints.BOTH;
        gbc1.weightx = 1.0;
        gbc1.weighty = 1.0;
        getContentPane().add(jSplitPane1, gbc1);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    JLabel jLabel1;
    JLabel jLabel2;
    JPanel jPanel1;
    JPanel jPanel2;
    JScrollPane jScrollPane1;
    JScrollPane jScrollPane2;
    JScrollPane jScrollPane3;
    JSplitPane jSplitPane1;
    JSplitPane jSplitPane2;
    JTable jTable1;
    JTable jTable2;
    JTable jTable3;
    // End of variables declaration//GEN-END:variables
}


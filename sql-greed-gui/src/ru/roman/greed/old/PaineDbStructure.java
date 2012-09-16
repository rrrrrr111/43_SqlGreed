/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PaineDbStructure.java
 *
 * Created on 18.10.2009, 0:54:42
 */
package ru.roman.greed.old;

import ru.roman.greed.gui.pane.conf.ConfigManager;
import ru.roman.greed.old.spec.DynamicTree;
import ru.roman.greed.old.spec.SpecialTableNode;
import ru.roman.greed.service.ServiceHolder;
import ru.roman.greed.service.sql.StatementExecutionService;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Roman
 */
public class PaineDbStructure extends javax.swing.JFrame {

    private ConfigManager configManager = ConfigManager.getInstance();
    private ArrayList<String[]> mainList = new ArrayList<String[]>();


    private StatementExecutionService stmExecService = ServiceHolder.getStmExecService();

    public PaineDbStructure() {
        initComponents();
        MyTree = new DynamicTree();
        jPanel1.add(MyTree);
        MainRigthGreed = new RightGreed();
        jTabbedPane1.addTab("MainRigthGreed", MainRigthGreed);

        mainList.add(new String[]{"RootMenu"});
        mainList.add(new String[]{"Shemas"});
        mainList.add(new String[]{"TableTypes"});
        mainList.add(new String[]{"Catalogs"});
        mainList.add(new String[]{"TypeInfo"});
        mainList.add(new String[]{"ClientInfoProperties"});
        MyTree.populateTreeByListS(null, mainList);
        mainList.clear();
        addListeners();
    }

    public void addListeners() {
        MyTree.tree.addTreeSelectionListener(new TreeSelectionListener() {

            public void valueChanged(TreeSelectionEvent e) {
                SpecialTableNode ActiveNoad = (SpecialTableNode) e.getPath().getLastPathComponent();
                if (ActiveNoad.isLeaf()) {
                    populateMyTreeNode(ActiveNoad);
                    }
                MainRigthGreed.setData(ActiveNoad.getData());
            }
        });
    }

    private void populateMyTreeNode(SpecialTableNode node) {
        for (; !node.isLeaf();) {
            ((SpecialTableNode) node.getFirstChild()).removeFromParent();
        }
        try {
            if (node.getLevel() == 1) {
                if (node.toString().equals("Shemas")) {
                    mainList = stmExecService.getSchemas();
                }
                if (node.toString().equals("TableTypes")) {
                    mainList = stmExecService.getTableTypes();
                }
                if (node.toString().equals("Catalogs")) {
                    mainList = stmExecService.getCatalogs();
                }
                if (node.toString().equals("TypeInfo")) {
                    mainList = stmExecService.getTypeInfo();
                }
                if (node.toString().equals("ClientInfoProperties")) {
                    mainList = stmExecService.getClientInfoProperties();
                }
                MyTree.populateTreeByListS(node, mainList);
            }
            if (node.getLevel() == 2) {
                if (node.getParent().toString().equals("Shemas")) {
                    mainList.add(new String[]{node.toString()});
                    mainList.add(new String[]{"Tables"});
                    mainList.add(new String[]{"SuperTables"});
                    mainList.add(new String[]{"Functions"});
                    mainList.add(new String[]{"Procedures"});
                    mainList.add(new String[]{"UDTs"});
                    mainList.add(new String[]{"SuperTypes"});
                    mainList.add(new String[]{"TablePrivileges"});
                    mainList.add(new String[]{"Attributes"});
                    mainList.add(new String[]{"Tables rows counts"});
                    MyTree.populateTreeByListS(node, mainList);
                }
            }
            if (node.getLevel() == 3) {
                if (node.toString().equals("Tables")) {
                    mainList = stmExecService.getTables(node.getParent().toString());
                }
                if (node.toString().equals("SuperTables")) {
                    mainList = stmExecService.getSuperTables(node.getParent().toString());
                }
                if (node.toString().equals("Functions")) {
                    mainList = stmExecService.getFunctions(node.getParent().toString());
                }
                if (node.toString().equals("Procedures")) {
                    mainList = stmExecService.getProcedures(node.getParent().toString());
                }
                if (node.toString().equals("UDTs")) {
                    mainList = stmExecService.getUDTs(node.getParent().toString());
                }
                if (node.toString().equals("SuperTypes")) {
                    mainList = stmExecService.getSuperTypes(node.getParent().toString());
                }
                if (node.toString().equals("TablePrivileges")) {
                    mainList = stmExecService.getTablePrivileges(node.getParent().toString());
                }
                if (node.toString().equals("Attributes")) {
                    mainList = stmExecService.getAttributes(node.getParent().toString());
                }
                if (node.toString().equals("Tables rows counts")) {
                    mainList = stmExecService.getTablesRowCounts(node.getParent().toString());
                }
                MyTree.populateTreeByListS(node, mainList);
            }
            if (node.getLevel() == 4) {
                if (node.getParent().toString().equals("Tables")) {
                    mainList.add(new String[]{node.toString()});
                    mainList.add(new String[]{"VersionColumns"});
                    mainList.add(new String[]{"Columns"});
                    mainList.add(new String[]{"ColumnPrivileges"});
                    //mainList.add(new String[]{"CrossReferences"});
                    mainList.add(new String[]{"ExportedKeys"});
                    mainList.add(new String[]{"ImportedKeys"});
                    mainList.add(new String[]{"PrimaryKeys"});
                    mainList.add(new String[]{"Indexes"});

                    MyTree.populateTreeByListS(node, mainList);
                }

            }
            if (node.getLevel() == 5) {
                if (node.toString().equals("VersionColumns")) {
                    mainList = stmExecService.getVersionColumns(node.getParent().toString());
                    MyTree.populateTreeByListS(node, mainList);
                    }
                if (node.toString().equals("Columns")) {
                    mainList = stmExecService.getColumns(node.getParent().toString());
                    MyTree.populateTreeByListS(node, mainList);
                    }
                if (node.toString().equals("ColumnPrivileges")) {
                    mainList = stmExecService.getColumnPrivileges(node.getParent().toString());
                    MyTree.populateTreeByListS(node, mainList);
                    }
                /*if (node.toString().equals("CrossReferences")) {
                mainList = stmExecService.getCrossReference(node.getParent().toString());
                MyTree.populateTreeByListS(node, mainList);
                }*/
                if (node.toString().equals("ExportedKeys")) {
                    mainList = stmExecService.getExportedKeys(node.getParent().toString());
                    MyTree.populateTreeByListS(node, mainList);
                    }
                if (node.toString().equals("ImportedKeys")) {
                    mainList = stmExecService.getImportedKeys(node.getParent().toString());
                    MyTree.populateTreeByListS(node, mainList);
                    }
                if (node.toString().equals("PrimaryKeys")) {
                    mainList = stmExecService.getPrimaryKeys(node.getParent().toString());
                    MyTree.populateTreeByListS(node, mainList);
                    }
                if (node.toString().equals("Indexes")) {
                    mainList = stmExecService.getIndexInfo(node.getParent().toString());
                    MyTree.populateTreeByListS(node, mainList);
                    }
            }

        } catch (SQLException e) {
            mainList.add(new String[]{"ERROR"});
            mainList.add(new String[]{e.toString()});
            MyTree.populateTreeByListS(node, mainList);
        } catch (IllegalAccessException e) {
            mainList.add(new String[]{"ERROR"});
            mainList.add(new String[]{e.toString()});
            MyTree.populateTreeByListS(node, mainList);
        } catch (ClassNotFoundException e) {
            mainList.add(new String[]{"ERROR"});
            mainList.add(new String[]{e.toString()});
            MyTree.populateTreeByListS(node, mainList);
        } catch (InstantiationException e) {
            mainList.add(new String[]{"ERROR"});
            mainList.add(new String[]{e.toString()});
            MyTree.populateTreeByListS(node, mainList);
        } catch (UnsupportedOperationException e) {
            mainList.add(new String[]{"ERROR"});
            mainList.add(new String[]{e.toString()});
            MyTree.populateTreeByListS(node, mainList);
        } finally {
            MyTree.treeModel.reload(node);
            mainList.clear();
        }
    }

    public class RightGreed extends JScrollPane {

        private JTable table;
        private OwnTableModel tableModel;
        private ArrayList<String[]> data = new ArrayList<String[]>();

        RightGreed() {
            super();
            this.data.add(new String[]{""});
            init();
        }

        void init() {
            tableModel = new OwnTableModel();
            table = new JTable(tableModel);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            this.setViewportView(table);
            table.setCellSelectionEnabled(true);
            //table.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()));
            //table.setCellEditor(new DefaultCellEditor(new JTextField()));
        }

        void setData(ArrayList<String[]> dt) {
            this.data = dt;
            tableModel.fireTableStructureChanged();
        }

        public class OwnTableModel extends AbstractTableModel {

            public int getRowCount() {
                return data.size() - 1;
            }

            public int getColumnCount() {
                return data.get(0).length;
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
                return data.get(rowIndex + 1)[columnIndex];
            }

            @Override
            public String getColumnName(int col) {
                return data.get(0)[col];
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new JSplitPane();
        jPanel1 = new JPanel();
        jTabbedPane1 = new JTabbedPane();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(configManager.getConnectionsModel().getDefaultConnection().getUrl());
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        setLocationByPlatform(true);

        jSplitPane1.setDividerLocation(300);
        jSplitPane1.setName("jSplitPane1"); // NOI18N

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new BorderLayout());
        jSplitPane1.setLeftComponent(jPanel1);

        jTabbedPane1.setName("jTabbedPane1"); // NOI18N
        jTabbedPane1.setPreferredSize(new Dimension(800, 400));
        jSplitPane1.setRightComponent(jTabbedPane1);

        getContentPane().add(jSplitPane1, BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JPanel jPanel1;
    private JSplitPane jSplitPane1;
    public JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
    public DynamicTree MyTree;
    public RightGreed MainRigthGreed;
}

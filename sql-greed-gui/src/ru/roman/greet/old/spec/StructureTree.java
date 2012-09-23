package ru.roman.greet.old.spec;

import ru.roman.greet.gui.common.DataTreeNode;
import ru.roman.greet.gui.pane.structure.StructureTreeModelListener;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.io.Serializable;
import java.util.List;

public class StructureTree extends JPanel {

    private DefaultTreeModel treeModel;
    private JTree tree;
    protected DataTreeNode rootNode;

    public StructureTree() {
        super(new GridLayout(1, 0));

        rootNode = new DataTreeNode("root");
        treeModel = new DefaultTreeModel(rootNode);
        treeModel.addTreeModelListener(new StructureTreeModelListener());
        tree = new JTree(treeModel);
        tree.setEditable(true);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);

        JScrollPane scrollPane = new JScrollPane(tree);
        add(scrollPane);
    }

    /** Remove all nodes except the root node. */
    public void clear() {
        rootNode.removeAllChildren();
        treeModel.reload();
    }

    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, String child) {
        return addObject(parent, child, true);
    }

    public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, String child, boolean shouldBeVisible) {
        DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
        if (parent == null) {
            parent = rootNode;
        }
        //It is key to invoke this on the TreeModel, and NOT DefaultMutableTreeNode
        treeModel.insertNodeInto(childNode, parent, parent.getChildCount());
        //Make sure the user can see the lovely new node.
        if (shouldBeVisible) {
            tree.scrollPathToVisible(new TreePath(childNode.getPath()));
        }
        return childNode;
    }




    public void populateTreeByList(DataTreeNode node, List<String[]> vec) {
        if (node == null) {
            node = rootNode;
        }
        if (vec.size() > 1) {

            String nodeName = node.toString();
            if (nodeName.equals("Tables") || nodeName.equals("Procedures")) {
                for (int i = 1; i < vec.size(); i++) {
                    addObject(node, vec.get(i)[2], vec);
                }
                return;
            }
            if (nodeName.equals("Columns")) {
                for (int i = 1; i < vec.size(); i++) {
                    addObject(node, vec.get(i)[3], vec);
                }
                return;
            }
            if (nodeName.equals("PrimaryKeys") || nodeName.equals("Indexes")) {
                for (int i = 1; i < vec.size(); i++) {
                    addObject(node, vec.get(i)[5], vec);
                }
                return;
            }
            if (nodeName.equals("ImportedKeys") || nodeName.equals("ExportedKeys")) {
                for (int i = 1; i < vec.size(); i++) {
                    addObject(node, vec.get(i)[12], vec);
                }
                return;
            }
            for (int i = 1; i < vec.size(); i++) {
                addObject(node, vec.get(i)[0], vec);
            }

        } else {
            addObject(node, "null", vec);
        }
    }

    public DataTreeNode addObject(DataTreeNode parent, String child, List<String[]> vec) {
        return addObject(parent, child, true, vec);
    }

    public DataTreeNode addObject(DataTreeNode parent, Serializable child,
                                      boolean shouldBeVisible, List<String[]> vec) {
        DataTreeNode childNode = new DataTreeNode(child);
        treeModel.insertNodeInto(childNode, parent, parent.getChildCount());
        parent.setData(vec);
        if (shouldBeVisible) {
            tree.scrollPathToVisible(new TreePath(childNode.getPath()));
        }
        return childNode;
    }

    public DefaultTreeModel getTreeModel() {
        return treeModel;
    }

    public JTree getTree() {
        return tree;
    }
}

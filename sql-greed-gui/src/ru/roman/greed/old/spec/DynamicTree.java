package ru.roman.greed.old.spec;

import ru.roman.greed.gui.pane.structure.StructureTreeModelListener;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class DynamicTree extends JPanel {

    public DefaultTreeModel treeModel;
    public JTree tree;
    protected DefaultMutableTreeNode rootNode;

    public DynamicTree() {
        super(new GridLayout(1, 0));

        rootNodeS = new SpecialTableNode("SpecNode");
        treeModel = new DefaultTreeModel(rootNodeS);
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

    protected SpecialTableNode rootNodeS;


    public void populateTreeByListS(SpecialTableNode node, ArrayList<String[]> vec) {
        if (node == null) {
            node = rootNodeS;
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

    public SpecialTableNode addObject(SpecialTableNode parent, String child, ArrayList<String[]> vec) {
        return addObject(parent, child, true, vec);
    }

    public SpecialTableNode addObject(SpecialTableNode parent, Serializable child,
                                      boolean shouldBeVisible, ArrayList<String[]> vec) {
        SpecialTableNode childNode = new SpecialTableNode(child);
        treeModel.insertNodeInto(childNode, parent, parent.getChildCount());
        parent.setData(vec.clone());
        if (shouldBeVisible) {
            tree.scrollPathToVisible(new TreePath(childNode.getPath()));
        }
        return childNode;
    }


}

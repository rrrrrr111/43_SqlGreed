/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.roman.greet.gui.common;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class DataTreeNode extends DefaultMutableTreeNode {

    public static final String[] EMPTY_STRINGS = new String[]{""};
    private List<String[]> data = new ArrayList<String[]>();

    public DataTreeNode() {
        super();
        this.data.add(EMPTY_STRINGS);
    }
    public DataTreeNode(Serializable userObject) {
        super(userObject);
        this.data.add(EMPTY_STRINGS);
    }
    public DataTreeNode(Object userObject, boolean allowsChildren) {
        super(userObject, allowsChildren);
        this.data.add(EMPTY_STRINGS);
    }

    public List<String[]> getData() {
        return data;
    }

    public void setData(List<String[]> data) {
        this.data = data;
    }
}

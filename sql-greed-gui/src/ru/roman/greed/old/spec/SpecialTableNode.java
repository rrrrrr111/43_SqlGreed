/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.roman.greed.old.spec;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.Serializable;
import java.util.ArrayList;


public class SpecialTableNode extends DefaultMutableTreeNode {

    private ArrayList<String[]> data = new ArrayList<String[]>();

    public SpecialTableNode() {
        super();
        this.data.add(new String[]{""});
    }
    public SpecialTableNode(Serializable userObject) {
        super(userObject);
        this.data.add(new String[]{""});
    }
    public SpecialTableNode(Object userObject, boolean allowsChildren) {
        super(userObject, allowsChildren);
        this.data.add(new String[]{""});
    }

    void setTableData(ArrayList<String[]> dt) {
        this.data = (ArrayList<String[]>) dt.clone();
    }

    public ArrayList<String[]> getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = (ArrayList<String[]>) data;
    }
}

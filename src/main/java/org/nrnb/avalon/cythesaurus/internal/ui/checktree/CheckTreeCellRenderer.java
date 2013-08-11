package org.nrnb.avalon.cythesaurus.internal.ui.checktree;

/**
 * MySwing: Advanced Swing Utilites Copyright (C) 2005 Santhosh Kumar T <p/> This library is free
 * software; you can redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation; either version 2.1 of the License,
 * or (at your option) any later version. <p/> This library is distributed in the hope that it will
 * be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 */

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

/**
 * @author Santhosh Kumar T
 * @email santhosh@in.fiorano.com
 */

public class CheckTreeCellRenderer extends JPanel implements TreeCellRenderer
{
    private CheckTreeSelectionModel selectionModel;
    private TreePathSelectable selectable;
    private TreeCellRenderer delegate;
    private TristateCheckBox checkBox;

    public CheckTreeCellRenderer(TreeCellRenderer delegate, CheckTreeSelectionModel selectionModel,
            TreePathSelectable selectable)
    {
        this.delegate = delegate;
        this.selectionModel = selectionModel;
        this.selectable = selectable;
        setLayout(new BorderLayout());
        setOpaque(false);
        setFont(((JComponent) delegate).getFont());
        checkBox = new TristateCheckBox();
        checkBox.setOpaque(false);
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
            boolean leaf, int row, boolean hasFocus)
    {
        Component renderer = delegate
                .getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

        TreePath path = tree.getPathForRow(row);
        if (path != null)
        {
            if (selectionModel.isPathSelected(path, selectionModel.isDigged()))
                checkBox.setState(Boolean.TRUE);
            else
                checkBox.setState(selectionModel.isDigged() && selectionModel.isPartiallySelected(path) ? null
                        : Boolean.FALSE);
        }
        removeAll();
        checkBox.setVisible(path == null || selectable == null || selectable.isSelectable(path));
        add(checkBox, BorderLayout.WEST);
        add(renderer, BorderLayout.CENTER);
        return this;
    }
}

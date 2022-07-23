package com.suai.familyTree.view;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;


public class TreeListener implements  VetoableChangeListener {

    FamilyView familyView;

    public TreeListener(FamilyView view) {
        this.familyView = view;
    }

    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        this.familyView.clearComponents();
        this.familyView.setComponents();
        this.familyView.repaint();
    }


}

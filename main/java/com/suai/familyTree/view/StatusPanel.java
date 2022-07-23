package com.suai.familyTree.view;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class StatusPanel extends JPanel{

    private final JLabel statusLabel;

    public StatusPanel(MainFrame mainFrame){

        setBorder(new BevelBorder(BevelBorder.LOWERED));
        this.statusLabel = new JLabel("Program loaded");

        //set size to the mainframe
        setPreferredSize(new Dimension(mainFrame.getWidth(), 18));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        //align text to the left
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        //this is where the status message will be displayed
        add(statusLabel);
    }

     void editStatus(String status) {
        statusLabel.setText(status);
    }

}

package com.suai.familyTree.view;

import com.suai.familyTree.model.Constants;
import com.suai.familyTree.model.FamilyTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class MainFrame extends JFrame {

    private FamilyTree familyTree;
    private HeaderPanel headerPanel;
    private StatusPanel statusPanel;
    private FamilyView familyView;
    private File currentFile = null;

    public MainFrame(String title) {
        super(title);
        this.familyTree = new FamilyTree();
        this.headerPanel = new HeaderPanel(this);
        this.statusPanel = new StatusPanel(this);
        this.familyView = new FamilyView(this.familyTree);

        setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
        //do nothing on close to give the user the option to save their work before quitting
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


        JScrollPane mainPanel = new JScrollPane(familyView);
        mainPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mainPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        add(headerPanel, BorderLayout.NORTH); // add header panel
        add(statusPanel, BorderLayout.SOUTH); // add status panel
        add(mainPanel, BorderLayout.CENTER);


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                if (checkUserContinue()) {
                    System.exit(0);
                }
            }
        });

        setVisible(true);

    }

    public FamilyTree getFamilyTree() { return this.familyTree; }

    public HeaderPanel getHeaderPanel() { return this.headerPanel; }

    public StatusPanel getStatusPanel() { return this.statusPanel; }

    public File getCurrentFile() { return this.currentFile; }
    public FamilyView getFamilyView(){ return this.familyView; }

    public void setCurrentFile(File file) { this.currentFile = file;}

    boolean checkUserContinue() {
        if (familyTree.getTreeList().size() != 0) {
            int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure you wish to continue? Any unsaved changes will be lost", "Warning", JOptionPane.YES_NO_CANCEL_OPTION);
            return dialogResult == JOptionPane.YES_OPTION;
        }
        return true;
    }

    /**
     * shows a error dialog containing an error message from a exception
     * @param e the exception to get the message from
     */
    void showErrorDialog(Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }


    public static void main(String[] args) {
        MainFrame mw = new MainFrame("Family tree");
    }

}

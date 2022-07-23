package com.suai.familyTree.view;

import com.suai.familyTree.model.Person;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;

public class HeaderPanel extends JPanel {

    public HeaderPanel(MainFrame mainFrame){
        JLabel headerLabel = new JLabel("Welcome to the Family Tree Application", JLabel.LEFT);
        headerLabel.setFont(new Font("SansSerif", Font.PLAIN, 22));
        JButton open = new JButton("Load Tree");
        open.addActionListener(new openAction(mainFrame));

        JButton saveTree = new JButton("Save Tree");
        saveTree.addActionListener(new saveAction(mainFrame));

        JButton saveTreeAs = new JButton("Save as");
        saveTreeAs.addActionListener(new saveAsAction(mainFrame));

        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(0,10,10,10));
        setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(headerLabel, gbc);

        JPanel container = new JPanel(new FlowLayout(FlowLayout.LEFT));
        container.setOpaque(false);
        container.add(open);
        container.add(saveTree);
        container.add(saveTreeAs);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(container, gbc);
    }



    private class openAction implements ActionListener { // listener for load tree button

        private MainFrame mainFrame;
        public openAction(MainFrame mainFrame){this.mainFrame = mainFrame;}

        @Override
        public void actionPerformed(ActionEvent e) {
            if (mainFrame.checkUserContinue()) {
                JFileChooser jFileChooser = new JFileChooser();
                //set file filters
                jFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("FamilyTree Files (*.txt)", "txt"));
                jFileChooser.setAcceptAllFileFilterUsed(true);

                int result = jFileChooser.showOpenDialog(this.mainFrame);
                //process jfilechooser result
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        //try to open the file, display the family tree
                        openFile(jFileChooser.getSelectedFile());
                        //displayTree(currentFamilyTree);
                        this.mainFrame.getStatusPanel().editStatus("File opened from: " + (jFileChooser.getSelectedFile().getAbsolutePath()));
                    } catch (Exception j) {
                        //error
                        this.mainFrame.showErrorDialog(j);
                        this.mainFrame.getStatusPanel().editStatus("Error: " + j.getMessage());
                    }
                }
            }
        }

        void openFile(File file) {
            this.mainFrame.getFamilyTree().clear();
            this.mainFrame.getFamilyTree().loadFromTextFile(file);
            this.mainFrame.setCurrentFile(file);
            this.mainFrame.getFamilyView().clearComponents();
            this.mainFrame.getFamilyView().setComponents();
            this.mainFrame.getFamilyView().repaint();
        }

    }

    private class saveAction implements ActionListener {

        private MainFrame mainFrame;
        public saveAction(MainFrame mainFrame){this.mainFrame = mainFrame;}

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (this.mainFrame.getCurrentFile() != null) {
                    int dialogResult = JOptionPane.showConfirmDialog(mainFrame, "Would You Like to overwrite the current tree?", "Warning", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        //save the file
                        saveToFile(this.mainFrame.getCurrentFile());
                        this.mainFrame.getStatusPanel().editStatus("File saved to: " + this.mainFrame.getCurrentFile().getPath());
                    }
                } else {
                    this.mainFrame.getStatusPanel().editStatus("File not loaded");
                    //save as instead
                    ActionListener listener = new saveAsAction(this.mainFrame);
                    listener.actionPerformed(e);

                }

            } catch (Exception j) {
                this.mainFrame.showErrorDialog(j);
                this.mainFrame.getStatusPanel().editStatus("Error: " + j.getMessage());
            }
        }

        private void saveToFile(File file) {
            this.mainFrame.setCurrentFile(file);
            this.mainFrame.getFamilyTree().saveToTxtFile(file);
        }

    }


    private class saveAsAction implements ActionListener {

        private MainFrame mainFrame;
        public saveAsAction(MainFrame mainFrame){this.mainFrame = mainFrame;}

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser jFileChooser = new JFileChooser() {
                //check if file already exists, as to overwrite
                @Override
                public void approveSelection() {
                    File selectedFile = getSelectedFile();
                    if (selectedFile.exists() && getDialogType() == SAVE_DIALOG) {
                        int result = JOptionPane.showConfirmDialog(this, "The file exists, overwrite?", "Existing file", JOptionPane.YES_NO_CANCEL_OPTION);
                        switch (result) {
                            case JOptionPane.YES_OPTION:
                                super.approveSelection();
                                return;
                            case JOptionPane.NO_OPTION:
                                return;
                            case JOptionPane.CLOSED_OPTION:
                                return;
                            case JOptionPane.CANCEL_OPTION:
                                cancelSelection();
                                return;
                        }
                    }
                    super.approveSelection();
                }
            };
            jFileChooser.setSelectedFile(new File("Family Tree.txt"));
            //Set an extension filter, so the user sees other ft files
            jFileChooser.setFileFilter(new FileNameExtensionFilter("FamilyTree Files (*.txt)", "txt"));
            //propmpt to save
            int result = jFileChooser.showSaveDialog(this.mainFrame);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    String filename = jFileChooser.getSelectedFile().toString();
                    if (!filename.endsWith(".txt")) {
                        filename += ".txt";
                    }
                    File file = new File(filename);

                    saveToFile(file);
                    //displayTree(currentFamilyTree);
                    this.mainFrame.getStatusPanel().editStatus("File saved to: " + (file.getAbsolutePath()));
                } catch (Exception j) {
                    this.mainFrame.showErrorDialog(j);
                    this.mainFrame.getStatusPanel().editStatus("Error: "+ j.getMessage());
                }
            }
        }

        private void saveToFile(File file) {
            this.mainFrame.setCurrentFile(file);
            this.mainFrame.getFamilyTree().saveToTxtFile(file);
        }
    }







}

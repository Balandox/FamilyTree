package com.suai.familyTree.view;

import com.suai.familyTree.model.FamilyTree;
import com.suai.familyTree.model.Person;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.util.NoSuchElementException;

public class PersonInfo extends JSplitPane{

    Person person;
    FamilyTree tree;
    Image image;
    TreeListener listener;
    JLabel imageLabel;
    FamilyView familyView;


    public PersonInfo(int newOrientation, PersonComponent source, FamilyView familyView) {
        super(newOrientation);

        this.familyView = familyView;
        this.person = source.getPerson();
        this.image = source.getImage();
        this.tree = source.getTree();
        this.listener = source.getTreeListener();


        this.setLeftComponent(createLeftPanel());
        this.setRightComponent(createRightPanel());
    }

    private JPanel createLeftPanel() {

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        imageLabel = new JLabel(new ImageIcon(image.getScaledInstance(200,200, Image.SCALE_SMOOTH)));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(createDeleteButton());
        buttonPanel.add(createAddRelativeButton());

        panel.add("South",buttonPanel);
        panel.add("Center", imageLabel);

        return panel;
    }

    private JButton createDeleteButton() {
        JButton deleteButton = new JButton("Delete");

        deleteButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                tree.removeAndFixNodes(person);

                try {
                    listener.vetoableChange(new PropertyChangeEvent(this, "",
                            tree, tree));

                } catch (PropertyVetoException e1) {
                    e1.printStackTrace();
                }
            }
        });

        deleteButton.setSize(100, 50);

        return deleteButton;
    }

    private JButton createAddRelativeButton() {
        JButton addRelativeButton = new JButton("Add relative");

        addRelativeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                AddRelativeView relativeView = new AddRelativeView(tree);

                int result = relativeView.openDialog("Relative addition");

                try {
                    if (result == JOptionPane.OK_OPTION) {
                        Person personToAdd = relativeView.getPerson();

                        //Person.RelativeType selectedRelative = relativeView.getRelativeType();

                        person.addRelative(relativeView.getRelativeType(), personToAdd);

                        tree.addNewPerson(personToAdd);
                        //checkSpouse(person, tree);


                        familyView.clearComponents();
                        familyView.setComponents();
                        familyView.repaint();
                    }
                } catch (NumberFormatException exception) {
                }
            }

        });

        addRelativeButton.setSize(100, 50);

        return addRelativeButton;
    }




        private JPanel createRightPanel() {

        JPanel rightPanel = new JPanel();

        rightPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        rightPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        JPanel container = new JPanel();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        rightPanel.add(container, gbc);

        //set another layout for the details
        GroupLayout layout = new GroupLayout(container);
        container.setLayout(layout);
        //dynamic gaps
        layout.setAutoCreateGaps(true);

        JLabel memberInfoLabel = new JLabel("Person Info: ");
        memberInfoLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        JLabel nameLabel = new JLabel("First name");
        JLabel nameTextField = new JLabel(this.person.getFirstName(), 10);
        JLabel lastnameLabel = new JLabel("Last name");
        JLabel lastnameTextField = new JLabel(this.person.getLastName(), 10);
        JLabel middleNameLabel = new JLabel("Middle name");
        JLabel middleNameTextField = new JLabel(this.person.getMiddleName(), 10);

        JLabel genderLabel = new JLabel("Gender");
        JLabel genderComboBox = new JLabel(person.getGender());

        JLabel birthDateLabel = new JLabel("Birth date: ");
        JLabel birthDateTextField = new JLabel(String.valueOf(this.person.getBirthDate()), 10);

        JLabel deathDateLabel = new JLabel("Death date: ");
        JLabel deathDateTextField = new JLabel(String.valueOf(this.person.getDeathDate()), 10);

        JLabel relativeInfoLabel = new JLabel("Relative Info: ");
        relativeInfoLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));

        JLabel fatherLabel = new JLabel("Father");
        JLabel fatherTextField = new JLabel();
        if (this.person.has(Person.Attribute.FATHER)) {
            fatherTextField.setText(this.person.getFather().toString());
        } else {
            fatherTextField.setText("No father on record");
        }
        JLabel motherLabel = new JLabel("Mother");
        JLabel motherTextField = new JLabel();
        if (this.person.has(Person.Attribute.MOTHER)) {
            motherTextField.setText(this.person.getMother().toString());
        } else {
            motherTextField.setText("No mother on record");
        }
        JLabel spouseLabel = new JLabel("Spouse");
        JLabel spouseTextField = new JLabel();
        if (this.person.has(Person.Attribute.SPOUSE)) {
            spouseTextField.setText(this.person.getSpouse().toString());
        } else {
            spouseTextField.setText("No spouse on record");
        }
        JLabel childrenLabel = new JLabel("Children");
        String children = "<html>";
        if (this.person.has(Person.Attribute.CHILDREN)) {
            for (Person child : this.person.getChildren()) {
                children += child.toString() + "<br>";
            }
            children += "</html>";
        } else {
            children = "No children on record";
        }
        JLabel childrenTextField = new JLabel(children);


        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(memberInfoLabel)
                        .addComponent(nameLabel)
                        .addComponent(middleNameLabel)
                        .addComponent(lastnameLabel)
                        .addComponent(birthDateLabel)
                        .addComponent(deathDateLabel)
                        .addComponent(genderLabel)
                        .addComponent(relativeInfoLabel)
                        .addComponent(fatherLabel)
                        .addComponent(motherLabel)
                        .addComponent(spouseLabel)
                        .addComponent(childrenLabel)
                )
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(nameTextField)
                        .addComponent(middleNameTextField)
                        .addComponent(lastnameTextField)
                        .addComponent(birthDateTextField)
                        .addComponent(deathDateTextField)
                        .addComponent(genderComboBox)
                        .addComponent(fatherTextField)
                        .addComponent(motherTextField)
                        .addComponent(spouseTextField)
                        .addComponent(childrenTextField)
                )
        );

        // verticle alignmnet
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(memberInfoLabel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(nameLabel)
                        .addComponent(nameTextField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(middleNameLabel)
                        .addComponent(middleNameTextField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lastnameLabel)
                        .addComponent(lastnameTextField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(birthDateLabel)
                        .addComponent(birthDateTextField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(deathDateLabel)
                        .addComponent(deathDateTextField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(genderLabel)
                        .addComponent(genderComboBox))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(relativeInfoLabel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(fatherLabel)
                        .addComponent(fatherTextField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(motherLabel)
                        .addComponent(motherTextField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(spouseLabel)
                        .addComponent(spouseTextField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(childrenLabel)
                        .addComponent(childrenTextField))
        );

        return rightPanel;
    }


}




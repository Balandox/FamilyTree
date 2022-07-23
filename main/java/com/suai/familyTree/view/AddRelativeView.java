package com.suai.familyTree.view;

import com.suai.familyTree.model.FamilyTree;
import com.suai.familyTree.model.Person;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Color;

import java.io.File;
import java.util.List;
import java.util.Objects;

import javax.swing.*;
import javax.swing.border.LineBorder;


public class AddRelativeView extends JPanel {

    private JPanel panel = new JPanel(new GridLayout(7,2));
    private Person person = new Person();
    private JTextField firstName = new JTextField();
    private JTextField lastName = new JTextField();
    private JTextField middleName = new JTextField();
    private JTextField birthDate = new JTextField();
    private JTextField deathDate = new JTextField();


    private JComboBox<Person> father;
    private JComboBox<Person> mother;
    private JComboBox<Person> spouse;
    private JComboBox<String> gender;
    private JComboBox<Person.RelativeType> relativeType;
    private Person.RelativeType selectRelative;

    private FamilyTree tree;

    private Person[] allPersonsArray;
    private Person[] malePersonsArray;
    private Person[] femalePersonsArray;
    private Person.RelativeType[] relativeTypesArray;

    public AddRelativeView(FamilyTree tree) {
        this.tree = tree;
        this.malePersonsArray = this.tree.getMalePersonsArray();
        this.femalePersonsArray = this.tree.getFemalePersonsArray();
        this.allPersonsArray = this.tree.getPersonsArray();
        this.selectRelative = null;

        this.relativeTypesArray = this.tree.getRelativeTypeArray();

        panel.add(new JLabel("Relative type: "));
        relativeType = new JComboBox<>(this.relativeTypesArray);
        panel.add(relativeType);

        panel.add(new JLabel("Gender: "));
        gender = new JComboBox<String>(new String[]{"male", "female"});
        panel.add(gender);

        panel.add(new JLabel("First name: "));
        panel.add(firstName);

        panel.add(new JLabel("Last name: "));
        panel.add(lastName);

        panel.add(new JLabel("Middle name: "));
        panel.add(middleName);

        panel.add(new JLabel("Birth day: "));
        panel.add(birthDate);

        panel.add(new JLabel("Death day: "));
        panel.add(deathDate);


        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(panel);
    }

    public Person getPerson() {

        Person temp;
        Person copyPerson = new Person(person);

        this.selectRelative = (Person.RelativeType) this.relativeType.getSelectedItem();

        copyPerson.setGender((String)gender.getSelectedItem());
        copyPerson.setFirstName(firstName.getText());
        copyPerson.setLastName(lastName.getText());
        copyPerson.setMiddleName(middleName.getText());
        copyPerson.setBirthDate(birthDate.getText());
        copyPerson.setDeathDate(deathDate.getText());
        copyPerson.setAge();
        copyPerson.setPhoto();


        return copyPerson;
    }

    public Person.RelativeType getRelativeType(){ return this.selectRelative;}


    public int openDialog(String title) {

        int result = JOptionPane.showConfirmDialog(null, this, title,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        return result;
    }


}

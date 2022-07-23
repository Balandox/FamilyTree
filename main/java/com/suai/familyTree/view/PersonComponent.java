package com.suai.familyTree.view;

import com.suai.familyTree.model.Constants;
import com.suai.familyTree.model.FamilyTree;
import com.suai.familyTree.model.Person;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

public class PersonComponent extends JComponent {
    private volatile int screenX = 0;
    private volatile int screenY = 0;
    private volatile int myX = 0;
    private volatile int myY = 0;

    private Font textFont = new Font(Font.DIALOG, Font.ROMAN_BASELINE|Font.BOLD, 12);

    private Image image;
    private Person person;
    private FamilyTree tree;
    private TreeListener treeListener;
    private FamilyView familyView;


    public PersonComponent(Person person, FamilyTree tree, TreeListener listener, FamilyView familyView) {

        this.person = person;
        this.tree = tree;
        this.treeListener = listener;
        this.familyView = familyView;

        ImageIcon imageIcon = new ImageIcon(person.getPhoto());
        image = imageIcon.getImage().getScaledInstance(Constants.ICON_HEIGHT, Constants.ICON_WIDTH, Image.SCALE_SMOOTH);

        setBorder(new LineBorder(new Color(12, 174, 91), 3));
        setBounds(0, 0, Constants.ICON_WIDTH, Constants.ICON_HEIGHT);
        setOpaque(true);

        setBackground(Color.WHITE);
        setName(person.getFirstName());
        setLocation(person.curX, person.curY);



        addMouseListener(createMouseListener());
        addMouseMotionListener(createMouseMotionListener());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.gray);
        g.fillRect(0, 0, Constants.ICON_WIDTH, Constants.ICON_HEIGHT);
        g.setColor(Color.BLACK);
        g.drawImage(image, 0, 0, null);

        g.clearRect(0, 80, Constants.ICON_WIDTH, 20);
        g.drawLine(0, 80, Constants.ICON_WIDTH, 80);
        g.setFont(textFont);
        g.drawString(person.getShortName(), 5, 93);
    }


    private MouseListener createMouseListener() {
        PersonComponent thisComponent = this;

        return new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                PersonInfo window = new PersonInfo(JSplitPane.HORIZONTAL_SPLIT, thisComponent, familyView);
                JOptionPane.showMessageDialog(null, window, person.getFirstName(), JOptionPane.PLAIN_MESSAGE);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                screenX = e.getXOnScreen();
                screenY = e.getYOnScreen();

                myX = getX();
                myY = getY();
            }

        };
    }

    private MouseMotionListener createMouseMotionListener() {

        return new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                int deltaX = e.getXOnScreen() - screenX;
                int deltaY = e.getYOnScreen() - screenY;

                person.curX = myX + deltaX;
                person.curY = myY + deltaY;
                setLocation(person.curX, person.curY);
            }

        };
    }

    public Image getImage() {
        return image;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public FamilyTree getTree() {
        return tree;
    }

    public void setTree(FamilyTree tree) {
        this.tree = tree;
    }

    public TreeListener getTreeListener() {
        return treeListener;
    }


}

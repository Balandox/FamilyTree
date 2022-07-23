package com.suai.familyTree.view;

import com.suai.familyTree.model.Constants;
import com.suai.familyTree.model.FamilyTree;
import com.suai.familyTree.model.Node;
import com.suai.familyTree.model.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class FamilyView extends JPanel {

    private FamilyTree familyTree;

    private Color green = new Color(12, 174, 91);
    //private Color backgroundColor = new Color(166, 123, 81);
    private Color branchColor = new Color(97,59,22);
    private Color bloodLine = new Color(255, 87, 51);

    private List<PersonComponent> personComponents = new ArrayList<>();
    private List<NodeComponent> nodeComponents = new ArrayList<>();
    private TreeListener treeListener;

    public FamilyView(FamilyTree familyTree) {

        this.familyTree = familyTree;

        setBackground(Color.lightGray);
        setLayout(null);

        setComponents();
        treeListener = new TreeListener(this);

        addVetoableChangeListener(treeListener);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { repaint(); }
        });
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        for (Node node : this.familyTree.getNodeList()) {
            drawСhildrenColors(g, node);
            drawParentsLines(g, node);
        }

        drawSpouseLines(g);
    }

    public void clearComponents() {
        personComponents.forEach(x -> remove(x));
        personComponents.clear();
        nodeComponents.forEach(x -> remove(x));
        nodeComponents.clear();

        repaint();
    }

    public void setComponents() {

        int[] furtherPoint = this.familyTree.getFurtherPoint();
        setPreferredSize(new Dimension(furtherPoint[0] + Constants.ICON_WIDTH + 2000,
                furtherPoint[1] + Constants.ICON_HEIGHT + 2000));

        addPersons();
        addNodes();
    }


    private void drawLine(Graphics g, Person person, Node node, Color color) {

        int personX = person.curX + Constants.ICON_WIDTH / 2;
        int personY = person.curY + Constants.ICON_HEIGHT / 2;
        int nodeX = node.curX + Constants.NODE_WIDTH / 2;
        int nodeY = node.curY + Constants.NODE_HEIGHT / 2;

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(branchColor);
        g2.setStroke(new BasicStroke(Constants.BRANCH_WIDTH));

        g2.draw(new Line2D.Float(personX, personY, nodeX, nodeY));
        g2.setColor(color);

        g2.setStroke(new BasicStroke(Constants.LINE_WIDTH));
        g2.draw(new Line2D.Float(personX, personY, nodeX, nodeY));

        g2.setStroke(new BasicStroke(1));
    }

    private void drawСhildrenColors(Graphics g, Node node) {

        if ((node.getFather() != null &&
                node.getMother() != null) || node.getChildren().size() != 0 || node.getNodeId() == 1) {

            for (Person person : node.getChildren()) {
                try {
                    drawLine(g, person, node, green);
                } catch (NoSuchElementException e) {}
            }
        }
    }

    private void drawParentsLines(Graphics g, Node node) {

        try {
            if(node.getFather() != null)
                drawLine(g, node.getFather(), node, bloodLine);
        } catch (NoSuchElementException e) {}

        try {
            if(node.getMother() != null)
            drawLine(g, node.getMother(), node, bloodLine);
        } catch (NoSuchElementException e) {}
    }

    private void drawSpouseLines(Graphics g) {
        Person spouse;
        int personOneX = 0, personOneY = 0, personTwoX = 0, personTwoY = 0;
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.CYAN);
        g2.setStroke(new BasicStroke(5));


        for (Person person : this.familyTree.getTreeList()) {
            if (person.getSpouse() != null) {

                spouse = person.getSpouse();

                personOneX = spouse.curX + Constants.ICON_WIDTH / 2;
                personOneY = spouse.curY + Constants.ICON_HEIGHT / 2;
                personTwoX = person.curX + Constants.ICON_WIDTH / 2;
                personTwoY = person.curY + Constants.ICON_HEIGHT / 2;

                g2.draw(new Line2D.Float(personOneX, personOneY, personTwoX, personTwoY));
            }
        }

        g2.setStroke(new BasicStroke(1));
    }

    private void addPersons() {
        List<Person> all = this.familyTree.getTreeList();
        PersonComponent component;

        for (Person person : all) {
            component = new PersonComponent(person, this.familyTree, treeListener, this);
            component.addComponentListener(new ComponentAdapter() {

                @Override
                public void componentMoved(ComponentEvent e) {
                    repaint();
                }
            });
            personComponents.add(component);
            add(component);
        }
    }


    private void addNodes() {
        List<Node> nodes = this.familyTree.getNodeList();
        NodeComponent nodeComponent;

        for (Node node : nodes) {
            if ((node.getFather() != null &&
                    node.getMother() != null) || node.getChildren().size() != 0 || node.getNodeId() == 1) {

                nodeComponent = new NodeComponent(node, node.curX, node.curY);

                nodeComponent.addComponentListener(new ComponentAdapter() {

                    @Override
                    public void componentMoved(ComponentEvent e) {
                        repaint();
                    }
                });

                nodeComponents.add(nodeComponent);
                add(nodeComponent);
            }
        }
    }
}


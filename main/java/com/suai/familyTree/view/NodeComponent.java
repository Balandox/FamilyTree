package com.suai.familyTree.view;

import com.suai.familyTree.model.Constants;
import com.suai.familyTree.model.Node;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.*;

public class NodeComponent extends JComponent {

    private volatile int screenX = 0;
    private volatile int screenY = 0;
    private volatile int myX = 0;
    private volatile int myY = 0;

    private Node node;

    public NodeComponent(Node node, int x, int y) {
        this.node = node;

        setBorder(createBorder());

        setBounds(0, 0, Constants.NODE_WIDTH, Constants.NODE_HEIGHT);
        setOpaque(true);

        setLocation(x, y);
        node.curX = x;
        node.curY = y;

        addMouseListener(createMouseListener());
        addMouseMotionListener(createMouseMotionListener());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLUE);
        g.fillOval(0, 0, Constants.NODE_WIDTH, Constants.NODE_HEIGHT);
    }

    private AbstractBorder createBorder() {
        AbstractBorder border = new AbstractBorder() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                g.setColor(new Color(166, 123, 81));
                g.fillOval(x, y, width, height);
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(3));
                g.setColor(new Color(80, 40, 0));
                g2.drawOval(x, y, width, height);
                g2.setStroke(new BasicStroke(1));
            }
        };

        return border;
    }

    private MouseListener createMouseListener() {

        return new MouseAdapter() {

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

                node.curX = myX + deltaX;
                node.curY = myY + deltaY;
                setLocation(node.curX, node.curY);
            }

        };
    }
}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package panel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

/**
 *
 * @author Esraa Ebrahim
 */
public class MyPanel extends JPanel {

    JButton Green, Red, Blue;
    JButton Oval, Rect, Line, CLEARALL, Eraser;
    int setColor = 0, setShape = 0;
    int xStart, yStart, xEnd, yEnd;

    final int OVAL_CONSTANT = 4;
    final int RECT_CONSTANT = 5;
    final int LINE_CONSTANT = 6;
    final int ERS_CONSTANT = 7;

    boolean ofilled, odotted;
    JCheckBox filled, dotted;

    int shapetype = 0;
    Vector<Shape> shape = new Vector<Shape>();

    public MyPanel() {
        this.setBackground(Color.WHITE);
        this.setFocusable(true);
        ofilled = false;
        odotted = false;
        //color buttons
        Green = new JButton("Green");
        Green.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setColor = 1;
            }
        });
        add(Green);
        Red = new JButton("Red");
        Red.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setColor = 2;

            }
        });
        add(Red);
        Blue = new JButton("Blue");
        Blue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setColor = 3;

            }
        });
        add(Blue);
        //shape buttons
        Oval = new JButton("Oval");
        Oval.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setShape = 4;

            }
        });
        add(Oval);
        Rect = new JButton("Rectangle ");
        Rect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setShape = 5;

            }
        });
        add(Rect);
        Line = new JButton("Line");
        Line.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setShape = 6;

            }
        });
        add(Line);
        CLEARALL = new JButton("CLEARALL");
        CLEARALL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearAll();
            }
        });
        add(CLEARALL);
        Eraser = new JButton("Eraser");
        Eraser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setShape = 7;
            }
        });
        add(Eraser);

        filled = new JCheckBox("Filled");
        filled.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == 1) {
                    ofilled = true;
                } else {
                    ofilled = false;
                }
            }
        });
        add(filled);
        dotted = new JCheckBox("Dotted");
        dotted.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == 1) {
                    odotted = true;
                } else {
                    odotted = false;
                }
            }
        });
        add(dotted);

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                xStart = e.getX();
                yStart = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                xEnd = e.getX();
                yEnd = e.getY();
                if (setShape == OVAL_CONSTANT) {
                    shapetype = setShape;
                    Shape oval1 = new Oval(xStart, yStart, xEnd, yEnd, setColor, ofilled, odotted, shapetype);
                    shape.add(oval1);
                    repaint();
                } else if (setShape == RECT_CONSTANT) {
                    shapetype = setShape;
                    Shape rect1 = new Rrectangle(xStart, yStart, xEnd, yEnd, setColor, ofilled, odotted, shapetype);
                    shape.add(rect1);
                    repaint();
                } else if (setShape == LINE_CONSTANT) {
                    shapetype = setShape;
                    Shape line1 = new Line(xStart, yStart, xEnd, yEnd, setColor, ofilled, odotted, shapetype);
                    shape.add(line1);
                    repaint();
                } else if (setShape == ERS_CONSTANT) {
                    shapetype = setShape;
                    Shape ers = new Erser(xStart, yStart, xEnd, yEnd, setColor, ofilled, odotted, shapetype);
                    shape.add(ers);
                    repaint();

                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                xEnd = e.getX();
                yEnd = e.getY();
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }

        });

    }

    public void clearAll() {
        shape.clear();
        xStart = yStart = xEnd = yEnd = 0;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (setShape == 7) {
            Erser ers = new Erser(xStart, yStart, xEnd, yEnd, setColor, ofilled, odotted, shapetype);
            ers.draw(g);
        } else if (setShape == 4) {
            Oval ova = new Oval(xStart, yStart, xEnd, yEnd, setColor, ofilled, odotted, shapetype);
            ova.draw(g);
        } else if (setShape == 5) {
            Rrectangle rect = new Rrectangle(xStart, yStart, xEnd, yEnd, setColor, ofilled, odotted, shapetype);
            rect.draw(g);
        } else if (setShape == 6) {
            Line MyLine = new Line(xStart, yStart, xEnd, yEnd, setColor, ofilled, odotted, shapetype);
            MyLine.draw(g);
        }
        for (int i = 0; i < shape.size(); i++) {
            if (shape.get(i).shapestyle == 4) {

                Shape sh = new Oval(shape.get(i).startX, shape.get(i).startY, shape.get(i).endX, shape.get(i).endY, shape.get(i).color, shape.get(i).ofilled, shape.get(i).dotted, shape.get(i).shapestyle);
                sh.draw(g);

            } else if (shape.get(i).shapestyle == 5) {

                Shape sh = new Rrectangle(shape.get(i).startX, shape.get(i).startY, shape.get(i).endX, shape.get(i).endY, shape.get(i).color, shape.get(i).ofilled, shape.get(i).dotted, shape.get(i).shapestyle);
                sh.draw(g);

            } else if (shape.get(i).shapestyle == 6) {

                Shape sh = new Line(shape.get(i).startX, shape.get(i).startY, shape.get(i).endX, shape.get(i).endY, shape.get(i).color, shape.get(i).ofilled, shape.get(i).dotted, shape.get(i).shapestyle);
                sh.draw(g);

            } else if (shape.get(i).shapestyle == 7) {

                Shape sh = new Erser(shape.get(i).startX, shape.get(i).startY, shape.get(i).endX, shape.get(i).endY, shape.get(i).color, shape.get(i).ofilled, shape.get(i).dotted, shape.get(i).shapestyle);
                sh.draw(g);

            }

        }
    }

    abstract class Shape {

        public int startX, startY, endX, endY, color, shapestyle;
        boolean ofilled, dotted;

        public Shape(int a, int b, int c, int d, int cc, boolean f, boolean dot, int s) {

            startX = a;
            startY = b;
            endX = c;
            endY = d;
            color = cc;
            ofilled = f;
            dotted = dot;
            shapestyle = s;
        }

        public abstract void draw(Graphics g);
    }

    class Oval extends Shape {

        public Oval(int a, int b, int c, int d, int cc, boolean f, boolean dot, int s) {
            super(a, b, c, d, cc, f, dot, s);
        }

        @Override
        public void draw(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
            g2d.setStroke(dashed);

            if (color == 1) {
                g.setColor(Color.green);
                g2d.setColor(Color.green);
            } else if (color == 2) {
                g.setColor(Color.red);
                g2d.setColor(Color.red);
            } else if (color == 3) {
                g.setColor(Color.blue);
                g2d.setColor(Color.blue);
            }
            if (ofilled == false) {
                if (dotted == true) {
                    g2d.drawOval(Math.min(endX, startX), Math.min(endY, startY), Math.abs(endX - startX), Math.abs(endY - startY));
                } else {
                    g.drawOval(Math.min(endX, startX), Math.min(endY, startY), Math.abs(endX - startX), Math.abs(endY - startY));
                }
            } else {
                if (dotted == true) {
                    g2d.fillOval(Math.min(endX, startX), Math.min(endY, startY), Math.abs(endX - startX), Math.abs(endY - startY));
                } else {
                    g.fillOval(Math.min(endX, startX), Math.min(endY, startY), Math.abs(endX - startX), Math.abs(endY - startY));
                }
            }

        }
    }

    class Rrectangle extends Shape {

        public Rrectangle(int a, int b, int c, int d, int cc, boolean f, boolean dot, int s) {
            super(a, b, c, d, cc, f, dot, s);
        }

        @Override
        public void draw(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
            g2d.setStroke(dashed);

            if (color == 1) {
                g.setColor(Color.green);
                g2d.setColor(Color.green);
            } else if (color == 2) {
                g.setColor(Color.red);
                g2d.setColor(Color.red);
            } else if (color == 3) {
                g.setColor(Color.blue);
                g2d.setColor(Color.blue);
            }
            if (ofilled == false) {
                if (dotted == true) {
                    g2d.drawRect(Math.min(endX, startX), Math.min(endY, startY), Math.abs(endX - startX), Math.abs(endY - startY));
                } else {
                    g.drawRect(Math.min(endX, startX), Math.min(endY, startY), Math.abs(endX - startX), Math.abs(endY - startY));
                }
            } else {
                if (dotted == true) {
                    g2d.fillRect(Math.min(endX, startX), Math.min(endY, startY), Math.abs(endX - startX), Math.abs(endY - startY));
                } else {
                    g.fillRect(Math.min(endX, startX), Math.min(endY, startY), Math.abs(endX - startX), Math.abs(endY - startY));
                }
            }

        }
    }

    class Line extends Shape {

        public Line(int a, int b, int c, int d, int cc, boolean f, boolean dot, int s) {
            super(a, b, c, d, cc, f, dot, s);
        }

        @Override
        public void draw(Graphics g) {

            Graphics2D g2d = (Graphics2D) g.create();
            Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
            g2d.setStroke(dashed);
            if (color == 1) {
                g.setColor(Color.green);
                g2d.setColor(Color.green);
            } else if (color == 2) {
                g.setColor(Color.red);
                g2d.setColor(Color.red);
            } else if (color == 3) {
                g.setColor(Color.blue);
                g2d.setColor(Color.blue);
            }
            if (dotted == true) {
                g2d.drawLine(startX, startY, endX, endY);
            } else {

                g.drawLine(startX, startY, endX, endY);
            }
        }

    }

    class Erser extends Shape {

        public Erser(int a, int b, int c, int d, int cc, boolean f, boolean dot, int s) {
            super(a, b, c, d, cc, false, false, s);
        }

        @Override
        public void draw(Graphics g) {

            g.setColor(Color.white);

            g.fillRect(startX, startY, 20, 20);
        }
    }
}

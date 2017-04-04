package ui;

import app.UI;
import structures.Grid;
import structures.Line;
import structures.Point;

import javax.swing.JComponent;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Stack;

public class Canvas extends JComponent implements MouseListener {
    private UI ui;
    Grid grid;
    HashMap<Integer, Guideline> guides;
    Stack<Point> points;
    private int width = 500;
    private int height = width;
    private int borderWidth = 100;
    private int borderHeight = 10;
    private boolean comparePoints = false;

    public Canvas(UI ui) {
        setPreferredSize(new Dimension(width + 2 * borderWidth, height + 2 * borderHeight));
        setBorder(new MatteBorder(borderHeight, borderWidth, borderHeight, borderWidth, new Color(223, 223, 223)));
        addMouseListener(this);
        this.grid = new Grid();
        this.guides = new HashMap<>();
        this.points = new Stack<>();
        this.ui = ui;
    }

    public void enableClicking() {
        comparePoints = true;
    }

    public void disableClicking() {
        points = new Stack<>();
        comparePoints = false;
    }

    private void drawLineLabel(Graphics g, Line line) {
        int offsetV = 5;
        int offsetH = 5;

        if (line.start().x == 0.0d) {
            if (line.getSlope() < 0) {
                g.drawString("" + line.getId(), // top right
                        borderWidth + offsetH,
                        height - borderHeight - (int) Math.round(line.start().y * height) + offsetV * 2);
            } else {
                g.drawString("" + line.getId(), // bottom right
                        borderWidth + offsetH,
                        height - borderHeight - (int) Math.round(line.start().y * height) + offsetV * 7);
            }
        } else if (line.start().x == 1.0d) {
            if (line.getSlope() > 0) {
                g.drawString("" + line.getId(), // top left
                        borderWidth + width - offsetH * 2,
                        height - borderHeight - (int) Math.round(line.start().y * height) + offsetV * 2);
            } else {
                g.drawString("" + line.getId(), // bottom left
                        borderWidth + width - offsetH * 2,
                        height - borderHeight - (int) Math.round(line.start().y * height) + offsetV * 7);
            }
        } else if (line.start().y == 0.0d) {
            if (line.getSlope() < 0) {
                g.drawString("" + line.getId(), // top right
                        borderWidth + (int) Math.round(line.start().x * width) + offsetH,
                        borderHeight + height - offsetV);
            } else {
                g.drawString("" + line.getId(), // top left
                        borderWidth + (int) Math.round(line.start().x * width) - offsetH * 2,
                        borderHeight + height - offsetV);
            }
        } else if (line.start().y == 1.0d) {
            if (line.getSlope() < 0) {
                g.drawString("" + line.getId(), // bottom left
                        borderWidth + (int) Math.round(line.start().x * width) - offsetH * 2,
                        borderHeight + offsetV * 3);
            } else {
                g.drawString("" + line.getId(), // bottom right
                        borderWidth + (int) Math.round(line.start().x * width) + offsetH,
                        borderHeight + offsetV * 3);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 14));
        for (Guideline gl : guides.values()) {
            gl.draw(g, getWidth(), getHeight(), borderWidth, borderHeight);
            drawLineLabel(g, gl.getLine());
        }
        for(Line line : grid.getLines()) {
            line.draw(g, getWidth(), getHeight(), borderWidth, borderHeight);
            drawLineLabel(g, line);
        }
        for(Point point : points) {
            point.draw(g, 2, getWidth(), getHeight(), borderWidth, borderHeight);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (comparePoints &&
                e.getX() > borderWidth + 2 && e.getX() < width + borderWidth - 2 &&
                e.getY() > borderHeight + 2 && e.getY() < height + borderHeight - 2) {

            Point p = new Point((e.getX() - borderWidth) / (double) width, (height - e.getY() + borderHeight) / (double) height);
            p.setId(65 + points.size());

            if (points.size() > 0) {
                ui.updatePointsLabel("Points " + points.peek().id + " and " + p.id + grid.comparePoints(points.peek(), p));
            }

            points.push(p);
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }
}

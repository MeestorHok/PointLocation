package ui;

import structures.Line;
import structures.Point;

import java.awt.Color;
import java.awt.Graphics;

public class Guideline {
    private int id;
    private Color color;
    private Line line;

    Guideline(int id, Color color, double x1, double y1, double x2, double y2) {
        this.id = id;
        this.color = color;
        updateLine(x1, y1, x2, y2);
    }

    public void updateLine(double x1, double y1, double x2, double y2) {
        Point start = new Point(x1, y1);
        Point end = new Point(x2, y2);
        line = new Line(id, color, start, end);
        start.setLine(line);
        end.setLine(line);
    }

    public Line getLine() {
        return line;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void draw(Graphics g, double width, double height, int bW, int bH) {
        line.draw(g, width, height, bW, bH);
    }
}

package structures;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Line {
    private final int id;
    private Color color;
    private Point startingPoint;
    private Point endingPoint;
    private double slope;

    public Line(int id, Point left, Point right) {
        this(id, Color.BLACK, left, right);
    }

    public Line(int id, Color color, Point left, Point right) {
        this.id = id;
        this.color = color;
        startingPoint = left;
        endingPoint = right;
        slope = (right.y - left.y) / (right.x - left.x);
    }

    public Point start() {
        return startingPoint;
    }

    public Point end() {
        return endingPoint;
    }

    public int getId() { return id; }

    public double getSlope() { return slope; }

    public void draw(Graphics g, double width, double height, int borderWidth, int borderHeight) {
        g.setColor(color);
        int x1 = borderWidth + (int) Math.round(startingPoint.x * (width - 2 * borderWidth));
        int x2 = borderWidth + (int) Math.round(endingPoint.x * (width - 2 * borderWidth));
        int y1 = (int) height - borderHeight - (int) Math.round(startingPoint.y * (height - 2 * borderHeight));
        int y2 = (int) height - borderHeight - (int) Math.round(endingPoint.y * (height - 2 * borderHeight));

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(x1, y1, x2, y2);
    }

    public Point generatePoint(double x) {
        double y = Math.min(1.00, Math.max(0, slope * (x - startingPoint.x) + startingPoint.y));
        Point ret = new Point(x, y);
        ret.setLine(this);

        return ret;
    }

    @Override
    public String toString() {
        return "{" + startingPoint.coords() + ", " + endingPoint.coords() + "}";
    }
}

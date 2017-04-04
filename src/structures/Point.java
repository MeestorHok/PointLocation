package structures;

import java.awt.Color;
import java.awt.Graphics;

public class Point implements Comparable<Point> {
    public double x;
    public double y;
    public String id;
    private Line line;

    public Point(double x, double y) {
        this.x = Math.max(0, Math.min(1.0, x));
        this.y = Math.max(0, Math.min(1.0, y));
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public Line getLine() {
        return line;
    }

    public Point passIntersection() {
        double buffer = 0.001;
        // accommodate for lines being drawn backwards (RTL)
        if (line.start().x > line.end().x) buffer *= -1;

        return line.generatePoint(Math.min(1.00, x + buffer));
    }

    public double distance() {
        return Math.sqrt(Math.abs(Math.pow((line.start().x - x), 2) + Math.pow((line.start().y - y), 2)));
    }

    @Override
    public int compareTo(Point other) {
        Point start = other.getLine().start();
        Point end = other.getLine().end();

        double dx1 = start.x - x;
        double dy1 = start.y - y;
        double dx2 = end.x - x;
        double dy2 = end.y - y;

        if (dx1*dy2 > dy1*dx2) return -1; // Counterclockwise
        else if (dx1*dy2 < dy1*dx2) return 1; // Clockwise
        else if ((dx1*dx2 < 0) || (dy1*dy2 < 0)) return 1; // Clockwise
        else if ((dx1*dx1+dy1*dy1) < (dx2*dx2+dy2*dy2)) return -1;// Counterclockwise
        else return 0; // Colinear
    }

    public void draw(Graphics g, int radius, double width, double height, int borderWidth, int borderHeight) {
        // adjusted x and y values
        int x1 = borderWidth + (int) Math.round(x * (width - 2 * borderWidth));
        int y1 = (int) height - borderHeight - (int) Math.round(y * (height - 2 * borderHeight));

        g.setColor(Color.BLACK);
        g.drawOval(x1 - radius, y1 - radius, 2 * radius, 2 * radius);
        g.drawString(id, x1 - 6 * radius, y1 + 6 * radius);
    }

    public void setId(int c) {
        id = Character.toString((char) c);
    }

    @Override
    public String toString() {
        return "" + line.getId();
    }

    public String coords() {
        return "(" + x + "," + y + ")";
    }
}

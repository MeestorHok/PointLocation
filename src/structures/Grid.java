package structures;

import java.util.ArrayList;

public class Grid {
    private ArrayList<Line> lines;
    private MyBST<Point> regions;

    public Grid() {
        reset();
    }

    public String comparePoints(Point point1, Point point2) {
        Point result = regions.comparePoints(point1, point2);
        if (result == null) return " are not separated by a line.";
        return " are separated by Line " + result.getLine().getId() + ".";
    }

    public void addLine(int id, double x1, double y1, double x2, double y2) {
        Point start = new Point(x1, y1);
        Point end = new Point(x2, y2);
        Line line = new Line(id, start, end);
        start.setLine(line);
        end.setLine(line);
        insert(line);
    }

    public void addLine(Line line) {
        insert(line);
    }

    private void insert(Line line) {
        regions.insert(line.start());

        if (lines.size() > 0) {
            ArrayList<Point> intersections = new ArrayList<>();

            for (Line other : lines) {
                Point intersection = getIntersection(line, other);
                if (intersection != null &&
                        intersection.x < 1.00 && intersection.y < 1.00 &&
                        intersection.x > 0.00 && intersection.y > 0.00) {
                    intersections.add(intersection);
                }
            }

            if (intersections.size() > 0) {
                // sort in order of distance from the beginning of the line
                intersections.sort((point1, point2) -> {
                    if (point1.distance() > point2.distance()) return 1;
                    if (point1.distance() < point2.distance()) return -1;
                    return 0;
                });

                // add the appropriate sections to the BST
                for (Point point : intersections) {
                    regions.insert(point.passIntersection());
                }
            }
        }

        // add the line to the list
        lines.add(line);
    }

    // Code (with custom edits) from http://www.ahristov.com/tutorial/geometry-games/intersection-lines.html
    private Point getIntersection(Line line1, Line line2) {
        double x1 = line1.start().x,
                y1 = line1.start().y,
                x2 = line1.end().x,
                y2 = line1.end().y,
                x3 = line2.start().x,
                y3 = line2.start().y,
                x4 = line2.end().x,
                y4 = line2.end().y;

        double d = (x1-x2)*(y3-y4) - (y1-y2)*(x3-x4);
        if (d == 0) return null;

        double xi = ((x3-x4)*(x1*y2-y1*x2)-(x1-x2)*(x3*y4-y3*x4))/d;
        double yi = ((y3-y4)*(x1*y2-y1*x2)-(y1-y2)*(x3*y4-y3*x4))/d;

        Point ret = new Point(xi, yi);
        ret.setLine(line1);

        return ret;
    }

    public double averageDepth() {
        return (double) regions.getDepth() / (double) regions.numExternalNodes();
    }

    public ArrayList<Line> getLines() {
        return lines;
    }

    public void reset() {
        lines = new ArrayList<>();
        regions = new MyBST<>();
    }

    public void print() {
        System.out.println("Lines " + lines.toString());
        System.out.println(regions.strDataPreOrder());
    }
}

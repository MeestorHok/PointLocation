package app;

import structures.Grid;
import structures.Point;

import java.io.File;
import java.util.Scanner;

public class TestClass {
    public static void main(String[] args) {
        File input = new File("input.txt");
        Grid grid = new Grid();

        try {
            // Access resources
            Scanner sc = new Scanner(input);
            int inputCount = Integer.parseInt(sc.nextLine());

            // Iterate over inputs
            for (int i = 0; i < inputCount; i++) {
                // Evaluate expression
                String line = sc.nextLine();
                if (line.equals("")) {
                    i--;
                    continue;
                }

                String[] points = line.split(" ");

                // Add the line to the grid
                grid.addLine(i, Double.parseDouble(points[0]),
                                Double.parseDouble(points[1]),
                                Double.parseDouble(points[2]),
                                Double.parseDouble(points[3]));
            }

            grid.print();
            System.out.println("Average Depth: " + grid.averageDepth());

            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.equals("")) continue;

                String[] points = line.split(" ");
                Point point1 = new Point(Double.parseDouble(points[0]), Double.parseDouble(points[1]));
                Point point2 = new Point(Double.parseDouble(points[2]), Double.parseDouble(points[3]));

                System.out.println("The Points" + grid.comparePoints(point1, point2));
            }

            // Close resources
            sc.close();
        } catch (Exception e) {
            // Handle FileNotFoundExceptions and IOExceptions
            e.printStackTrace();
        }
    }
}

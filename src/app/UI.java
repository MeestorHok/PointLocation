package app;

import ui.Canvas;
import ui.LinesContainer;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Color;

public class UI extends JFrame {
    private Canvas canvas;
    private LinesContainer linesContainer;

    private UI() {
        setTitle("Line Generator");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        addElements();
        pack();
    }

    private void addElements() {
        canvas = new Canvas(this);
        linesContainer = new LinesContainer(canvas);

        add(canvas, BorderLayout.CENTER);
        add(linesContainer, BorderLayout.SOUTH);
    }

    public void updatePointsLabel(String text) {
        linesContainer.updatePointsLabel(text);
    }

    public static void main(String[] args) {
        new UI().setVisible(true);
    }
}

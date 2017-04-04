package ui;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LinesContainer extends JPanel implements ActionListener {
    private Canvas canvas;
    private ArrayList<Guideline> guides;
    private ScrollPanel linesContainer;

    private JLabel pointsLabel;
    private JButton addButton;
    private JButton pointsButton;
    private JButton clearButton;

    public LinesContainer (Canvas canvas) {
        this.canvas = canvas;
        this.guides = new ArrayList<>();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new MatteBorder(10, 10, 10, 10, new Color(223, 223, 223)));
        addControls();
        addContainer();
    }

    private void addControls() {
        JPanel controlsPanel = new JPanel();
        controlsPanel.setBackground(Color.WHITE);
        controlsPanel.setLayout(new BorderLayout());
        controlsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        Box b = Box.createHorizontalBox();

        addButton = new JButton("+");
        addButton.addActionListener(this);

        b.add(addButton);
        b.add(Box.createRigidArea(new Dimension(5, 0)));

        clearButton = new JButton("Reset");
        clearButton.addActionListener(this);

        b.add(clearButton);
        controlsPanel.add(b, BorderLayout.WEST);

        pointsLabel = new JLabel("Compare two points by clicking that ->", SwingConstants.CENTER);
        controlsPanel.add(pointsLabel, BorderLayout.CENTER);

        pointsButton = new JButton("Compare Points");
        pointsButton.addActionListener(this);
        controlsPanel.add(pointsButton, BorderLayout.EAST);

        add(controlsPanel);
    }

    private void addContainer() {
        linesContainer = new ScrollPanel();

        JScrollPane scroller = new JScrollPane(linesContainer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroller.setBorder(new MatteBorder(10, 0, 0, 0, new Color(223, 223, 223)));
        scroller.getViewport().setBackground(new Color(223, 223, 223));

        add(scroller);
    }

    private void addLine() {
        canvas.disableClicking();
        updatePointsLabel("Compare two points by clicking that ->");

        // First, create a new line controller
        LineController newLine = new LineController(Math.max(guides.size(), canvas.grid.getLines().size()), canvas);

        // Then add it to the list of all
        guides.add(newLine.guide);

        // Then add it to the UI
        linesContainer.add(newLine);

        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.pack();
    }

    // Add all the lines in the canvas to the grid
    private void buildLines() {
        for (Guideline gl : guides) {
            canvas.grid.addLine(gl.getLine());
        }

        updatePointsLabel("Click in two places to compare the points.");
        canvas.enableClicking();

        clearAll(false);
    }

    private void clearAll(boolean resetGrid) {
        if (resetGrid) {
            canvas.grid.reset();
            canvas.disableClicking();
            updatePointsLabel("Compare two points by clicking that ->");
        }

        linesContainer.removeAll();
        guides = new ArrayList<>(); // empty GUI controls of guides
        canvas.guides.clear(); // empty drawn guidelines
        canvas.repaint();

        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.pack();
    }

    public void updatePointsLabel(String text) {
        pointsLabel.setText(text);
        pointsLabel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();

        if (source == addButton) {
            addLine();
        } if (source == pointsButton) {
            buildLines();
        } else if (source == clearButton) {
            clearAll(true);
        }
    }

    // Inner class to allow implementation of Scrollable
    private class ScrollPanel extends JPanel implements Scrollable {
        ScrollPanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        }

        @Override
        public Dimension getPreferredScrollableViewportSize() {
            int minWidth = (int) Math.min(getPreferredSize().getWidth(), 320);
            int minHeight = (int) Math.min(getPreferredSize().getHeight(), 320);
            return new Dimension(minWidth, minHeight);
        }

        @Override
        public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
            return 0;
        }

        @Override
        public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
            return 0;
        }

        @Override
        public boolean getScrollableTracksViewportWidth() {
            return false;
        }

        @Override
        public boolean getScrollableTracksViewportHeight() {
            return false;
        }
    }
}

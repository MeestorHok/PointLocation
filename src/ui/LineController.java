package ui;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Color;
import java.awt.Dimension;

public class LineController extends JPanel implements ChangeListener {
    private final int id;
    Guideline guide;
    private Canvas canvas;

    private static final Dimension PREF_SIZE = new Dimension(200, 150);
    private final int MAX_HORIZ_POSITION;
    private final int MAX_VERT_POSITION;

    private JPanel colorBox;
    private JSlider xStartSlider;
    private JSlider yStartSlider;
    private JSlider xEndSlider;
    private JSlider yEndSlider;
    private JSlider redSlider;
    private JSlider greenSlider;
    private JSlider blueSlider;

    private int red;
    private int green;
    private int blue;
    private double startPosX = 0.0;
    private double startPosY = 0.0;
    private double endPosX = 1.0;
    private double endPosY = 1.0;

    public LineController(int id, Canvas canvas) {
        this.id = id;
        this.canvas = canvas;

        MAX_HORIZ_POSITION = canvas.getWidth();
        MAX_VERT_POSITION = canvas.getHeight();

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(Color.WHITE);
        setBorder(new MatteBorder(0, 0, 10, 0, new Color(223, 223, 223)));

        addLabel();
        addStartControls();
        addEndControls();
        addColorControls();

        this.guide = new Guideline(id, new Color(red, green, blue), startPosX, startPosY, endPosX, endPosY);
        this.canvas.guides.put(id, this.guide); // add this guide to the list of guides
    }

    private void addLabel() {
        JPanel namePanel = new JPanel();
        namePanel.setBackground(Color.WHITE);
        namePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));

        JLabel lineLabel = new JLabel("Line #" + id, SwingConstants.CENTER);
        namePanel.add(lineLabel);

        add(namePanel);

        add(Box.createVerticalStrut(10));
        add(new JSeparator(JSeparator.VERTICAL));
        add(Box.createVerticalStrut(10));
    }

    private void addStartControls() {
        JPanel startPanel = new JPanel();
        startPanel.setBackground(Color.WHITE);
        startPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        startPanel.setPreferredSize(PREF_SIZE);
        startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));

        JLabel startLabel = new JLabel("Starting Point");
        startPanel.add(leftAlign(startLabel));

        JLabel xLabel = new JLabel("X");
        xStartSlider = new JSlider(JSlider.HORIZONTAL, 0, MAX_HORIZ_POSITION, 0);
        xStartSlider.addChangeListener(this);
        xStartSlider.setBackground(Color.WHITE);

        startPanel.add(leftAlign(xLabel));
        startPanel.add(xStartSlider);
        startPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel yLabel = new JLabel("Y");
        yStartSlider = new JSlider(JSlider.HORIZONTAL, 0, MAX_VERT_POSITION, 0);
        yStartSlider.addChangeListener(this);
        yStartSlider.setBackground(Color.WHITE);

        startPanel.add(leftAlign(yLabel));
        startPanel.add(yStartSlider);

        add(startPanel);

        add(Box.createVerticalStrut(10));
        add(new JSeparator(JSeparator.VERTICAL));
        add(Box.createVerticalStrut(10));
    }

    private void addEndControls() {
        JPanel endPanel = new JPanel();
        endPanel.setBackground(Color.WHITE);
        endPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        endPanel.setPreferredSize(PREF_SIZE);
        endPanel.setLayout(new BoxLayout(endPanel, BoxLayout.Y_AXIS));

        JLabel endLabel = new JLabel("Ending Point");
        endPanel.add(leftAlign(endLabel));

        JLabel xLabel = new JLabel("X");
        xEndSlider = new JSlider(JSlider.HORIZONTAL, 0, MAX_HORIZ_POSITION, MAX_HORIZ_POSITION);
        xEndSlider.addChangeListener(this);
        xEndSlider.setBackground(Color.WHITE);

        endPanel.add(leftAlign(xLabel));
        endPanel.add(xEndSlider);
        endPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel yLabel = new JLabel("Y");
        yEndSlider = new JSlider(JSlider.HORIZONTAL, 0, MAX_VERT_POSITION, MAX_VERT_POSITION);
        yEndSlider.addChangeListener(this);
        yEndSlider.setBackground(Color.WHITE);

        endPanel.add(leftAlign(yLabel));
        endPanel.add(yEndSlider);

        add(endPanel);

        add(Box.createVerticalStrut(10));
        add(new JSeparator(JSeparator.VERTICAL));
        add(Box.createVerticalStrut(10));
    }

    private void addColorControls() {
        JPanel colorPanel = new JPanel();
        colorPanel.setBackground(Color.WHITE);
        colorPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        colorPanel.setPreferredSize(PREF_SIZE);
        colorPanel.setLayout(new BoxLayout(colorPanel, BoxLayout.Y_AXIS));

        JLabel colorLabel = new JLabel("Color");
        colorPanel.add(leftAlign(colorLabel));

        JPanel colorControlPanel = new JPanel();
        colorControlPanel.setBackground(Color.WHITE);
        colorControlPanel.setLayout(new BoxLayout(colorControlPanel, BoxLayout.X_AXIS));

        redSlider = new JSlider(JSlider.VERTICAL, 0, 255, red);
        redSlider.addChangeListener(this);
        redSlider.setBackground(Color.WHITE);
        JLabel redLabel = new JLabel("Red");

        colorControlPanel.add(redSlider);
        colorControlPanel.add(bottomAlign(redLabel));
        colorControlPanel.add(Box.createRigidArea(new Dimension(20, 0)));

        greenSlider = new JSlider(JSlider.VERTICAL, 0, 255, green);
        greenSlider.addChangeListener(this);
        greenSlider.setBackground(Color.WHITE);
        JLabel greenLabel = new JLabel("Green");

        colorControlPanel.add(greenSlider);
        colorControlPanel.add(bottomAlign(greenLabel));
        colorControlPanel.add(Box.createRigidArea(new Dimension(20, 0)));

        blueSlider = new JSlider(JSlider.VERTICAL, 0, 255, blue);
        blueSlider.addChangeListener(this);
        blueSlider.setBackground(Color.WHITE);
        JLabel blueLabel = new JLabel("Blue");

        colorControlPanel.add(blueSlider);
        colorControlPanel.add(bottomAlign(blueLabel));

        colorPanel.add(colorControlPanel);

        colorBox = new JPanel();
        colorBox.setBorder(new MatteBorder(10, 0, 0, 0, new Color(red, green, blue)));
        colorBox.setBackground(new Color(red, green, blue));

        colorPanel.add(colorBox);

        add(colorPanel);
    }

    // Helper function to left align elements
    private Box leftAlign (JComponent comp) {
        Box b = Box.createHorizontalBox();
        b.add(Box.createRigidArea(new Dimension(5, 0)));
        b.add(comp);
        b.add(Box.createHorizontalGlue());
        return b;
    }

    // Helper function to bottom align elements
    private Box bottomAlign (JComponent comp) {
        Box b = Box.createVerticalBox();
        b.add(Box.createVerticalGlue());
        b.add(comp);
        b.add(Box.createRigidArea(new Dimension(0, 5)));
        return b;
    }

    // Helper function to update color
    private void updateColor() {
        Color newColor = new Color(red, green, blue);

        guide.setColor(newColor);

        colorBox.setBorder(new MatteBorder(10, 0, 0, 0, newColor));
        colorBox.setBackground(newColor);
        colorBox.repaint();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();

        if (source == xStartSlider) {
            startPosX = xStartSlider.getValue() / (double) MAX_HORIZ_POSITION;
            yStartSlider.setEnabled(xStartSlider.getValue() == 0.0 || xStartSlider.getValue() == MAX_HORIZ_POSITION);
        } else if (source == yStartSlider) {
            startPosY = yStartSlider.getValue() / (double) MAX_VERT_POSITION;
            xStartSlider.setEnabled(yStartSlider.getValue() == 0.0 || yStartSlider.getValue() == MAX_VERT_POSITION);
        } else if (source == xEndSlider) {
            endPosX = xEndSlider.getValue() / (double) MAX_HORIZ_POSITION;
            yEndSlider.setEnabled(xEndSlider.getValue() == 0.0 || xEndSlider.getValue() == MAX_HORIZ_POSITION);
        } else if (source == yEndSlider) {
            endPosY = yEndSlider.getValue() / (double) MAX_VERT_POSITION;
            xEndSlider.setEnabled(yEndSlider.getValue() == 0.0 || yEndSlider.getValue() == MAX_VERT_POSITION);
        } else if (source == redSlider) {
            red = source.getValue();
            updateColor();
        } else if (source == greenSlider) {
            green = source.getValue();
            updateColor();
        } else if (source == blueSlider) {
            blue = source.getValue();
            updateColor();
        }

        guide.updateLine(startPosX, startPosY, endPosX, endPosY);
        canvas.repaint();
    }
}

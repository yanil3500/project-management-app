import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main extends JPanel implements KeyListener, MouseListener, MouseMotionListener {

    public static int WIDTH = 1024;
    public static int HEIGHT = 768;
    Board board;
    JLabel[] LanePanels;
    Lane[] Lanes;
    Lane startLane;
    Lane endLane;
    //Keeps track of selected or dragged panel
    Panel clickedPanel;
    //Index of the dragged panel to allow panel to be deleted from start Lane
    int clickedPanelIndex;
    int diffX;
    int diffY;

    public Main() {
        board = new Board(WIDTH, HEIGHT);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setLayout(null);
        LanePanels = new JLabel[3];
        Lanes = board.getLanes();
        if (!ProgramStateManager.getInstance().doesPreviousStateExist()) {
            //hardcoding Tasks for now
            Task task1 = new Task("Task1 Test");
            Task task2 = new Task("Task2 Test");
            Task task3 = new Task("Task3 Test");
            Task task4 = new Task("Task4 Test");
            Task task5 = new Task("Task5 Test");
            Task task6 = new Task("Task6 Test");
            Panel p1 = new Panel(task1);
            Panel p2 = new Panel(task2);
            Panel p3 = new Panel(task3);
            Panel p4 = new Panel(task4);
            Panel p5 = new Panel(task5);
            Panel p6 = new Panel(task6);
            Lanes[0].addPanel(p1);
            Lanes[1].addPanel(p2);
            Lanes[2].addPanel(p3);
            Lanes[0].addPanel(p4);
            Lanes[1].addPanel(p5);
            Lanes[2].addPanel(p6);

            for (int i = 0; i < 3; i++) {
                LanePanels[i] = new JLabel();
                add(LanePanels[i]);
                LanePanels[i].setLocation(WIDTH / 13 + i * 4 * WIDTH / 13, WIDTH / 13);
                LanePanels[i].setSize(3 * WIDTH / 13, HEIGHT - (2 * WIDTH / 13));

                List<Panel> panels = Lanes[i].getPanels();
                for (Panel p : panels) {
                    add(p);
                    p.addMouseListener(this);
                    p.addMouseMotionListener(this);
                }
            }

        } else {
            HashMap<String, Lane> lanes = board.getLaneMappings();
            //Loads panels from disk
            ArrayList<Panel> panelsFromDisk = ProgramStateManager.getInstance().load();
            //Adds each panel back to its respective lane object
            panelsFromDisk.stream().forEach(panel -> lanes.get(panel.getLaneName()).addPanel(panel));
            for (int i = 0; i < 3; i++) {
                LanePanels[i] = new JLabel();
                add(LanePanels[i]);
                LanePanels[i].setLocation(WIDTH / 13 + i * 4 * WIDTH / 13, WIDTH / 13);
                LanePanels[i].setSize(3 * WIDTH / 13, HEIGHT - (2 * WIDTH / 13));
                List<Panel> panels = Lanes[i].getPanels();
                panels.stream().forEach(panel -> {
                    add(panel);
                    panel.addMouseListener(this);
                    panel.addMouseMotionListener(this);
                });
            }

        }
        board.updatePanels();
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Main mainInstance = new Main();
        frame.add(mainInstance);
        frame.pack();
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        board.draw(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        for (Panel p : startLane.getPanels()) {
            if (e.getSource() == p) {
                clickedPanel = p;
                System.out.println("clicked " + clickedPanel);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

        startLane = laneFinder(e);
        List<Panel> panels = startLane.getPanels();
        int i = 0;
        for (Panel panel : panels) {
            if (e.getSource() == panel) {
                clickedPanel = panel;
                clickedPanelIndex = i;
                //difference for drag
                diffX = (int) Math.round(e.getPoint().getX() - panel.getBounds().getX());
                diffY = (int) Math.round(e.getPoint().getY() - panel.getBounds().getY());
            }
            i++;
        }
    }

    private Lane laneFinder(MouseEvent e) {

        Point mousePoint = e.getLocationOnScreen();
        Lane foundLane = null;

        //checks which lane mouse press occurred in and assigns it to startLane
        for (int i = 0; i < 3; i++) {
            JLabel lane = LanePanels[i];
            double yBound = lane.getBounds().getY();
            double xBound = lane.getBounds().getX();
            double height = lane.getBounds().getHeight();
            double width = lane.getBounds().getWidth();

            if (yBound < mousePoint.getY() && (yBound + height) > mousePoint.getY() && xBound < mousePoint.getX() && (xBound + width) > mousePoint.getX()) {
                foundLane = Lanes[i];
            }
        }
        return foundLane;
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        clickedPanel = null;
        board.updatePanels();
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        endLane = laneFinder(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (clickedPanel != null) {
            //repainting drag using setX() setY() panel methods
            clickedPanel.setX(e.getX() - diffX);
            clickedPanel.setY(e.getY() - diffY);
            repaint();
            //redraws board with changes in lanes
            if (startLane != endLane && endLane != null) {
                startLane.deletePanel(clickedPanelIndex);
                endLane.addPanel(clickedPanel);
                board.updatePanels();
                repaint();
                clickedPanel = null;
                startLane = null;
                endLane = null;
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}

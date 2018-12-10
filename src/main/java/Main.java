import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Main extends JPanel implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    public static int WIDTH = 1024;
    public static int HEIGHT = 768;
    Board board;
    JLabel[] betweenLanes;
    Lane[] Lanes;
    //The starting lane of a panel being dragged
    Lane startLane;
    //The ending lane of a panel being dragged
    Lane endLane;
    //Keeps track of selected or dragged panel
    Panel clickedPanel;
    //Index of the dragged panel to allow panel to be deleted from start Lane
    int clickedPanelIndex;
    int diffX;
    int diffY;

    private Main() {
        board = new Board(WIDTH, HEIGHT);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setLayout(null);
        Lanes = board.getLanes();
        Lane toDoLane = board.getLaneMappings().get("ToDo");

        //Adding 'Add Task' button to To Do Lane
        JButton button = addButtonToLane(toDoLane, "Add Task");
        button.addActionListener(e -> {
            //Presents modal for adding a new task
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            String taskName = new AddTaskForm(parentFrame, true).getTitle();
            //FOR TESTING PURPOSES
            String note = new AddNoteForm(parentFrame, true).getNote();
            //Create new task and adds it to To Do Lane
            if (taskName != null) {
                Panel newTask = Panel.createPanel(taskName);
                //FOR TESTING PURPOSES
                if (note != null) {
                    newTask.getTask().addNote(note);
                }
                toDoLane.addPanel(newTask);
                add(newTask);
                newTask.addMouseListener(this);
                newTask.addMouseMotionListener(this);
                board.updatePanels();
            }
        });

        //adding mouse listeners to area between lanes
        betweenLanes = new JLabel[2];
        for (int i = 0; i < 2; i++) {
            betweenLanes[i] = new JLabel();
            betweenLanes[i].setLocation(4 * WIDTH / 13 + i * 4 * WIDTH / 13, WIDTH / 13);
            betweenLanes[i].setSize(WIDTH / 13, HEIGHT - (2 * WIDTH / 13));
            this.add(betweenLanes[i]);
            betweenLanes[i].addMouseListener(this);
            betweenLanes[i].addMouseMotionListener(this);
        }

        //updating board with any existing tasks
        //if (!ProgramStateManager.getInstance().doesPreviousStateExist()) {
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
            List<Panel> panels = Lanes[i].getPanels();
            for (Panel panel : panels) {
                add(panel);
                panel.addMouseListener(this);
                panel.addMouseMotionListener(this);
            }
        }


        /*} else {
            HashMap<String, Lane> lanes = board.getLaneMappings();
            //Loads panels from disk
            ArrayList<Panel> panelsFromDisk = ProgramStateManager.getInstance().load();
            //Adds each panel back to its respective lane object
            panelsFromDisk.stream().forEach(panel -> lanes.get(panel.getLaneName()).addPanel(panel));
            for (int i = 0; i < 3; i++) {
                List<Panel> panels = Lanes[i].getPanels();
                for (Panel panel : panels) {
                    add(panel);
                    panel.addMouseListener(this);
                    panel.addMouseMotionListener(this);
                }
            }

        }*/
        addMouseWheelListener(this);
        board.updatePanels();
        SmsSender.sendSMS("sending");
    }

    private JButton addButtonToLane(Lane lane, String title) {
        //Adding border layout; Used for setting up button location.
        JPanel borderLayoutPanel = new JPanel();
        borderLayoutPanel.setLocation(lane.getxCoord(), lane.getyWidth());

        //Setting the size of the panel equal to lane header.
        borderLayoutPanel.setBounds(lane.getxCoord(), lane.getyCoord(), lane.getxWidth(), lane.getMargin() / 2);
        borderLayoutPanel.setLayout(new BorderLayout());

        //Makes the panel transparent
        borderLayoutPanel.setOpaque(false);

        //Adds panel to parent container
        add(borderLayoutPanel);

        JButton button = new JButton(title);
        Font font = new Font("Arial", Font.PLAIN, 8);

        //Gets the width and height of the button's text
        int stringWidth = button.getFontMetrics(font).stringWidth(title);
        int stringHeight = button.getFontMetrics(font).getAscent();

        //Sets the button size according to its text size
        button.setPreferredSize(new Dimension((int) (stringWidth * 2.5), stringHeight));

        //Adds button to borderLayoutPanel; The button will hug the right wall of the container
        borderLayoutPanel.add(button, BorderLayout.EAST);

        return button;
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
            }
        }

        Point mousePoint = e.getPoint();

        //Double click for viewing task.
        if (mousePoint.getX() < (clickedPanel.getWidth() - 2) && mousePoint.getX() > 0 && mousePoint.getY() < (clickedPanel.getHeight() - 2) && mousePoint.getY() > 0) {
            if (e.getClickCount() == 2) {
                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                //FOR TESTING PURPOSES
                ViewTaskForm vtf = new ViewTaskForm(parentFrame, true, clickedPanel.getTask());
                ViewNotesForm vnf = new ViewNotesForm(parentFrame, true, clickedPanel.getTask());
                repaint();
            }

        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

        startLane = laneFinder(e.getLocationOnScreen());
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

    private Lane laneFinder(Point mousePoint) {

        Lane foundLane = null;
        int i = 0;
        //checks which lane mouse press occurred in and assigns it to startLane

        if (mousePoint.getX() < betweenLanes[0].getBounds().getX()) {
            foundLane = Lanes[0];
        } else if (mousePoint.getX() < betweenLanes[1].getBounds().getX()) {
            foundLane = Lanes[1];
        } else if (mousePoint.getX() > betweenLanes[1].getBounds().getX()) {
            foundLane = Lanes[2];
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
        //endLane = laneFinder(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {

        if (e.getSource() == betweenLanes[0]) {
            if (e.getPoint().getX() < 40) {
                endLane = Lanes[0];
            }
            if (e.getPoint().getX() > 40) {
                endLane = Lanes[1];
            }
        }
        if (e.getSource() == betweenLanes[1]) {
            if (e.getPoint().getX() < 40) {
                endLane = Lanes[1];
            }
            if (e.getPoint().getX() > 40) {
                endLane = Lanes[2];
            }
        }
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

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

        int notches = e.getWheelRotation();
        System.out.println(notches);
        Lane l = laneFinder(e.getPoint());

        if (l != null) {

            if (PanelsInsideLane(l, notches)) {
                if (notches < 0) {
                    for (Panel p : l.getPanels()) {
                        p.setY(p.getYPos() + 10);
                        System.out.println("Scrolled up");
                    }
                } else if (notches > 0) {
                    for (Panel p : l.getPanels()) {
                        p.setY(p.getYPos() - 10);
                        System.out.println("Scrolled down");
                    }
                }
            }
            repaint();
        }
    }

    public boolean PanelsInsideLane(Lane l, int notches) {

        if (notches > 0) {
            int panelPos = 0;
            if (l.getPanels() != null) {
                for (Panel p : l.getPanels()) {
                    panelPos = p.getYPos();
                }
            }
            if (panelPos > (l.getyCoord() + l.getMargin())) {
                return true;
            } else {
                return false;
            }
        } else if (notches < 0) {
            int panelPos = HEIGHT;
            if (l.getPanels() != null) {
                panelPos = (l.getPanels().get(0).getYPos()) + (l.getPanels().get(0).getYBound());
            }
            if (panelPos < (l.getyCoord() + l.getyWidth())) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}

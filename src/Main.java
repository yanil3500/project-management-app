import javax.swing.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.awt.Point;

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

	//creating JLabels in same spot as Lanes and adding MouseListener
	for (int i = 0; i < 3; i++) {
	    LanePanels[i] = new JLabel();
	    add(LanePanels[i]);
	    LanePanels[i].setLocation(WIDTH/13 + i*4*WIDTH/13, WIDTH/13);
	    LanePanels[i].setSize(3*WIDTH/13, HEIGHT - (2*WIDTH/13));
	    LanePanels[i].addMouseListener(this);
	    LanePanels[i].addMouseMotionListener(this);

	    for(Panel p : Lanes[i].getPanels()) {
		add(p);
		//not need for now since listener listens to Lane
		//instead using y position to determine which panel for now
		//p.addMouseListener(this);
	    }
	}
	board.updatePanels();
    }
    
    public static void main(String[] args) {

	System.out.println("testing");
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

	System.out.println("clicked");
        for (Panel p : startLane.getPanels()) {
	    if (e.getSource() == p) {
		clickedPanel = p;
	    }
	}
    }

    @Override
    public void mousePressed(MouseEvent e) {

	if (e.getSource() == LanePanels[0]) {
	    System.out.println("to do lane");
	    pressedHelper(board.getLanes()[0], e.getPoint());
	    startLane = Lanes[0];
	} else if (e.getSource() == LanePanels[1]) {
	    System.out.println("in progress lane");
	    pressedHelper(board.getLanes()[1], e.getPoint());
	    startLane = Lanes[1];
	} else if (e.getSource() == LanePanels[2]) {
	    System.out.println("completed lane");
	    pressedHelper(board.getLanes()[2], e.getPoint());
	    startLane = Lanes[2];
	}
    }

    private void pressedHelper(Lane lane, Point mousePoint) {

	List<Panel> panels = lane.getPanels();
	int i = 0;
	for (Panel panel : panels) {
	    System.out.println(panel);
	    System.out.println(mousePoint.getX());
	    System.out.println(panel.getBounds().getX());
	    //Checks if a mouse is in within an existing panel in the lane
	    if(panel.getBounds().getY() > mousePoint.getY() && panel.getBounds().getY() - panel.getBounds().getHeight() < mousePoint.getY()) {
		clickedPanel = panel;
		clickedPanelIndex = i;
		//Difference for drag
		diffX = (int) Math.round(mousePoint.getX() - panel.getBounds().getX());
		diffY = (int) Math.round(mousePoint.getY() - panel.getBounds().getY());
		System.out.println("Found" + panel);
	    }
	    i++;
	}	
    }

    @Override
    public void mouseReleased(MouseEvent e) {

	clickedPanel = null;
	board.updatePanels();
	repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

	//to determine end lane
	if (e.getSource() == LanePanels[0]) {
	    endLane = Lanes[0];
	} else if (e.getSource() == LanePanels[1]) {
	    endLane = Lanes[1];
	} else if (e.getSource() == LanePanels[2]) {
	    endLane = Lanes[2];
	}
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
	if(clickedPanel != null) {
	    //repainting drag using setX() setY() panel methods
	    clickedPanel.setX(e.getX() - diffX);
	    clickedPanel.setY(e.getY() - diffY);
	    repaint();
	    //redraws board with changes in lanes
	    if(!startLane.equals(endLane) && endLane!=null) {
		endLane.addPanel(clickedPanel);
		startLane.deletePanel(clickedPanelIndex);
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

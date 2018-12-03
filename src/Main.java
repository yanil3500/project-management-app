import javax.swing.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Main extends JPanel implements KeyListener, MouseListener, MouseMotionListener {

    public static int WIDTH = 1024;
    public static int HEIGHT = 768;
    Board board;
    JLabel[] LanePanels;
    Lane[] Lanes;
    Lane startLane;
    Lane endLane;
    Panel clickedPanel;

    public Main() {
	board = new Board(WIDTH, HEIGHT);
	this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
	this.setLayout(null);
	LanePanels = new JLabel[3];
	Lanes = board.getLanes();
	
	for (int i = 0; i < 3; i++) {
	    LanePanels[i] = new JLabel();
	    add(LanePanels[i]);
	    LanePanels[i].setLocation(WIDTH/13 + i*4*WIDTH/13, WIDTH/13);
	    LanePanels[i].setSize(3*WIDTH/13, HEIGHT - (2*WIDTH/13));
	    LanePanels[i].addMouseListener(this);

	    for(Panel p : Lanes[i].getPanels()) {
		add(p);
		p.addMouseListener(this);
	    }
	}
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

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

	if (e.getSource() == LanePanels[0]) {
	    System.out.println("to do lane");
	    startLane = Lanes[0];
	} else if (e.getSource() == LanePanels[1]) {
	    System.out.println("in progress lane");
	    startLane = Lanes[1];
	} else if (e.getSource() == LanePanels[2]) {
	    System.out.println("completed lane");
	    startLane = Lanes[2];
	}
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}

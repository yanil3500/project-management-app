import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;


public class Lane extends JComponent implements Drawable {

    /**
     * Used for keeping track of all panels used by all of the Lane instances.
     * This will be used for persisting all of those panels to disk.
     */
    private static LinkedHashSet<Panel> allPanels = new LinkedHashSet<>();

    private ArrayList<Panel> panels;
    private int boardWidth;
    private int boardHeight;
    private String title;
    private int margin;
    private int xCoord;
    private int yCoord;
    private int xWidth;
    private int yWidth;
    private String laneName;
    private ObservableHelper observable;

    public Lane(String laneName) {
        this.laneName = laneName;

        this.panels = new ArrayList<>();
        //Instantiates an ObservableHelper
        observable = new ObservableHelper();
        //Adds the ProgramStateManager as an observer to monitor any changes in this lane.
        observable.addObserver(ProgramStateManager.getInstance());

    }


    public Lane() {
        this("");
    }

    public String getLaneName() {
        return laneName;
    }

    /**
     * Gets all of the panels from across all lanes instances.
     *
     * @return HashSet
     */
    public static LinkedHashSet<Panel> getAllPanels() {
        return allPanels;
    }

    /**
     * Adds panel to lane.
     *
     * @param panel
     */

    public void addPanel(Panel panel) {
        //Indicates that the lane has been changed.
        observable.setChanged();

        //Gives it name to each panel.
        panel.setLaneName(this.laneName);

        //Adds the task attached to the given panel to the set of all tasks.
        Task.observe(panel.getTask());

        //Adds panel to list of panels belonging to this lane.
        panels.add(panel);

        //Adds panel to the set of all panels.
        allPanels.add(panel);

        //This lane notifies the ProgramStateManager of its latest change.
        observable.notifyObservers();

        //Indicates that the lane has already notified the ProgramStateManager of its latest change.
        observable.clearChanged();
    }

    public void deletePanel(int index) {
        if (index < 0 || index > panels.size()) {
            throw new IndexOutOfBoundsException();
        }

        //Indicates that the lane has been changed.
        observable.setChanged();

        //Remove from list of available panels.
        Panel toDelete = panels.remove(index);

        //Remove toDelete from the set of all panels.
        allPanels.remove(toDelete);

        //Notifies the ProgramStateManager that the lane has been changed.
        observable.notifyObservers();

        //Indicates that the lane has already notified the ProgramStateManager of its latest change.
        observable.clearChanged();
    }

    public void setCoordinates(int initWidth, int initHeight, int numLane, String lane) {

        boardWidth = initWidth;
        boardHeight = initHeight;
        margin = boardWidth / 13;
        xCoord = margin + numLane * 4 * margin;
        xWidth = 3 * margin;
        yCoord = margin;
        yWidth = boardHeight - 2 * margin;
        title = lane;

    }

    public ArrayList<Panel> getPanels() {
        return panels;
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public int getxWidth() {
        return xWidth;
    }

    public int getyWidth() {
        return yWidth;
    }

    public int getMargin() {
        return margin;
    }

    @Override
    public void draw(Graphics g) {

        Graphics gr = g.create();
        gr.setColor(Color.WHITE);
        gr.fillRect(xCoord, yCoord, xWidth, yWidth);
        gr.setColor(Color.GRAY);
        gr.fillRect(xCoord, yCoord, xWidth, margin / 2);
        gr.setColor(Color.BLACK);

        Font font = new Font("Arial", Font.BOLD, 20);
        int fontX = gr.getFontMetrics(font).stringWidth(title);
        int fontY = gr.getFontMetrics(font).getAscent();
        gr.setFont(font);
        gr.drawString(title, xCoord + (xWidth - fontX) / 2, yCoord + (margin / 2 + fontY) / 2);

        gr.setClip(xCoord, yCoord + margin/2, xWidth, yWidth - margin/2);
        for (Panel p : panels) {
            p.draw(gr);
        }
        gr.dispose();
    }
}

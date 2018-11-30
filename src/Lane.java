import java.awt.*;
import java.util.*;


/**
 * NOTE about Observable: This class extends the Observable class so that these objects can notify the ProgramStateManager
 * that their state has changed.
 * The Oracle docs for the Observable class were used during implementation.
 * https://docs.oracle.com/javase/8/docs/api/index.html?java/util/Observer.html
 */
public class Lane extends Observable implements Drawable {

    /**
     * Used for keeping track of all panels used by all of the Lane instances.
     * This will be used for persisting all of those panels to disk.
     */
    private static HashSet<Panel> allPanels = new HashSet<>();

    private ArrayList<Panel> panels;

    public Lane() {
        this.panels = new ArrayList<>();
        //Adds the ProgramStateManager as an observer to monitor any changes in this lane.
        this.addObserver(ProgramStateManager.getInstance());
    }

    /**
     * Gets all of the panels from across all lanes instances.
     *
     * @return HashSet
     */
    public static HashSet<Panel> getAllPanels() {
        return allPanels;
    }

    /**
     * Adds panel to lane.
     *
     * @param panel
     */
    public void addPanel(Panel panel) {
        //Indicates that the lane has been changed.
        setChanged();

        //Adds panel to list of panels belonging to this lane.
        panels.add(panel);

        //Adds panel to the set of all panels.
        allPanels.add(panel);

        //This lane notifies the ProgramStateManager of its latest change.
        notifyObservers();

        //Indicates that the lane has already notified the ProgramStateManager of its latest change.
        clearChanged();
    }

    public void deletePanel(int index) {
        if (index < 0 || index > panels.size()) {
            throw new IndexOutOfBoundsException();
        }

        //Indicates that the lane has been changed.
        setChanged();

        //Remove from list of available panels.
        Panel toDelete = panels.remove(index);

        //Remove toDelete from the set of all panels.
        allPanels.remove(toDelete);

        //Notifies the ProgramStateManager that the lane has been changed.
        notifyObservers();

        //Indicates that the lane has already notified the ProgramStateManager of its latest change.
        clearChanged();
    }

    @Override
    public void draw(Graphics g) {

    }
}

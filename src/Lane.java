import java.awt.*;
import java.util.ArrayList;

public class Lane implements Drawable {

    /**
     * Used for keeping track of all panels used by all of the Lane instances.
     * This will be used for persisting all of those panels to disk.
     */
    private static ArrayList<Panel> allPanels = new ArrayList<>();

    private ArrayList<Panel> panels;

    public Lane() {
        this.panels = new ArrayList<>();
    }

    public void addPanel(Panel panel) {

    }

    public void deletePanel(int index) {

    }

    @Override
    public void draw(Graphics g) {

    }
}

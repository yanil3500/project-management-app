import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

public class Lane implements Drawable {

    /**
     * Used for keeping track of all panels used by all of the Lane instances.
     * This will be used for persisting all of those panels to disk.
     */
    private static HashSet<Panel> allPanels = new HashSet<>();

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

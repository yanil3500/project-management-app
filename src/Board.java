import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;

public class Board implements Drawable, Serializable {
    private Lane toDo;
    private Lane inProgress;
    private Lane completed;
    private HashMap<String, Lane> lanes;
    private int width;
    private int height;

    public Board(int initWidth, int initHeight) {
        width = initWidth;
        height = initHeight;
        lanes = new HashMap<>();
        createLanes();
        updatePanels();
    }

    public void createLanes() {
        toDo = new Lane("ToDo");
        lanes.put(toDo.getLaneName(), toDo);
        inProgress = new Lane("InProgress");
        lanes.put(inProgress.getLaneName(), inProgress);
        completed = new Lane("Completed");
        lanes.put(completed.getLaneName(), completed);
        toDo.setCoordinates(width, height, 0, "To Do");
        inProgress.setCoordinates(width, height, 1, "In Progress");
        completed.setCoordinates(width, height, 2, "Completed");
    }

    public Lane[] getLanes() {
        Lane[] allLanes = {toDo, inProgress, completed};
        return allLanes;
    }

    public HashMap<String, Lane> getLaneMappings() {
        return lanes;
    }

    public void updatePanels() {
        int margin = width / 13;
        int panelWidth = 8 * (3 * margin) / 10;
        int panelHeight = margin;
        int i = 0;
        for (Panel p : toDo.getPanels()) {
            p.updatePosition(margin + (3 * margin) / 10, 2 * margin + i * 3 * margin / 2, panelWidth, panelHeight);
            i += 1;
        }
        i = 0;
        for (Panel p : inProgress.getPanels()) {
            p.updatePosition(5 * margin + (3 * margin) / 10, 2 * margin + i * 3 * margin / 2, panelWidth, panelHeight);
            i += 1;
        }
        i = 0;
        for (Panel p : completed.getPanels()) {
            p.updatePosition(9 * margin + (3 * margin) / 10, 2 * margin + i * 3 * margin / 2, panelWidth, panelHeight);
            i += 1;
        }
    }

    @Override
    public void draw(Graphics g) {

        //g.setColor(Color.LIGHT_GRAY);
        //g.fillRect(0, 0, width, height);
        /*draw Lanes
        toDo.draw(g);
        inProgress.draw(g);
        completed.draw(g);*/
    }
}

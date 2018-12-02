import java.awt.Graphics;
import java.awt.Color;
import java.util.*;

public class Board implements Drawable {
    private Lane toDo;
    private Lane inProgress;
    private Lane completed;
    private int width;
    private int height;

    public Board(int initWidth, int initHeight) {
	width = initWidth;
	height = initHeight;
	toDo = new Lane();
	inProgress = new Lane();
	completed = new Lane();
	toDo.setCoordinates(width, height, 0, "To Do");
	inProgress.setCoordinates(width, height, 1, "In Progress");
	completed.setCoordinates(width, height, 2, "Completed");
    }

    public Lane[] getLanes() {
	Lane[] allLanes = {toDo, inProgress, completed};
	return allLanes;
    }
	
    @Override
    public void draw(Graphics g) {

	g.setColor(Color.LIGHT_GRAY);
	g.fillRect(0, 0, width, height);
	toDo.draw(g);
	inProgress.draw(g);
	completed.draw(g);
    }
}

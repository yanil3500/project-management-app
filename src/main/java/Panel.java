import javax.swing.*;
import java.awt.*;
import java.io.Serializable;


/**
 * NOTE about Serializable: This class implements Serializable so that these types of objects can be converted into a byte stream
 * and saved onto disk.
 */

class ButtonDimensions implements Serializable {
    int buttonLeftWall;
    int buttonRightWall;
    int buttonCeiling;
    int buttonFloor;


    public ButtonDimensions(int x, int y, int width, int height) {
        this.buttonLeftWall = x;
        this.buttonRightWall = y;
        this.buttonCeiling = width;
        this.buttonFloor = height;
    }

    @Override
    public String toString() {
        return "ButtonDimensions{" +
                "buttonLeftWall=" + buttonLeftWall +
                ", buttonRightWall=" + buttonRightWall +
                ", buttonCeiling=" + buttonCeiling +
                ", buttonFloor=" + buttonFloor +
                '}';
    }
}

public class Panel extends JLabel implements Drawable, Serializable {
    private Task task;
    /**
     * The lane to which this panel currently belongs.
     */
    private String laneName;
    private int xPos;
    private int yPos;
    private int width;
    private int height;
    private ButtonDimensions viewTaskButtonDimensions;
    private ButtonDimensions viewNotesButtonDimensions;

    public Panel(Task task) {
        this.task = task;
        this.task.setPanel(this);

    }

    //Copy constructor
    public Panel(Panel panel) {
        this.task = panel.task;
        this.laneName = panel.laneName;
        this.xPos = panel.xPos;
        this.yPos = panel.yPos;
        this.width = panel.width;
        this.height = panel.height;
    }

    public void updatePosition(int x, int y, int initWidth, int initHeight) {
        xPos = x;
        yPos = y;
        width = initWidth;
        height = initHeight;
        this.setLocation(xPos, yPos);
        this.setSize(width, height);

    }

    public String getLaneName() {
        return laneName;
    }

    public void setLaneName(String laneName) {
        this.laneName = laneName;
    }

    public void setX(int x) {
        xPos = x;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public int getXBound() {
        return width;
    }

    public int getYBound() {
        return height;
    }

    public void setY(int y) {
        yPos = y;
    }

    @Override
    public void draw(Graphics g) {
        //drawing Panel
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(xPos, yPos, width, height);

        //drawing Task information Strings
        String title = task.getTitle();
        String author = task.getAuthor();
        g.setColor(Color.BLACK);
        Font font = new Font("TimesRoman", Font.PLAIN, 10);
        int titleRise = g.getFontMetrics(font).getAscent();
//        int titleWidth = g.getFontMetrics(font).stringWidth(title);

        g.setFont(font);
        if (title != null) {
            g.drawString(title, xPos + 5, yPos + 5 + titleRise);
        }

        //button font
        Font buttonFont = new Font("Arial", Font.BOLD, 8);
        g.setFont(buttonFont);

        //gets 'View Notes' string dimensions
        String viewNotes = "Add/View/Edit Notes";
        int viewNotesRise = g.getFontMetrics(buttonFont).getAscent();
        int viewNotesWidth = g.getFontMetrics(buttonFont).stringWidth(viewNotes);
        g.setFont(buttonFont);
        int viewNotesX = xPos + 6;
        int viewNotesY = yPos + height - viewNotesRise - 2;

        //draw View Notes 'button' area
        int viewNotesButtonX = viewNotesX - 2;
        int viewNotesButtonY = viewNotesY - 10;
        int viewNotesButtonHeight = viewNotesRise + 5;
        int viewNotesButtonWidth = viewNotesWidth + 5;
        g.setColor(Color.ORANGE);
        g.fillRect(viewNotesButtonX, viewNotesButtonY, viewNotesButtonWidth, viewNotesButtonHeight);
        viewNotesButtonDimensions = new ButtonDimensions(
                viewNotesButtonX, //left wall of button
                viewNotesButtonX + viewNotesButtonWidth, //right wall of button
                viewNotesButtonY, //ceiling of button
                viewNotesButtonY + viewNotesButtonHeight //floor of button
        );

        //gets 'View Task' string dimensions
        String viewTask = "View/Edit Task";
        int viewTaskRise = g.getFontMetrics(buttonFont).getAscent();
        int viewTaskStringWidth = g.getFontMetrics(buttonFont).stringWidth(viewTask);
        int viewTaskX = xPos + width - viewTaskStringWidth - 3 - 3;
        int viewTaskY = yPos + height - viewTaskRise - 2;

        //draw View Task 'button' area
        int viewTaskButtonX = viewTaskX - 2;
        int viewTaskButtonY = viewTaskY - 10;
        int viewTaskButtonHeight = viewTaskRise + 5;
        int viewTaskButtonWidth = viewTaskStringWidth + 5;
        g.setColor(Color.PINK);
        g.fillRect(viewTaskButtonX, viewTaskButtonY, viewTaskButtonWidth, viewTaskButtonHeight);
        viewTaskButtonDimensions = new ButtonDimensions(
                viewTaskButtonX, //left wall of button
                viewTaskButtonX + viewTaskButtonWidth, //right wall of button
                viewTaskButtonY, //ceiling of button
                viewTaskButtonY + viewTaskButtonHeight); //floor of button

        g.setColor(Color.BLACK);

        //draw 'View Task' string
        g.drawString(viewTask, viewTaskX, viewTaskY);
        //draw 'View Notes' string
        g.drawString(viewNotes, viewNotesX, viewNotesY);
    }

    public ButtonDimensions getViewTaskButtonDimensions() {
        return viewTaskButtonDimensions;
    }

    public ButtonDimensions getViewNotesButtonDimensions() {
        return viewNotesButtonDimensions;
    }

    /**
     * NOTE: the equals method was generated by the IntelliJ IDE.
     * Compares this panel to the specified object. The result is true if and only if
     * the argument is not null, is a Panel instance, and the specified object represents this panel.
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Panel panel = (Panel) o;

        return task != null ? task.equals(panel.task) : panel.task == null;
    }

    /**
     * Gets the task associated with this panel.
     *
     * @return a Task.
     */
    public Task getTask() {
        return this.task;
    }


    /**
     * Creates a panel instance with given task.
     *
     * @param taskTitle
     * @return Panel.
     */
    public static Panel createPanel(String taskTitle) {
        if (taskTitle == null || taskTitle.equals("")) {
            return null;
        }

        Task task = new Task(taskTitle);
        Panel panel = new Panel(task);

        return panel;
    }

}

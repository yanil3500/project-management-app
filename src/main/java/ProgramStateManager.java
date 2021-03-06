import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;


/**
 * The <code>ProgramStateManager</code> is used for saving/loading the program's state to/from disk.
 */

/**
 * NOTE on Serialization and Deserialization: This Stackoverflow question was referenced while this class's methods were being defined. The bit about
 * using null as way to indicate that there are no more objects to read in was very helpful.
 * Link: https://stackoverflow.com/questions/12684072/eofexception-when-reading-files-with-objectinputstream
 * <p>
 * NOTE on Observer: This class implements the Observer interface so that it can be notified of any changes to
 * observable objects; In this case, the ProgramStateManager will monitor tasks on the board and will save their state if changed.
 * <p>
 * The Oracle docs for the Observer interface were used during implementation.
 * Link: https://docs.oracle.com/javase/8/docs/api/index.html?java/util/Observer.html
 * The following tutorial was also looked at during implementation:
 * Link: https://examples.javacodegeeks.com/core-java/util/observer/java-util-observer-example/
 */
public class ProgramStateManager implements Observer {

    /**
     * Single instance of ProgramStateManager
     */
    private final static ProgramStateManager shared = new ProgramStateManager();

    /**
     * The name for the file that will be used to save/load the program's state.
     */
    private final String FILE_NAME = "program-state.txt";

    /**
     * A File object that will be used to check if the program's state has been saved previously.
     */
    private final File file = new File(FILE_NAME);

    /**
     * The default constructor is being set to private so that no other ProgramStateManager instances can be created.
     */
    private ProgramStateManager() {
    }

    /**
     * Gets the singleton instance of ProgramStateManager
     *
     * @return
     */
    public static ProgramStateManager getInstance() {
        return shared;
    }

    /**
     * Converts the objects into a byte stream and writes it to a file.
     *
     * @param objects
     */
    public void save(List<Object> objects) {
        /* Adding the null marks the end of objects to save to disk. This null will be useful when it comes
        time reload the program's state.*/

        if (objects == null) {
            throw new NullPointerException("The objects list argument is null.");
        }

        objects.add(null);
        try (FileOutputStream fos = new FileOutputStream(FILE_NAME); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            for (Object object : objects) {
                oos.writeObject(object);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the byte stream from disk, feeds the byte stream to an ObjectInputStream, reads in an object from
     * the ObjectInputStream and adds it to a list until the end of the file is reached.
     *
     * @return a ArrayList<Panel>.
     */
    public ArrayList<Panel> load() {
        ArrayList<Panel> fromFile = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(FILE_NAME); ObjectInputStream ois = new ObjectInputStream(fis)) {
            Object o = ois.readObject();
            while (o != null) {
                //if o == null, then the end of the file has been reached.
                fromFile.add((Panel) o);
                o = ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return fromFile;
    }

    /**
     * Checks if there is a saved state from previous run.
     *
     * @return boolean.
     */
    public boolean doesPreviousStateExist() {
        return file.isFile();
    }

    /**
     * This method is called anytime an Observable object is changed.
     *
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        //Creating new Panel instances will, effectively, remove any mouse listeners before serialization.
        List<Object> panels = Lane.getAllPanels().stream()
                .map(panel -> new Panel(panel))
                .collect(Collectors.toList());
        save(panels);
    }

}

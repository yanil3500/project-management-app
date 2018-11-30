import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * The <code>ProgramStateManager</code> is used for saving/loading the program's state to/from a file (disk).
 */
public class ProgramStateManager {

    /**
     * The name for the file that will be used to save/load the program's state.
     */
    private static String FILE_NAME = "program-state.txt";

    /**
     * Converts the objects into a byte stream and writes it to a file (disk).
     *
     * @param objects
     */
    public static void save(List<Object> objects) {
        /* Adding the null marks the end of objects to save to disk. This null will be useful when it comes
        time reload the program's state.*/
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
     * Loads the byte stream from disk, reads every object and adds it to a list until the end of the file is reached.
     *
     * @return a List<Object>.
     */
    public static List<Object> load() {
        List<Object> fromProgramStateFile = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(FILE_NAME); ObjectInputStream ois = new ObjectInputStream(fis)) {
            Object o = ois.readObject();
            while (o != null) {
                //if o == null, then the end of the file has been reached.
                fromProgramStateFile.add(o);
                o = ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return fromProgramStateFile;
    }
}

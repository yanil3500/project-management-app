import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The <code>Tester</code> class is used for testing the code written for this project.
 */
public class Tester {

    public static void main(String[] args) {
        testPanelSavesToDisk();
    }

    /**
     * Tests that Panel objects and their member variables are correctly being saved/loaded correctly to/from disk.
     */
    public static void testPanelSavesToDisk() {

        //Creates a dummy task for Panel and sets its fields.
        Task t1 = new Task("Work on cracking the coding interview.");
        t1.setAuthor("Berner Herzog");
        t1.editAssignedTo("Rusty Shackleford");
        t1.editDeadline("12 Dec 2018");
        t1.editDescription("Work on a practice problem everyday!");

        //Creates panel and saves it to disk.
        Panel p1 = new Panel(t1);
        ProgramStateManager.save(Stream.of(p1).collect(Collectors.toList()));


        //Loads panel from disk.
        List<Object> fromProgramStateFile = ProgramStateManager.load();
        Panel p2 = (Panel) fromProgramStateFile.get(0);


        //Gets the task from the Panel that was saved to disk.
        Task t2 = p2.getTask();

        System.out.println("(Task objects) t1 == t2: " + assertFunc(t1, t2));
        System.out.println("(Panel objects) p1 == p2: " + assertFunc(p1, p2));
    }

    /**
     * Checks that given objects are equal to one another.
     * @param o1
     * @param o2
     * @return
     */
    public static boolean assertFunc(Object o1, Object o2) {
        return o1.equals(o2);
    }
}

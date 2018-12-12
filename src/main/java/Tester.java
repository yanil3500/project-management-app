import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The <code>Tester</code> class is used for testing the code written for this project.
 * To test any aspect of the code, define a static method for the aspect of code under test.
 * Every static method used for testing code should follow this naming convention: test<aspect of code>.
 * Call the newly defined static method in main to run the test.
 */
public class Tester {

    /**
     * The default constructor is being set to private so that no Tester instances can be created.
     */
    private Tester() {
    }

    public static void main(String[] args) {
        testPanelSavesAndLoadsToAndFromDisk();
        testPanelsAreSavedWhenMovingThroughLanes();
        testPanelsAreSavedWhenAssociatedTasksChanged();
    }

    /**
     * Tests that Panel objects and their member variables are correctly being saved/loaded correctly to/from disk.
     */
    private static void testPanelSavesAndLoadsToAndFromDisk() {

        //Creates a dummy task for Panel.
        Task t1 = new Task("Work on cracking the coding interview.");

        //Creates panel and saves it to disk.
        Panel p1 = new Panel(t1);
        t1.setAuthor("Berner Herzog");
        t1.editAssignedTo("Rusty Shackleford");
        t1.editDeadline("12 Dec 2018");
        t1.editDescription("Work on a practice problem everyday!");
        ProgramStateManager.getInstance().save(Stream.of(p1).collect(Collectors.toList()));

        //Panel reference that will hold the object loaded from disk.
        Panel p2 = null;

        //Checks if there is a saved state from a previous run.
        if (ProgramStateManager.getInstance().doesPreviousStateExist()) {
            //Loads panel from disk.
            List<Panel> fromProgramStateFile = ProgramStateManager.getInstance()
                    .load()
                    .stream()
                    .map(object -> (Panel) object)
                    .collect(Collectors.toList());
            p2 = fromProgramStateFile.get(0);
        }

        //Gets the task from the Panel that was saved to disk.
        Task t2 = p2.getTask();

        System.out.println("testPanelSavesAndLoadsToAndFromDisk");
        System.out.println("(Task objects) t1 == t2: " + assertEquals(t1, t2));
        System.out.println("(Panel objects) p1 == p2: " + assertEquals(p1, p2));
    }

    /**
     * Tests that Panel objects are saved to disk when moving through lanes.
     */
    private static void testPanelsAreSavedWhenMovingThroughLanes() {
        //Creates dummy tasks for Panel and sets its fields.
        Task t1 = new Task("Work on cracking the coding interview.");
        t1.setAuthor("Berner Herzog");
        t1.editAssignedTo("Rusty Shackleford");
        t1.editDeadline("12 Dec 2018");
        t1.editDescription("Work on a practice problem everyday!");

        Task t2 = new Task("Apply for internships.");
        t2.setAuthor("Berner Herzog");
        t2.editAssignedTo("Berner Herzog");
        t2.editDeadline("12 Dec 2018");
        t2.editDescription("Look on indeed.com for internship opportunities!");

        //Creates dummy panels
        Panel p1 = new Panel(t1);
        Panel p2 = new Panel(t2);

        //Create dummy lanes
        Lane toDo = new Lane();
        Lane inProgress = new Lane();
        Lane completed = new Lane();

        System.out.println("testPanelsAreSavedWhenMovingThroughLanes: ");
        //Move the panels through the lanes.
        toDo.addPanel(p1);
        toDo.addPanel(p2);
        toDo.deletePanel(0);
        inProgress.addPanel(p1);
        toDo.deletePanel(0);
        inProgress.addPanel(p2);
        inProgress.deletePanel(1);
        completed.addPanel(p2);
        inProgress.deletePanel(0);
        completed.addPanel(p1);
    }

    /**
     * Tests that the Panel are saved to disk when their associated task is changed.
     */
    private static void testPanelsAreSavedWhenAssociatedTasksChanged() {
        //Creates dummy tasks for Panel and sets its fields.
        Task t1 = new Task("Work on cracking the coding interview.");
        Task t2 = new Task("Apply for internships.");

        //Creates dummy panels
        Panel p1 = new Panel(t1);
        Panel p2 = new Panel(t2);

        //Create dummy lanes
        Lane toDo = new Lane();

        //Add panels to lane
        toDo.addPanel(p1);
        toDo.addPanel(p2);

        //Updates tasks
        System.out.println("testPanelsAreSavedWhenAssociatedTasksChanged: ");
        t1.setAuthor("Berner Herzog");
        t1.editAssignedTo("Rusty Shackleford");
        t1.editDeadline("12 Dec 2018");
        t1.editDescription("Work on a practice problem everyday!");

        t2.setAuthor("Berner Herzog");
        t2.editAssignedTo("Berner Herzog");
        t2.editDeadline("12 Dec 2018");
        t2.editDescription("Look on indeed.com for internship opportunities!");
    }

    /**
     * Checks that the given objects are equal to one another.
     *
     * @param o1
     * @param o2
     * @return
     */
    private static boolean assertEquals(Object o1, Object o2) {
        return o1.equals(o2);
    }
}

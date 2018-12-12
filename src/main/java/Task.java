import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;


/**
 * A <code>Task</code> represents a piece of work that a user has set for themselves (or somebody else) to complete.
 */

/**
 * NOTE about Serializable: This class implements Serializable so that these types of objects can be converted into a byte stream
 * and saved onto disk.
 * <p>
 * NOTE about Observable: This class extends the Observable class so that these types objects can notify the ProgramStateManager
 * that their state has changed.
 * <p>
 * The Oracle docs for the Observable class were used during implementation.
 * https://docs.oracle.com/javase/8/docs/api/java/util/Observable.html
 */
public class Task extends Observable implements Serializable {

    /**
     * The max length of a title's task; The task must be 140 characters or less in length.
     * This limit is completely arbitrary.
     */
    final static int TITLE_LENGTH_LIMIT = 140;

    /**
     * The name of the task's creator. This field can be left blank.
     */
    private String author;

    /**
     * The title of the task.
     */
    private String title;

    /**
     * The name of the user the task is assigned to. This field can be left blank.
     */
    private String assignedTo;

    /**
     * An explanation of what the task entails. This field can be left blank.
     */
    private String description;

    /**
     * The date for the when the task should be complete.
     */
    private String deadline;

    /**
     * Information relevant to the task like what is keeping the task from getting completed, who has worked on this
     * task previously, etc.
     */
    private ArrayList<Note> notes;

    /**
     * Represents when the task was created and last updated.
     */
    private Metadata metadata;

    /**
     * The panel to which this task is attached.
     */
    private Panel panel;

    /**
     * Represents whether a reminder SMS has been sent.
     */
    private boolean reminded;

    /**
     * Phone numbers to which reminders will be sent.
     */
    private ArrayList<String> phoneNumbers;

    /**
     * This is the default constructor. The only argument required to create a Task instance is its title.
     *
     * @param title
     */

    public Task(String title) {
        this.title = title;
        this.metadata = new Metadata();
        this.notes = new ArrayList<>();
        this.phoneNumbers = new ArrayList<>();
        this.reminded = false;
        //Adds the ProgramStateManager as an observer to monitor any changes in this task.
        observe(this);
    }

    /**
     * Adds the ProgramStateManager as an observer to monitor any changes in the given task.
     *
     * @param task
     */
    public static void observe(Task task) {
        //Reinstates the ProgramStateManager as an observer.
        task.addObserver(ProgramStateManager.getInstance());
    }

    /**
     * Gets the name of the task's creator.
     *
     * @return String.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the name of the task's creator.
     *
     * @param author
     */
    public void setAuthor(String author) {
        this.author = author;
        //Update the last modified date to indicate that the task has been changed
        updateMetadata();
    }

    /**
     * Gets the task's title.
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Changes the task's title.
     *
     * @param title
     */
    public void editTitle(String title) {
        this.title = title;
        //Update the last modified date to indicate that the task has been changed
        updateMetadata();
    }

    /**
     * Gets the name of the user assigned to the task.
     *
     * @return
     */
    public String getAssignedTo() {
        return assignedTo;
    }

    /**
     * Changes who the task is assigned to.
     *
     * @param assignedTo
     */
    public void editAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
        //Update the last modified date to indicate that the task has been changed
        updateMetadata();
    }

    /**
     * Get the task's description.
     *
     * @return String.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Changes the task's description.
     *
     * @param description
     */
    public void editDescription(String description) {
        this.description = description;
        //Update the last modified date to indicate that the task has been changed
        updateMetadata();
    }

    /**
     * Gets the task's deadline.
     *
     * @return String.
     */
    public String getDeadline() {
        return deadline;
    }

    /**
     * Changes the task's deadline
     *
     * @param deadline
     */
    public void editDeadline(String deadline) {
        this.deadline = deadline;
        //Update the last modified date to indicate that the task has been changed
        updateMetadata();
    }

    /**
     * Gets whether a reminder has been sent
     *
     * @return boolean
     */
    public boolean getReminded() {
        return reminded;
    }

    /**
     * Sets whether a reminder has been sent
     *
     * @param sent
     */
    public void setReminded(boolean sent) {
        reminded = sent;
    }

    /**
     * Get the panel to which this task is attached.
     *
     * @return Panel.
     */
    public Panel getPanel() {
        return panel;
    }

    /**
     * Changes the panel to which the task is attached.
     *
     * @param panel
     */
    public void setPanel(Panel panel) {
        this.panel = panel;
    }

    /**
     * Gets the metadata describing when the task object was created and last updated.
     *
     * @return Metadata.
     */
    public Metadata getMetadata() {
        return new Metadata(metadata);
    }

    /**
     * Updates the last modified date to reflect that the task has been changed.
     */
    private void updateMetadata() {

        //Indicates that the task has been changed.
        setChanged();

        this.metadata.updateLastModified();

        //This task notifies the ProgramStateManager of its latest change.
        notifyObservers();

        //Indicates that this task has already notified the ProgramStateManager of its latest change.
        clearChanged();
    }

    /**
     * Adds a note to the list of notes.
     *
     * @param text
     */
    public void addNote(String text) {
        if (text == null || text.equals("")) {
            return;
        } else {
            Note note = new Note(text);
            this.notes.add(note);
            //Update the last modified date to indicate that the task has been changed
            updateMetadata();
        }

    }

    /**
     * This is a convenience method; Adds a note and who wrote it to the list of notes.
     *
     * @param author
     * @param text
     */
    public void addNote(String author, String text) {
        if (text == null || text.equals("")) {
            return;
        } else {
            Note note = new Note(author, text);
            this.notes.add(note);
            //Update the last modified date to indicate that the task has been changed
            updateMetadata();
        }

    }

    /**
     * Removes the note at the specified location in the list of notes.
     *
     * @param index
     */
    public void deleteNote(int index) {
        if (index < 0 || index > notes.size()) {
            throw new IndexOutOfBoundsException();
        }

        //Remove note
        notes.remove(index);
        //Update the last modified date to indicate that the task has been changed
        updateMetadata();
    }

    /**
     * Will set the author for the note at the given index if and only if the setAuthor boolean is true;
     * Otherwise, the text of the note will be edited.
     *
     * @param text
     * @param index
     * @param setAuthor
     */
    public void editNote(String text, int index, boolean setAuthor) {
        if (index < 0 || index > notes.size()) {
            throw new IndexOutOfBoundsException();
        }
        Note note = notes.get(index);
        if (setAuthor) {
            note.setAuthor(text);
        } else {
            note.editNote(text);
        }
        //Update the last modified date to indicate that the task has been changed
        updateMetadata();
    }

    /**
     * Get the list of notes associated with the task.
     *
     * @return
     */
    public ArrayList<Note> getNotes() {
        return notes;
    }

    /**
     * Adds given phone number to the list of phone numbers.
     *
     * @param phoneNumber
     */
    public void addPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return;
        }

        phoneNumbers.add(phoneNumber);
        updateMetadata();
    }

    /**
     * Remove the phone number at the given index.
     *
     * @param index
     */
    public void deletePhoneNumber(int index) {
        if (index < 0 || index > phoneNumbers.size()) {
            throw new IndexOutOfBoundsException();
        }
        //deletes phone number at given index
        phoneNumbers.remove(index);
        updateMetadata();

    }

    /**
     * Gets the list of phone numbers associated with this task.
     *
     * @return
     */
    public ArrayList<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    /**
     * NOTE: the equals method was generated by the IntelliJ IDE.
     * Compares this task to the specified object. The result is true if and only if
     * the argument is not null, is a Task instance, and the object represents the same object.
     *
     * @param o
     * @return boolean.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (getAuthor() != null ? !getAuthor().equals(task.getAuthor()) : task.getAuthor() != null) return false;
        if (!getTitle().equals(task.getTitle())) return false;
        if (getAssignedTo() != null ? !getAssignedTo().equals(task.getAssignedTo()) : task.getAssignedTo() != null)
            return false;
        if (getDescription() != null ? !getDescription().equals(task.getDescription()) : task.getDescription() != null)
            return false;
        if (getDeadline() != null ? !getDeadline().equals(task.getDeadline()) : task.getDeadline() != null)
            return false;
        return notes != null ? notes.equals(task.notes) : task.notes == null;
    }

    /**
     * NOTE: the hashCode method was generated by the IntelliJ IDE.
     * Returns a hash code for this task.
     *
     * @return int.
     */
    @Override
    public int hashCode() {
        int result = getAuthor() != null ? getAuthor().hashCode() : 0;
        result = 31 * result + getTitle().hashCode();
        result = 31 * result + (getAssignedTo() != null ? getAssignedTo().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getDeadline() != null ? getDeadline().hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Task{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", assignedTo='" + assignedTo + '\'' +
                ", description='" + description + '\'' +
                ", deadline='" + deadline + '\'' +
                ", notes=" + notes +
                ", metadata=" + metadata +
                ", panel=" + panel +
                ", reminded=" + reminded +
                ", phoneNumbers=" + phoneNumbers +
                '}';
    }
}

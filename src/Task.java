import java.io.Serializable;
import java.util.ArrayList;

/**
 * A <code>Task</code> represents a piece of work that a user has set for themselves (or somebody else) to complete.
 */

/**
 * NOTE: This class implements Serializable so that these types of objects can be converted into a byte stream
 * and saved onto disk.
 */
public class Task implements Serializable {
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

    public Task(String title) {
        this.title = title;
        this.metadata = new Metadata();
        this.notes = new ArrayList<>();
    }

    /**
     * Gets the name of the task's creator.
     *
     * @return
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
     * @return
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
     * @return
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
        this.metadata.updateLastModified();
    }

    /**
     * Adds a note to the list of notes.
     *
     * @param text
     */
    public void addNote(String text) {
        if (text == null) {
            return;
        } else if (text.equals("")) {
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
        if (text == null || author == null) {
            return;
        } else if (text.equals("") || author.equals("")) {
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

}

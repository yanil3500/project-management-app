/**
 * An instance of the <code>Note</code> class represents a note related to a specific task, and can provide more
 * information about the nature/current status/etc. of the task.
 */
class Note {

    /**
     * The name of the note's creator. This field can be left blank.
     */
    private String author;

    /**
     * The information the author wishes to leave about a task.
     */
    private String text;

    public Note(String text) {
        this.text = text;
    }

    /**
     * This is convenience constructor. If the author the note decides to provide their name, then this constructor will
     * be used for creating note.
     *
     * @param author
     * @param text
     */
    public Note(String author, String text) {
        this.author = author;
        this.text = text;
    }
}

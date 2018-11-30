import java.io.Serializable;

/**
 * An instance of the <code>Note</code> class represents a note related to a specific task, and can provide more
 * information about the nature/current status/etc. of the task.
 */

/**
 * NOTE: This class implements Serializable so that these types of objects can be converted into a byte stream
 * and saved onto disk.
 */

class Note implements Serializable {

    /**
     * The name of the note's creator. This field can be left blank.
     */
    private String author;

    /**
     * The information the author wishes to leave about a task.
     */
    private String text;

    /**
     * This is the default constructor. The only argument required to create a Note instance is its text.
     *
     * @param text
     */
    public Note(String text) {
        this.text = text;
    }

    /**
     * This is convenience constructor. If the author of the note decides to provide their name, then this constructor will
     * be used for creating note.
     *
     * @param author
     * @param text
     */
    public Note(String author, String text) {
        this.author = author;
        this.text = text;
    }

    /**
     * Gets the name of the note's author.
     *
     * @return
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Gets the note's text.
     *
     * @return
     */
    public String getText() {
        return text;
    }

    public void editNote(String text){
        if (text == null || text.equals("")) {
            return;
        }
        this.text = text;
    }

    public void setAuthor(String text){
        if (text == null || text.equals("")){
            return;
        }
        this.author = author;
    }
}

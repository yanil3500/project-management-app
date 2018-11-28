import java.util.ArrayList;

public class Task {
    private String author;
    private String title;
    private String assignedTo;
    private String description;
    private String deadline;
    private ArrayList<Note> notes;
    private Metadata metadata;

    public Task(String title){
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void editAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void editTitle(String title) {
        this.title = title;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void editAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getDescription() {
        return description;
    }

    public void editDescription(String description) {
        this.description = description;
    }

    public String getDeadline() {
        return deadline;
    }

    public void editDeadline(String deadline) {
        this.deadline = deadline;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void editMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public void addNote(String note){

    }

    public void deleteNote(int index){

    }

}

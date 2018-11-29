import java.util.Calendar;
import java.util.Date;

class Metadata {

    /**
     * The date the Task was created.
     */
    private Date dateCreated;

    /**
     * The date for the last time the Task was updated.
     */
    private Date lastModified;

    //Here is the Stackoverflow question that was looked at for how to use the Calendar class.
    //Link: https://stackoverflow.com/questions/5165428/how-to-set-time-to-a-date-object-in-java
    public Metadata() {
        this.dateCreated = Calendar.getInstance().getTime();
        this.lastModified = Calendar.getInstance().getTime();
    }

    //Copy constructor
    public Metadata(Metadata metadata) {
        this.dateCreated = metadata.getDateCreated();
        this.lastModified = metadata.getLastModified();
    }


    /**
     * Gets the date the Task was created.
     *
     * @return a Date.
     */
    public Date getDateCreated() {
        return new Date(dateCreated.getTime());
    }

    /**
     * Gets the date for the last time the Task was updated.
     *
     * @return a Date.
     */
    public Date getLastModified() {
        return new Date(lastModified.getTime());
    }

    /**
     * Updates lastModified to indicate the state of the Task has changed.
     */
    public void updateLastModified() {
        this.lastModified = new Date(System.nanoTime());
    }

}

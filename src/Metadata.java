import java.util.Calendar;
import java.util.Date;

class Metadata {

    private Date dateCreated;
    private Date lastModified;

    //Here is the Stackoverflow question that was looked for how to use the Calendar class.
    //Link: https://stackoverflow.com/questions/5165428/how-to-set-time-to-a-date-object-in-java
    public Metadata(){
        this.dateCreated = Calendar.getInstance().getTime();
        this.lastModified = Calendar.getInstance().getTime();
    }

    public Date getDateCreated() {
        return new Date(dateCreated.getTime());
    }

    public Date getLastModified() {
        return new Date(lastModified.getTime());
    }

    public void updateLastModified(){
        this.lastModified = new Date(System.nanoTime());
    }

}

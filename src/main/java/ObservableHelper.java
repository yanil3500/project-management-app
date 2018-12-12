import java.io.Serializable;
import java.util.Observable;


/**
 * The <code>ObservableHelper</code> is a subclass of Observable. This subclass exists so other classes that need
 * to make use of Observable functionality don't have to subclass Observable themselves.
 * NOTE about Observable: This class extends the Observable class so that these objects can notify the ProgramStateManager
 * that their state has changed.
 * The Oracle docs for the Observable class were used during implementation.
 * https://docs.oracle.com/javase/8/docs/api/index.html?java/util/Observer.html
 */
public class ObservableHelper extends Observable implements Serializable {
    public ObservableHelper() {
    }

    @Override
    protected synchronized void setChanged() {
        super.setChanged();
    }

    @Override
    protected synchronized void clearChanged() {
        super.clearChanged();
    }
}

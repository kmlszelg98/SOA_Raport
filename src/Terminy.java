import java.sql.Timestamp;

/**
 * Created by Kamil on 11.06.2017.
 */
public class Terminy {

    int ile;
    int strefa;

    public Terminy() {
    }

    public Terminy(int ile, int strefa) {
        this.ile = ile;
        this.strefa = strefa;
    }

    public int getIle() {
        return ile;
    }

    public void setIle(int ile) {
        this.ile = ile;
    }

    public int getStrefa() {
        return strefa;
    }

    public void setStrefa(int strefa) {
        this.strefa = strefa;
    }
}

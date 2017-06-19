import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ticket.Ticket;

import javax.persistence.metamodel.EntityType;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Kamil on 11.06.2017.
 */
public class Database {
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public ArrayList<Ticket> main(Timestamp start, Timestamp end){
        ArrayList<Ticket> tickets = new ArrayList<>();
        final Session session = getSession();

        final Query query = session.createQuery("from Ticket");
        for (Object o : query.list()) {
            Ticket t = (Ticket)o;
            if(t.getStartTime().before(end) && t.getStartTime().after(start)) tickets.add(t);
        }
        return tickets;
    }
}
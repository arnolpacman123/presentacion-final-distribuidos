package connection;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Connection {
    private static Connection instance = new Connection();
    private EntityManagerFactory entityManagerFactory;

    private Connection() {
        entityManagerFactory = Persistence.createEntityManagerFactory("ServerPU");
    }

    public static Connection getInstance() {
        return instance;
    }

    public static void setInstance(Connection instance) {
        Connection.instance = instance;
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

}

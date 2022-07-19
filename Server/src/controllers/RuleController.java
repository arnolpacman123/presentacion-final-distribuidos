package controllers;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import connection.Connection;

import entities.Rules;

public class RuleController {
    public List<Rules> getAll() {
        Query query = entityManager().createQuery("SELECT r FROM Rules r");
        return query.getResultList();
    }

    private EntityManager entityManager() {
        return Connection.getInstance().getEntityManagerFactory().createEntityManager();
    }
}

package controllers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import connection.Connection;

import entities.Datas;

public class DataController {

    public void create(Datas data) {
        EntityManager entityManager = entityManager();
        try {
            entityManager.getTransaction().begin(); // Iniciar la transacción
            entityManager.persist(data);
            entityManager.getTransaction().commit(); // Inserta la transacción
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
    }

    public void edit(Datas data) {
        EntityManager entityManager = entityManager();
        try {
            entityManager.getTransaction().begin(); // Iniciar la transacción
            entityManager.merge(data);
            entityManager.getTransaction().commit(); // Inserta la transacción
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
    }

    public List<Datas> getAll() {
        Query query = entityManager().createQuery("SELECT d FROM Datas d");
        return query.getResultList();
    }

    public void delete(Datas data) {
        EntityManager entityManager = entityManager();
        try {
            entityManager.getTransaction().begin(); // Iniciar la transacción
            entityManager.remove(entityManager.merge(data));
            entityManager.getTransaction().commit(); // Inserta la transacción
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
    }

    private EntityManager entityManager() {
        return Connection.getInstance().getEntityManagerFactory().createEntityManager();
    }
}

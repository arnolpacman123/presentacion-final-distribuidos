package controllers;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import connection.Connection;

import entities.Devices;

public class DeviceController {

    public void save(Devices device) {
        Devices findDevice = getDeviceByIdentifier(device.getIdentifier());
        if (findDevice != null) {
            findDevice.setDate(device.getDate());
            findDevice.setTemperature(device.getTemperature());
            findDevice.setHumidity(device.getHumidity());
            findDevice.setConnected(device.getConnected());
        } else {
            findDevice = device;
        }
        EntityManager entityManager = entityManager();
        try {
            entityManager.getTransaction().begin(); // Iniciar la transacción
            entityManager.merge(findDevice);
            entityManager.getTransaction().commit(); // Inserta la transacción
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
    }

    public Devices getDeviceByIdentifier(String identifier) {
        try {
            String queryString = "SELECT d FROM Devices d WHERE d.identifier = :identifier";
            TypedQuery<Devices> query = entityManager().createQuery(queryString, Devices.class);
            return query.setParameter("identifier", identifier).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    private EntityManager entityManager() {
        return Connection.getInstance().getEntityManagerFactory().createEntityManager();
    }
}

package features.services;

import features.hibernate.HibernateUtil;
import features.spaceTravel.Planet;
import jakarta.persistence.RollbackException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PlanetCrudService {
    public void addPlanet(Planet planet) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
                session.persist(planet);
            transaction.commit();
            System.out.println(String.format("Planet '%s' was successfully added", planet.getName()));
        } catch (RollbackException | HibernateException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Planet getPlanetById(String id) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            return session.get(Planet.class, id);
        } catch (HibernateException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public List<Planet> getPlanetByName(String name) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            String queryString = "from Planet pl WHERE UPPER(pl.name) LIKE UPPER('%" + name + "%')";
            return session.createQuery(queryString, Planet.class).list();
        } catch (HibernateException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public List<Planet> getAllPlanets() {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            return session.createQuery("from Planet", Planet.class).list();
        } catch (HibernateException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void updatePlanet(String id, String newName) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Planet existingPlanet = session.get(Planet.class, id);
            if (existingPlanet == null) {
                System.out.println(String.format("Planet with id '%s' does not exist", id));
            } else {
                existingPlanet.setName(newName);
                Transaction transaction = session.beginTransaction();
                    session.persist(existingPlanet);
                transaction.commit();
                System.out.println(String.format("Planet with id '%s' was successfully updated", id));
            }
        } catch (HibernateException | RollbackException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void removePlanet(String id) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Planet existingPlanet = session.get(Planet.class, id);
            if (existingPlanet == null) {
                System.out.println(String.format("Cannot remove. Planet with id '%s' does not exist", id));
            } else {
                Transaction transaction = session.beginTransaction();
                    session.remove(existingPlanet);
                transaction.commit();
                System.out.println(String.format("Planet with id '%s' was successfully removed", id));
            }
        } catch (HibernateException | RollbackException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}

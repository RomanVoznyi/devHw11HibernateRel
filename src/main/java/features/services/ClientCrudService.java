package features.services;

import features.hibernate.HibernateUtil;
import features.spaceTravel.Client;
import jakarta.persistence.RollbackException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ClientCrudService {

    public void addClient(Client client) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
                session.persist(client);
            transaction.commit();
            System.out.println(String.format("Client '%s' was successfully added", client.getName()));
        } catch (RollbackException | HibernateException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Client getClientById(long id) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            return session.get(Client.class, id);
        } catch (HibernateException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public List<Client> getClientByName(String name) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            String queryString = "from Client c WHERE UPPER(c.name) LIKE UPPER('%" + name + "%')";
            return session.createQuery(queryString, Client.class).list();
        } catch (HibernateException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public List<Client> getAllClients() {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            return session.createQuery("from Client", Client.class).list();
        } catch (HibernateException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void updateName(long id, String newName) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Client existingClient = session.get(Client.class, id);
            if (existingClient == null) {
                System.out.println(String.format("Client with id '%s' does not exist", id));
            } else {
                existingClient.setName(newName);
                Transaction transaction = session.beginTransaction();
                    session.persist(existingClient);
                transaction.commit();
                System.out.println(String.format("Client with id '%s' was successfully updated", id));
            }
        } catch (HibernateException | RollbackException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void removeUser(long id) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Client existingClient = session.get(Client.class, id);
            if (existingClient == null) {
                System.out.println(String.format("Cannot remove. Client with id '%s' does not exist", id));
            } else {
                Transaction transaction = session.beginTransaction();
                    session.remove(existingClient);
                transaction.commit();
                System.out.println(String.format("Client with id '%s' was successfully removed", id));
            }
        } catch (HibernateException | RollbackException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}

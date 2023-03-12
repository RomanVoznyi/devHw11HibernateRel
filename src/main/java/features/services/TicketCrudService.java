package features.services;

import features.hibernate.HibernateUtil;
import features.spaceTravel.Client;
import features.spaceTravel.Planet;
import features.spaceTravel.Ticket;
import jakarta.persistence.Query;
import jakarta.persistence.RollbackException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TicketCrudService {
    public void addTicket(Ticket ticket) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(ticket);
            transaction.commit();
            System.out.println(String.format("Ticket '%s' was successfully added", ticket.getShortInfo()));
        } catch (RollbackException | HibernateException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public Ticket getTicketById(long id) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            return session.get(Ticket.class, id);
        } catch (HibernateException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public List<Ticket> getTicketsFromClientById(long id) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            String queryString = "from Ticket t WHERE t.client.id = " + id;
            return session.createQuery(queryString, Ticket.class).list();
        } catch (HibernateException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public List<Ticket> getTicketsFromPlanetById(String id) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            String queryString = "from Ticket t WHERE t.planet_from.id=:planet_id";
            return session.createQuery(queryString, Ticket.class).setParameter("planet_id", id).list();
        } catch (HibernateException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public List<Ticket> getTicketsToPlanetById(String id) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            String queryString = "from Ticket t WHERE t.planet_to.id=:planet_id";
            return session.createQuery(queryString, Ticket.class).setParameter("planet_id", id).list();
        } catch (HibernateException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public List<Ticket> getAllTickets() {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            return session.createQuery("from Ticket", Ticket.class).list();
        } catch (HibernateException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void changeClientInTicketById(long id, long client_id) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Ticket existingTicket = session.get(Ticket.class, id);
            if (existingTicket == null) {
                System.out.println(String.format("Cannot change. Ticket with id '%s' does not exist", id));
            } else {
                Client client = new ClientCrudService().getClientById(client_id);
                if (client == null) {
                    System.out.println(String.format("Cannot change. Client with id '%s' does not exist", client_id));
                } else {
                    existingTicket.setClient(client_id);
                    Transaction transaction = session.beginTransaction();
                    session.persist(existingTicket);
                    transaction.commit();
                    System.out.println(String.format("Ticket with id '%s' was successfully updated", id));
                }
            }
        } catch (HibernateException | RollbackException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void changePlanetFromInTicketById(long id, String from_planet_id) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Ticket existingTicket = session.get(Ticket.class, id);
            if (existingTicket == null) {
                System.out.println(String.format("Cannot change. Ticket with id '%s' does not exist", id));
            } else if (existingTicket.getPlanet_to().getId().equals(from_planet_id)) {
                System.out.println("Cannot change. Planet 'from' and 'to' should be different");
            } else {
                Planet planet = new PlanetCrudService().getPlanetById(from_planet_id);
                if (planet == null) {
                    System.out.println(String.format("Cannot change. Planet with id '%s' does not exist", from_planet_id));
                } else {
                    existingTicket.setPlanet_from(from_planet_id);
                    Transaction transaction = session.beginTransaction();
                    session.persist(existingTicket);
                    transaction.commit();
                    System.out.println(String.format("Ticket with id '%s' was successfully updated", id));
                }
            }
        } catch (HibernateException | RollbackException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void changePlanetToInTicketById(long id, String to_planet_id) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Ticket existingTicket = session.get(Ticket.class, id);
            if (existingTicket == null) {
                System.out.println(String.format("Cannot change. Ticket with id '%s' does not exist", id));
            } else if (existingTicket.getPlanet_from().getId().equals(to_planet_id)) {
                System.out.println("Cannot change. Planet 'from' and 'to' should be different");
            } else {
                Planet planet = new PlanetCrudService().getPlanetById(to_planet_id);
                if (planet == null) {
                    System.out.println(String.format("Cannot change. Planet with id '%s' does not exist", to_planet_id));
                } else {
                    existingTicket.setPlanet_to(to_planet_id);
                    Transaction transaction = session.beginTransaction();
                    session.persist(existingTicket);
                    transaction.commit();
                    System.out.println(String.format("Ticket with id '%s' was successfully updated", id));
                }
            }
        } catch (HibernateException | RollbackException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void removeTicket(long id) {
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()) {
            Ticket existingTicket = session.get(Ticket.class, id);
            if (existingTicket == null) {
                System.out.println(String.format("Cannot remove. Ticket with id '%s' does not exist", id));
            } else {
                Transaction transaction = session.beginTransaction();
                session.remove(existingTicket);
                transaction.commit();
                System.out.println(String.format("Ticket with id '%s' was successfully removed", id));
            }
        } catch (HibernateException | RollbackException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}

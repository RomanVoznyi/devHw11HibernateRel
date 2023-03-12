package features.spaceTravel;

import features.services.ClientCrudService;
import features.services.PlanetCrudService;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
public class Ticket {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    @Column
    private Timestamp created_at;
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    @ManyToOne
    @JoinColumn(name = "from_planet_id", nullable = false)
    private Planet planet_from;
    @ManyToOne
    @JoinColumn(name = "to_planet_id", nullable = false)
    private Planet planet_to;

    private Ticket() {
    }

    public Ticket(long client_id, String from_planet_id, String to_planet_id) {
        if (from_planet_id == null || to_planet_id == null) {
            throw new IllegalArgumentException("Planet id cannot be 'null'");
        } else if (from_planet_id.equals(to_planet_id)) {
            throw new IllegalArgumentException("Planet 'from' and 'to' should be different");
        } else {
            setClient(client_id);
            setPlanet_from(from_planet_id);
            setPlanet_to(to_planet_id);
            setCreated_at();
        }
    }

    public void setClient(long client_id) {
        Client client = new ClientCrudService().getClientById(client_id);
        if (client == null) {
            throw new IllegalArgumentException("Client with id '" + client_id + "' does not exist");
        }
        this.client = client;
    }

    public void setPlanet_from(String from_planet_id) {
        Planet planet = new PlanetCrudService().getPlanetById(from_planet_id);
        if (planet == null) {
            throw new IllegalArgumentException("Planet with id '" + from_planet_id + "' does not exist");
        }
        this.planet_from = planet;
    }

    public void setPlanet_to(String to_planet_id) {
        Planet planet = new PlanetCrudService().getPlanetById(to_planet_id);
        if (planet == null) {
            throw new IllegalArgumentException("Planet with id '" + to_planet_id + "' does not exist");
        }
        this.planet_to = planet;
    }

    public void setCreated_at() {
        ZonedDateTime nowZoned = ZonedDateTime.now(ZoneId.of("UTC"));
        DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        this.created_at = Timestamp.valueOf(nowZoned.format(dtFormatter));
    }

    @Override
    public String toString() {
        return String.format("Ticket: {id='%s', created_at='%s', client='%s', from='%s', to='%s'}",
                id, created_at, client.getName(), planet_from.getName(), planet_to.getName());
    }

    public String getShortInfo() {
        return String.format("{'%s', '%s --> %s'}",
                client.getName(), planet_from.getName(), planet_to.getName());
    }
}

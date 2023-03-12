package features.spaceTravel;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Client {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @Column(length = 200)
    private String name;

    @OneToMany(mappedBy = "client")
    private Set<Ticket> tickets;

    private Client() {
    }

    public Client(String name) {
        setName(name);
    }

    public void setName(String name) {
        if (name == null || name.length() < 3 || name.length() > 200) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Client: {id='%s', name='%s'}", getId(), getName());
    }
}

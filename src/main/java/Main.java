
import features.hibernate.HibernateUtil;
import features.initDB.DBInitAndPopulateService;
import features.services.TicketCrudService;
import features.spaceTravel.Client;
import features.services.ClientCrudService;
import features.spaceTravel.Planet;
import features.services.PlanetCrudService;
import features.spaceTravel.Ticket;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        DBInitAndPopulateService.getInstance().runMigration();
        runClientServicesTests();
        runPlanetServicesTests();
        runTicketServicesTests();

        HibernateUtil.getInstance().close();
        System.gc();
    }

    private static void runClientServicesTests() {
        ClientCrudService clientService = new ClientCrudService();

        //Add clients
        System.out.println("-------------Adding new clients-------------");
        clientService.addClient(new Client("John Dou 1"));
        clientService.addClient(new Client("John Dou 2"));
        clientService.addClient(new Client("Mary Jane"));

        //Get clients info
        printItem(clientService.getClientById(11L), "Get client by ID '11'");
        printList(clientService.getClientByName("John Dou"), "Get clients by name 'John Dou'");
        printList(clientService.getAllClients(), "Get all clients");

        //Update clients info
        System.out.println("-------------Updating clients-------------");
        clientService.updateName(2, "Boris Johnson");
        clientService.updateName(11, "Bruce Willis");
        printItem(clientService.getClientById(2), "Get modified client with id '2'");
        printItem(clientService.getClientById(11), "Get modified client with id '11'");

        //Remove clients
        System.out.println("-------------Removing clients-------------");
        clientService.removeUser(1);
        clientService.removeUser(11);
        printItem(clientService.getClientById(1), "Trying to find a user with id '1' after removing");
        printItem(clientService.getClientById(11), "Trying to find a user with id '11' after removing");
        printList(clientService.getAllClients(), "Final updated list of clients");
    }

    private static void runPlanetServicesTests() {
        PlanetCrudService planetService = new PlanetCrudService();

        //Add planets
        System.out.println("-------------Adding new planets-------------");
        planetService.addPlanet(new Planet("05JUP", "Jupiter"));
        planetService.addPlanet(new Planet("06SAT", "Saturn"));
        planetService.addPlanet(new Planet("333NAB", "Naboo"));

        //Get planets info
        printItem(planetService.getPlanetById("03EAR"), "Get planet by ID '03EAR'");
        printList(planetService.getPlanetByName("Naboo"), "Get planets by name 'Naboo'");
        printList(planetService.getPlanetByName("vulcan"), "Get planets by name 'vulcan'");
        printList(planetService.getAllPlanets(), "Get all planets");

        //Update planets info
        System.out.println("-------------Updating planets-------------");
        planetService.updatePlanet("04MAR", "Barsoom");
        planetService.updatePlanet("03EAR", "Jasoom");
        planetService.updatePlanet("02VEN", "Cosoom");
        printItem(planetService.getPlanetById("03EAR"), "Get modified planet with id '03EAR'");
        printItem(planetService.getPlanetById("04MAR"), "Get modified planet with id '04MAR'");

        //Remove planets
        System.out.println("-------------Removing planets-------------");
        planetService.removePlanet("06SAT");
        planetService.removePlanet("222NAB");
        planetService.removePlanet("333NAB");
        printItem(planetService.getPlanetById("06SAT"), "Trying to find a planet with id '06SAT' after removing");
        printItem(planetService.getPlanetById("333NAB"), "Trying to find a planet with id '333NAB' after removing");
        printList(planetService.getAllPlanets(), "Final updated list of planets");
    }

    private static void runTicketServicesTests() {
        TicketCrudService ticServ = new TicketCrudService();

        //Add tickets
        System.out.println("-------------Adding new tickets-------------");
        ticServ.addTicket(new Ticket(2, "01MER", "15VUL"));
        ticServ.addTicket(new Ticket(10, "35TAT", "03EAR"));
        try {
            ticServ.addTicket(new Ticket(1000, "05JUP", "15VUL"));
        } catch (IllegalArgumentException ex) {
            System.out.println("!!!Error with adding: " + ex.getMessage());
        }
        try {
            ticServ.addTicket(new Ticket(2, null, "1212"));
        } catch (IllegalArgumentException ex) {
            System.out.println("!!!Error with adding: " + ex.getMessage());
        }
        ticServ.addTicket(new Ticket(13, "02VEN", "04MAR"));

        //Get tickets info
        printItem(ticServ.getTicketById(2), "Get ticket by ID '2'");
        printItem(ticServ.getTicketById(1000), "Get ticket by ID '1000'");
        printList(ticServ.getTicketsFromClientById(2), "Get tickets from client with id '2'");
        printList(ticServ.getTicketsFromPlanetById("02VEN"), "Get tickets from Planet with id '02VEN'");
        printList(ticServ.getTicketsToPlanetById("35TAT"), "Get tickets to Planet with id '35TAT'");
        printList(ticServ.getAllTickets(), "Get all tickets");

        //Update tickets info
        System.out.println("-------------Updating tickets-------------");
        ticServ.changeClientInTicketById(10, 3);
        ticServ.changePlanetFromInTicketById(13, "04MAR");
        ticServ.changePlanetToInTicketById(13, "14VUL");
        printItem(ticServ.getTicketById(10), "Get modified ticket with id '10'");
        printItem(ticServ.getTicketById(13), "Get modified ticket with id '13'");

        //Remove tickets
        System.out.println("-------------Removing tickets-------------");
        ticServ.removeTicket(5);
        ticServ.removeTicket(1);
        ticServ.removeTicket(10);
        printItem(ticServ.getTicketById(5), "Trying to find a ticket with id '5' after removing");
        printItem(ticServ.getTicketById(10), "Trying to find a ticket with id '10' after removing");
        printList(ticServ.getAllTickets(), "Final updated list of tickets");
    }

    private static <T> void printItem(T item, String topic) {
        System.out.println("-------------" + topic + "-------------");
        System.out.println(item + "\n");
    }

    private static <T> void printList(List<T> list, String topic) {
        System.out.println("-------------" + topic + "-------------");
        if (list.size() == 0) {
            System.out.println("The list of data is empty");
        } else {
            for (T item : list) {
                System.out.println(item);
            }
        }
        System.out.println();
    }
}

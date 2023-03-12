package features.initDB;

import org.flywaydb.core.Flyway;

public class DBInitAndPopulateService {
    private static final DBInitAndPopulateService INSTANCE = new DBInitAndPopulateService();

    private DBInitAndPopulateService() {
    }

    public static DBInitAndPopulateService getInstance() {
        return INSTANCE;
    }

    public void runMigration(){
        Flyway flyway = Flyway.configure().dataSource("jdbc:h2:./spaceDB", null, null).load();
        flyway.migrate();
    }
}

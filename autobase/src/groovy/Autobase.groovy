import liquibase.database.Database;
import liquibase.log.LogFactory;
import liquibase.dsl.command.MigrateCommand
import org.liquibase.grails.GrailsFileOpener

class Autobase {

  private static final def log = LogFactory.getLogger();

	static void migrate() {
		try {
			new LiquibaseDsl(generateGroovyChangeSet(), GrailsFileOpenerFactory.fileOpener, database).update(null)
		} catch(Exception e) {
			
		}
	}

	static void generateGroovyChangeSet() {
		// TODO Implement this
	}	

	static Database getDatabase() {
		// TODO Implement this
	}

}

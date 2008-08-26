import liquibase.log.LogFactory;
import liquibase.dsl.command.MigrateCommand
import org.liquibase.grails.GrailsFileOpener

class Autobase {

  private static final def log = LogFactory.getLogger();

	static void migrate() {
		try {
			new LiquibaseDsl(generateGroovyChangeSet(), new GrailsFileOpener(), database).update(null)
		} catch(Exception e) {
			
		}
	}

	


}

import liquibase.*
import liquibase.database.Database;
import liquibase.log.LogFactory;
import liquibase.dsl.command.MigrateCommand
import liquibase.dsl.properties.LbdslProperties
import org.codehaus.groovy.grails.commons.ConfigurationHolder as Config
import grails.util.GrailsUtil

class AutobaseTest {

	static void migrate() {
    println "===EXECUTING AUTOBASE BEGIN==="
    new LbdslProperties()
    println "===EXECUTING AUTOBASE END==="
	}

}

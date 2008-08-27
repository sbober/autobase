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
    new StringBuilder(5)
    println "===CREATING PROPERTIES==="
    println "DEFAULT USER: ${new LbdslProperties().defaultUser}"
    println "===EXECUTING AUTOBASE BEGIN==="
	}

}

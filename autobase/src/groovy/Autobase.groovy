import liquibase.*
import liquibase.database.Database;
import liquibase.log.LogFactory;
import liquibase.dsl.command.MigrateCommand
import liquibase.dsl.properties.LbdslProperties
import org.codehaus.groovy.grails.commons.ConfigurationHolder as Config
import grails.util.GrailsUtil

class Autobase {

  private static final def log = LogFactory.getLogger();

	static void migrate() {
		def fileOpener = GrailsFileOpenerFactory.fileOpener
		def inst = LbdslProperties.instance
		def props = inst.properties
		props[LbdslProperties.DB_DRIVER_PROPERTY] = Config.config.dataSource.driverClassName.toString()
		props[LbdslProperties.DB_USER_PROPERTY] = Config.config.dataSource.username.toString()
		props[LbdslProperties.DB_PASS_PROPERTY] = Config.config.dataSource.password.toString()
		props[LbdslProperties.DB_URL_PROPERTY] = Config.config.dataSource.url.toString()
		Database db = inst.database
		if(fileOpener.getResource("changelog.xml")) {
			new LiquibaseDsl("grails-app/migrations/changelog.xml", fileOpener, db).update(null)
		}
    if("development".equals(GrailsUtil.environment)) {
      new LiquibaseDsl(generateGroovyChangeSet(), new FileSystemFileOpener(), db).update(null)
    } else if(fileOpener.getResource("changelog.groovy")) {
		  new LiquibaseDsl("changelog.groovy", fileOpener, db).update(null)
    } else {
		  // No "changelog.groovy", not in development -- do nothing
    }
	}

	static String generateGroovyChangeSet() {
    def out = new File("grails-app/migrations/changelog.groovy")
    if(out.exists()) { out.delete() }

    // TODO Add preconditions for db type, user?
    File dir = new File("grails-app/migrations/changesets")
    out.text = dir.listFiles().findAll { 
       it.path.toLowerCase().endsWith(".groovy")
    }.sort { a,b -> 
      a.absolutePath <=> b.absolutePath 
    }.inject("dbChangeLog() {\n") { memo, file ->
      "${memo}\tinclude('${file.path}')\n"
    } + "}\n"

    return out.getPath()
	}	

/*	static Database getDatabase() {
		def inst = Props.instance
		def props = inst.properties
		props[Props.DB_DRIVER_PROPERTY] = Config.config.dataSource.driverClassName.toString()
		props[Props.DB_USER_PROPERTY] = Config.config.dataSource.username.toString()
		props[Props.DB_PASS_PROPERTY] = Config.config.dataSource.password.toString()
		props[Props.DB_URL_PROPERTY] = Config.config.dataSource.url.toString()
		return inst.database
	} */

}

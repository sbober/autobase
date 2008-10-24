import liquibase.*
import liquibase.database.Database;
import liquibase.log.LogFactory;
import liquibase.dsl.command.MigrateCommand
import liquibase.dsl.properties.LbdslProperties as Props
import liquibase.parser.groovy.*;
import org.codehaus.groovy.grails.commons.ConfigurationHolder as Config
import org.codehaus.groovy.grails.commons.ApplicationHolder
import grails.util.GrailsUtil
import org.apache.log4j.*;

class Autobase {

  private static final Logger log = Logger.getLogger(Autobase)

	static void migrate() {
    try {
      assignSystemProperties();
      def fileOpener = findFileOpener() 
      log.debug("Using a file opener of type ${fileOpener?.class}")
      Database db = getDatabase();
      if(fileOpener.getResourceAsStream("./migrations/changelog.groovy")) {
        new LiquibaseDsl("./migrations/changelog.groovy", fileOpener, db).update(null)
      } else {
        log.warn("No changelog found")
      }
    } catch(Exception e) {
      GrailsUtil.deepSanitize(e)
      throw e
    }
	}

  static FileOpener findFileOpener() {
    if(ApplicationHolder.application?.isWarDeployed()) {
      return new WarFileOpener()
    } else {
      return new FileSystemFileOpener()
    }
  }

  static void assignSystemProperties() {
    assignSystemProperty("lbdsl.home", new File((String)System.properties["user.home"], (String)".lbdsl").canonicalPath)
  }

  static void assignSystemProperty(String propName, String defValue) {
    System.properties[propName] = System.properties[propName] ?:
                                  Config.config.autobase."$propName" ?:
                                  defValue
  }

	static Database getDatabase() {
		def inst = Props.instance
		def props = inst.properties
		props[Props.DB_DRIVER_PROPERTY] = Config.config.dataSource.driverClassName.toString()
		props[Props.DB_USER_PROPERTY] = Config.config.dataSource.username.toString()
		props[Props.DB_PASS_PROPERTY] = Config.config.dataSource.password.toString()
		props[Props.DB_URL_PROPERTY] = Config.config.dataSource.url.toString()
		return inst.database
	} 

}

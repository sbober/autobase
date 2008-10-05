import org.codehaus.groovy.grails.commons.ApplicationHolder as AH;
import java.util.zip.*

// Provides a list of the migration files to execute, in order
def changeLogs = MigrationsFinder.findMigrations()

// The overall database change log
databaseChangeLog(logicalFilePath:'@APPNAME@-autobase') {
  changeLogs.each { include(it) }
}

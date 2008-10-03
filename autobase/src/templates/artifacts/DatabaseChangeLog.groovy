import org.codehaus.groovy.grails.commons.ApplicationHolder as AH;
import java.util.zip.*

// Provides a string list of the migration files to execute, in order
def changeLogs
if(AH.application.isWarDeployed()) {
  def resources = new ZipInputStream(AH.application.classLoader.getResourceAsStream("Autobase.Migrations.zip"))
  changeLogs = []
  def entry
  while(entry = resources.nextEntry()) {
    if(entry.isDirectory()) { next }
    def file = new File(System.properties['java.io.tmpdir'], zipEntry.name )
    file.parentFile.mkdirs() // Make the path to its parent
    file.delete()
    def outLine = new BufferedOutputStream(new FileOutputStream(file, false))
    while(resources.available()) {
      outLine.write(resources.read())
    }
    outLine.close()
    resources.closeEntry()
    changeLogs << file
  }
  changeLogs.sort({a,b -> a.name <=> b.name })
} else {
  def changeLogsFromDir
  changeLogsFromDir = {
      def root = it.listFiles()
      root.findAll { it.isDirectory() }.each { changeLogs << changeLogsFromDir(it) }
      def changeLogs = (root.findAll { 
        it.isFile() && it.name.endsWith('Migration.groovy') 
      } ?: [])*.canonicalPath
      return changeLogs.flatten()*.sort( {a,b -> a.name <=> b.name })
  }
  
}

// The overall database change log
databaseChangeLog(logicalFilePath:'@APPNAME@-autobase') {
  changeLogs.each { include(it) }
}

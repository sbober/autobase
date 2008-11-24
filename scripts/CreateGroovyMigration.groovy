import org.codehaus.groovy.grails.commons.GrailsClassUtils as GCU
import liquibase.dsl.properties.LbdslProperties as Props
import org.apache.commons.lang.StringUtils as SU
import java.text.SimpleDateFormat

Ant.property(environment:"env")                             
grailsHome = Ant.antProject.properties."env.GRAILS_HOME"

includeTargets << new File ( "${grailsHome}/scripts/Init.groovy" )
includeTargets << new File ( "${autobasePluginDir}/scripts/_MigrationBase.groovy" )

target ('default': "Creates a new migration based on a Groovy script") {
  Ant.property(name:'migration.template',value:"${autobasePluginDir}/src/templates/artifacts/GroovyMigration.groovy")
  makeMigration()

  def name = Ant.antProject.properties.'migration.name'
  def author = Ant.antProject.properties.'migration.author'
  def dir = Ant.antProject.properties.'migration.dir'
	def fileName = Ant.antProject.properties.'migration.file'
  //println "name[${name}]"
  //println "author[${author}]"
  //println "dir[${dir}]"
  //println "fileName[${fileName}]"

  def scriptDir = "${dir}/.scripts"
  def scriptFile = "${scriptDir}/${name}GroovyScript.groovy"
  //println "scriptDir[${scriptDir}]"
  //println "scriptFile[${scriptFile}]"
	
  Ant.sequential {  
    replace(file:fileName, token:'@SCRIPTFILE@', value:scriptFile, summary:true)
  
    mkdir(dir:scriptDir)
    copy(file:"${autobasePluginDir}/src/templates/artifacts/GroovyScript.groovy",
       tofile:scriptFile) 
    replace(file:scriptFile, token:'@AUTHOR@', value:author )
    replace(file:scriptFile, token:'@ID@', value:name )
  }
  println "Groovy Script generated at ${scriptFile}"
}

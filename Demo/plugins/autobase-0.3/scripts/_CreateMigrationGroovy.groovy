import org.codehaus.groovy.grails.commons.GrailsClassUtils as GCU
import liquibase.dsl.properties.LbdslProperties as Props
import org.apache.commons.lang.StringUtils as SU
import java.text.SimpleDateFormat

Ant.property(environment:"env")                             
grailsHome = Ant.antProject.properties."env.GRAILS_HOME"

includeTargets << new File ( "${grailsHome}/scripts/Init.groovy" )

target ('default': "Creates a new migration using a Groovy script") {
  def name = args?.toString()
	if(!name) {
		Ant.input(addProperty:"migration.name", message:"Migration name not specified (press enter for auto-generated name)")
		name = Ant.antProject.properties."migration.name"
	}
  if(name == null || SU.isBlank(name)) {
    String timestamp = new SimpleDateFormat("yyyyMMdd.HHmmss").format(new Date())
    name = timestamp
  }
  name = name.split(/\s+/).collect { SU.capitalize(it) }.join("")

  def author = Props.instance.defaultAuthor
  author = author.split(/\s+/).collect { SU.capitalize(it) }.join("")
  def dir = "./migrations/${author}"
  def scriptDir = "${dir}/scripts"
		      
	def fileName = "${dir}/${name}Migration.groovy"
  def scriptName = "${scriptDir}/${name}Migration.groovy"
		
	Ant.sequential {  
    // Create migration file
    mkdir(dir:dir)
		copy(file:"${autobasePluginDir}/src/templates/artifacts/MigrationGroovy.groovy",
			 tofile:fileName) 
		replace(file:fileName, 
				token:"@AUTHOR@", value:author )
    replace(file:fileName,
        token:"@ID@", value:name )
    replace(file:fileName,
        token:"@SCRIPT@", value:scriptName)
  
    // Create the script file
    mkdir(dir:scriptDir)
    copy(file:"${autobasePluginDir}/src/templates/artifacts/GroovyScript.groovy",
         tofile:scriptName)
		replace(file:fileName, 
				token:"@AUTHOR@", value:author )
    replace(file:fileName,
        token:"@ID@", value:name )
	}	                                                                            
	println "Migration based on Groovy script generated at ${fileName}; Groovy script is at ${scriptName}"
}

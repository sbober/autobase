import org.codehaus.groovy.grails.commons.GrailsClassUtils as GCU
import liquibase.dsl.properties.LbdslProperties as Props
import org.apache.commons.lang.StringUtils as SU

Ant.property(environment:"env")                             
grailsHome = Ant.antProject.properties."env.GRAILS_HOME"

includeTargets << new File ( "${grailsHome}/scripts/Init.groovy" )

target ('default': "Creates a new migration") {
  def timestamp = Long.toString(System.currentTimeMillis(), 16).toUpperCase() 
  def name = args?.toString()
	if(!name) {
		Ant.input(addProperty:"migration.name", message:"Migration name not specified (press enter for auto-generated name)")
		name = Ant.antProject.properties."migration.name"
	}
  if(name == null || SU.isBlank(name)) {
    name = timestamp
  }
  name = name.split(/\s+/).collect { SU.capitalize(it) }.join("")

  def author = Props.instance.defaultAuthor
  author = author.split(/\s+/).collect { SU.capitalize(it) }.join("")
  def dir = "${basedir}/grails-app/migrations/${author}"
		      
	def fileName = "${dir}/${name}Migration.groovy"
		
	Ant.sequential {  
    mkdir(dir:dir)
		copy(file:"${pluginBaseDir}/src/templates/artifacts/Migration.groovy",
			 tofile:fileName) 
		replace(file:fileName, 
				token:"@AUTHOR@", value:author )
    replace(file:fileName,
        token:"@ID@", value:name )
	}	                                                                            
	println "Migration generated at ${fileName}"
}

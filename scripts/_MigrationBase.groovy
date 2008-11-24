import org.codehaus.groovy.grails.commons.GrailsClassUtils as GCU
import liquibase.dsl.properties.LbdslProperties as Props
import org.apache.commons.lang.StringUtils as SU
import java.text.SimpleDateFormat

Ant.property(environment:"env")                             
grailsHome = Ant.antProject.properties."env.GRAILS_HOME"

includeTargets << new File ( "${grailsHome}/scripts/Init.groovy" )

target(findAuthor:"Finds the author and sets the appropriate property") {
  def author = Props.instance.defaultAuthor
  author = author.split(/\s+/).collect { SU.capitalize(it) }.join("")
  Ant.property(name:'migration.author',value:author)
}

target(makeMigrationDir: "Creates the directory for migrations") {
  depends(findAuthor)
  def author = Ant.antProject.properties.'migration.author'
  def dir = "./migrations/${author}"
  Ant.property(name:'migration.dir',value:dir)
	Ant.mkdir(dir:dir)
}

target(makeMigration:'Creates the migration script itself') {
  depends(findAuthor)
  depends(makeMigrationDir)
  def name = Ant.antProject.properties.'migration.name' ?: args?.toString()
  if(!name) {
    Ant.input(addProperty:"migration.name.orig", message:"Migration name not specified (press enter for auto-generated name)")
    name = Ant.antProject.properties."migration.name.orig"
  }
  if(name == null || SU.isBlank(name)) {
    String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())
    name = timestamp
  }
  name = name.split(/\s+/).collect { SU.capitalize(it) }.join("")
  Ant.property(name:"migration.name", value:name)

  def dir = Ant.antProject.properties.'migration.dir'
  def author = Ant.antProject.properties.'migration.author'

  def fileName = "${dir}/${name}Migration.groovy"
  Ant.property(name:'migration.file', value:fileName)

  def file = Ant.antProject.properties.'migration.template' ?: "${autobasePluginDir}/src/templates/artifacts/Migration.groovy"

  Ant.sequential {
    copy(file:file, tofile:fileName) 
    replace(file:fileName, token:"@AUTHOR@", value:author )
    replace(file:fileName, token:"@ID@", value:name )
  }
  println "Migration generated at ${fileName}"
}


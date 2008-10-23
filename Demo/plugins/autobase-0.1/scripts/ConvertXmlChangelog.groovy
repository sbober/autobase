import org.codehaus.groovy.grails.commons.GrailsClassUtils as GCU
import org.apache.commons.lang.StringUtils as SU
import groovy.xml.QName

grailsHome = Ant.project.properties."environment.GRAILS_HOME"

includeTargets << new File ( "${grailsHome}/scripts/Init.groovy" )  

target('default': "Converts a full or partial changelog.xml file into a changelog.groovy file") {
  args = args?.split(/\s+/)
  String sourceName = args?.getAt(0)
  String targetName = args?.getAt(1)

  if(!sourceName) {
    Ant.input(addProperty:"autobase.convert.source", message: "What file should we convert?")
    sourceName = Ant.antProject.properties."autobase.convert.source"
  }
  if(sourceName == null || SU.isBlank(sourceName)) {
    Ant.fail(message:"No source file to convert specified")
  }
 
  File sourceFile = new File(sourceName)
  if(!sourceFile.exists()) {
    Ant.fail(message:"Source file to convert does not exist: ${sourceFile.absolutePath}")
  }
  if(!sourceFile.isFile()) {
    Ant.fail(message:"Source file to convert is not a file: ${sourceFile.absolutePath}")
  }
  if(!sourceFile.canRead()) {
    Ant.fail(message:"Cannot read source file: ${sourceFile.absolutePath}")
  }

  if(!targetName) {
    targetName = SU.removeEnd(sourceName, ".xml") + ".groovy"
  }
  File targetFile = new File(targetName)

  Writer target = new BufferedWriter(new FileWriter(targetFile, false))
  new XmlParser().parse(sourceFile).children().each { processNode(it, target) }
  target.close()
}

private String stripNamespace(final String str) {
  if(str.contains('}')) {
    return SU.substringAfterLast(str, '}')
  } else {
    return str
  }
}

private String stripNamespace(final QName nodeName) {
  return nodeName.localPart
}

private void processNode(final Node node, final Writer writer, final int depth=0) {
  final String nodeName = stripNamespace(node.name())

  //println "Processing node ${nodeName}"
  (0..<depth).each{ writer.write('\t') }; writer.write(nodeName)
  writer.write('( ')
  List inParens = []
  String textBody = node.children().find { it instanceof String }
  if(textBody) { 
    textBody = SU.replace(textBody, '\n', '\\n')
    inParens << "\"${textBody}\""
  }
  def attrs = node.attributes().entrySet()
  if(nodeName == 'databaseChangeLog') { attrs = attrs.findAll { stripNamespace(it.key) != 'schemaLocation' } }
  attrs.each { inParens << "${stripNamespace(it.key)}: \"$it.value\"" }
  writer.write(inParens.join(', ') ?: '')
  writer.write(' )')
  def children = node.children().findAll { !(it instanceof String) }
  if(children) {
    (0..<depth).each { writer.write('\t') }; writer.write('{\n')
    children.each { 
      processNode(it, writer, depth + 1)
    }
    (0..<depth).each { writer.write('\t') }; writer.write('}\n')
  }
  writer.write('\n')

}

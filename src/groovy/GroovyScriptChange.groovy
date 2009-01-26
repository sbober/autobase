package autobase.change;

import liquibase.*;
import liquibase.change.*;
import liquibase.database.*;
import liquibase.database.structure.*;
import liquibase.database.sql.*;
import liquibase.database.sql.visitor.*; 
import liquibase.exception.*;
import groovy.sql.*;
import org.codehaus.groovy.control.*;
import org.w3c.dom.*;
import java.util.*;
import org.apache.commons.lang.StringUtils

public class GroovyScriptChange implements liquibase.change.Change {

  String sourceFile;
  Set<DatabaseObject> affectedObjects = new HashSet<DatabaseObject>(1);
  def result;

  FileOpener fileOpener;
  ChangeSet changeSet;

  String getChangeName() { return "groovyScript" }
  String getTagName() { return this.changeName }

  void executeStatements(Database db, List<SqlVisitor> sqlVisitors) throws JDBCException, UnsupportedChangeException {
    if(!sourceFile) {
      throw new IllegalStateException(changeName + " tag requires 'sourceFile' property to be set")
    }
    String logicalName = sourceFile
    logicalName = StringUtils.remove(logicalName, '.groovy')
    logicalName = StringUtils.remove(logicalName, '.')
    logicalName = logicalName + ".groovy"
    def binding = new Binding()
    binding.setVariable("db", db)
    binding.setVariable("sql", new Sql(db.connection.underlyingConnection) )
    binding.setVariable("fileOpener", fileOpener)
    binding.setVariable("changeSet", changeSet)
    binding.setVariable("sourceFile", sourceFile)
    binding.setVariable("logicalName", logicalName)
    def shell = new GroovyShell(binding)
    result = shell.evaluate(fileOpener.getResourceAsStream(sourceFile).text, logicalName)
  }

  void saveStatements(Database db, List<SqlVisitor> sqlVisitors, Writer writer) throws IOException, UnsupportedChangeException,  StatementNotSupportedOnDatabaseException {
    writer << (db.lineComment + " Insert arbitrary Groovy processing from ${sourceFile} here")
  }

  void executeRollbackStatements(Database database, List<SqlVisitor> sqlVisitors) throws JDBCException, UnsupportedChangeException, RollbackImpossibleException {
    throw new RollbackImpossibleException("Can't roll back arbitrary Groovy code")
  }

  void saveRollbackStatement(Database database, List<SqlVisitor> sqlVisitors, java.io.Writer writer)
                           throws java.io.IOException,
                                  UnsupportedChangeException,
                                  RollbackImpossibleException,
                                  StatementNotSupportedOnDatabaseException {
    throw new RollbackImpossibleException("Can't roll back arbitrary Groovy code")
  }

  SqlStatement[] generateStatements(Database database) throws UnsupportedChangeException {
    return ([new RawSqlStatement(database.lineComment + " Insert arbitrary Groovy processing from ${sourceFile} here")] as SqlStatement[])
  }

  SqlStatement[] generateRollbackStatements(Database database) throws UnsupportedChangeException, RollbackImpossibleException {
    throw new RollbackImpossibleException("Can't roll back arbitrary Groovy code")
  }

  boolean canRollBack() { false }

  String getConfirmationMessage() { "Executed Groovy in ${sourceFile}" }

  Node createNode(Document currentChangeLogDOM) {
    def element = currentChangeLogDOM.createElement(changeName)
    setAttribute("sourceFile", sourceFile)
    return element
  }

  String getMD5Sum() {
    return liquibase.util.MD5Util.computeMD5(fileOpener.getResourceAsStream(sourceFile))
  }

  void setUp() throws SetupException {}

  Set<DatabaseObject> getAffectedDatabaseObjects() { return affectedObjects }

  void validate(Database database) throws InvalidChangeDefinitionException {
    if(!sourceFile) {
      throw new InvalidChangeDefinitionException("No source file provided", this)
    }
    if(!fileOpener.getResourceAsStream(sourceFile)) {
      throw new IllegalStateException("Could not open ${sourceFile} for some unknown reason")
    }
  }

}



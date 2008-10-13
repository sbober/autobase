// The overall database change log
databaseChangeLog(logicalFilePath:'Demo-autobase') { 
  includeAll('./migrations')
  changeSet(id:"1", author:"bob") {
        comments("A sample change log")
        createTable(tableName:"foo")
  }
  changeSet(id:"2", author:"bob", runAlways:"true") {
    addColumn(tableName:"foo") {
      column(name:"bar", type:"varchar(255)")
    }
    dropColumn(tableName:"foo", columnName:"bar")
  }
}

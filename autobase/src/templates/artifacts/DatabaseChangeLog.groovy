// The overall database change log
databaseChangeLog(logicalFilePath:'@APPNAME@-autobase') { 
  includeAll('./migrations')
}

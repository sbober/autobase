property( name: "table.name", value: "paramter_table" )
property( file: "autobase.properties" )
property( name: "column1.name", value: "updated-columnA" )
property( name: "column2.name", value: "columnB" )
changeSet( id: "datatypetest-1", author: "nvoxland" ){
	validCheckSum( "e5b7b29ce3a75640858cd022501852d2" )
	createTable( tableName: "dataTypeTest", schemaName: "" )	{
		column( name: "id", type: "integer" )		{
			constraints( primaryKey: "true", nullable: "false" )
		}

		column( name: "dateCol", type: "date" )
		column( name: "timeCol", type: "time" )
		column( name: "dateTimeCol", type: "dateTime" )
	}

}

changeSet( id: "tagTest", author: "nvoxland" ){
	tagDatabase( tag: "testTag" )
}

changeSet( id: "datatypetest-2", author: "nvoxland" ){
	insert( tableName: "dataTypeTest" )	{
		column( name: "id", valueNumeric: "1" )
		column( name: "dateCol", valueDate: "2007-08-09" )
		column( name: "timeCol", valueDate: "13:14:15" )
		column( name: "dateTimeCol", valueDate: "2007-08-09T13:14:15" )
	}

}

changeSet( id: "defaultValueTest-1", author: "nvoxland" ){
	createTable( tableName: "defaultValueTest" )	{
		column( name: "id", type: "int" )
		column( name: "intA", type: "int" )
		column( name: "textA", type: "varchar(5)" )
		column( name: "booleanA", type: "boolean" )
		column( name: "dateA", type: "date" )
		column( name: "timeA", type: "time" )
		column( name: "datetimeA", type: "datetime" )
		column( name: "datetimeB", type: "datetime" )
	}

	addDefaultValue( tableName: "defaultValueTest", defaultValueNumeric: "1", columnName: "intA" )
	addDefaultValue( defaultValue: "a", tableName: "defaultValueTest", columnName: "textA" )
	addDefaultValue( tableName: "defaultValueTest", columnName: "booleanA", defaultValueBoolean: "true" )
	addDefaultValue( tableName: "defaultValueTest", columnName: "dateA", defaultValueDate: "2007-08-09" )
	addDefaultValue( tableName: "defaultValueTest", columnName: "timeA", defaultValueDate: "14:15:16" )
	addDefaultValue( tableName: "defaultValueTest", columnName: "datetimeA", defaultValueDate: "2007-08-09T10:11:12" )
	addDefaultValue( tableName: "defaultValueTest", columnName: "datetimeB", defaultValueDate: "2007-08-09 10:11:12" )
	insert( tableName: "defaultValueTest" )	{
		column( name: "id", valueNumeric: "1" )
	}

}

changeSet( id: "defaultValueTest-2", author: "nvoxland" ){
	createTable( tableName: "defaultValueTest2" )	{
		column( name: "id", type: "int" )
		column( defaultValueNumeric: "1", type: "int", name: "intA" )
		column( defaultValue: "a", type: "varchar(5)", name: "textA" )
		column( type: "boolean", name: "booleanA", defaultValueBoolean: "true" )
		column( type: "date", name: "dateA", defaultValueDate: "2007-08-09" )
		column( type: "time", name: "timeA", defaultValueDate: "14:15:16" )
		column( type: "datetime", name: "datetimeA", defaultValueDate: "2007-08-09T10:11:12" )
		column( type: "datetime", name: "datetimeB", defaultValueDate: "2007-08-09 10:11:12" )
	}

	insert( tableName: "defaultValueTest2" )	{
		column( name: "id", valueNumeric: "1" )
	}

}

changeSet( id: "compoundPrimaryKeyTest", author: "nvoxland" ){
	createTable( tableName: "compoundPKTest" )	{
		column( name: "id1", type: "int" )		{
			constraints( primaryKey: "true", nullable: "false" )
		}

		column( name: "id2", type: "int" )		{
			constraints( primaryKey: "true", nullable: "false" )
		}

		column( name: "name", type: "varchar(255)" )
	}

}

changeSet( id: "compoundIndexTest", author: "nvoxland" ){
	createTable( tableName: "compoundIndexTest" )	{
		column( name: "id", type: "int" )		{
			constraints( primaryKey: "true", nullable: "false" )
		}

		column( name: "firstname", type: "varchar(10)" )
		column( name: "lastname", type: "varchar(10)" )
	}

	createIndex( tableName: "compoundIndexTest", indexName: "idx_compoundtest" )	{
		column( name: "firstname" )
		column( name: "lastname" )
	}

}

changeSet( id: "commentTest", author: "nvoxland" ){
	comment( "Creates a table and updates a value with actual SQL. Mainly for testing the comment tags" )
	createTable( tableName: "commenttest" )	{
		column( name: "id", type: "int" )
	}

	sql( "insert into commenttest (id) values (1);\n            insert into commenttest (id) values (2);\n            insert into commenttest (id) values (3);" )	{
		comment( "Insert value into commenttest" )
	}

}

changeSet( id: "add-column-test1", author: "alan" ){
	comment( "Testing bug reported on mailing list" )
	createTable( tableName: "ADD_COLUMN_TEST_TABLE" )	{
		column( name: "id", type: "int" )
	}

	addColumn( tableName: "ADD_COLUMN_TEST_TABLE" )	{
		column( defaultValueNumeric: "1", type: "int", name: "A_NEW_COLUMN" )		{
			constraints( nullable: "false" )
		}

	}

}

changeSet( id: "tablespace-test1", author: "alan" ){
	comment( "Test tablespace support. Ignored if database does not support tablespaces" )
	createTable( tableName: "TABLESPACE_TEST_TABLE", tablespace: "liquibase2" )	{
		column( name: "id", type: "int" )		{
			constraints( nullable: "false" )
		}

		column( name: "name", type: "varchar(255)" )
		column( name: "username", type: "varchar(255)" )		{
			constraints( nullable: "false" )
		}

	}

	createIndex( tableName: "TABLESPACE_TEST_TABLE", tablespace: "liquibase2", indexName: "tablespace_index_test" )	{
		column( name: "id" )
	}

	addPrimaryKey( constraintName: "pk_tablespacetest", tableName: "TABLESPACE_TEST_TABLE", columnNames: "id", tablespace: "liquibase2" )
	addUniqueConstraint( constraintName: "uq_tbsptest_usern", tableName: "TABLESPACE_TEST_TABLE", columnNames: "username", tablespace: "liquibase2" )
}

changeSet( id: "contextstest-1", author: "nvoxland" ){
	comment( "Test how contexts work" )
	createTable( tableName: "contextsTest" )	{
		column( name: "id", type: "varchar(255)" )
	}

}

changeSet( author: "nvoxland", id: "contextstest-2", context: "context-a" ){
	insert( tableName: "contextsTest" )	{
		column( name: "id", value: "context a" )
	}

}

changeSet( author: "nvoxland", id: "contextstest-3", context: "context-a, context-b" ){
	insert( tableName: "contextsTest" )	{
		column( name: "id", value: "a and b" )
	}

}

changeSet( author: "nvoxland", id: "contextstest-4", context: "context-b" ){
	insert( tableName: "contextsTest" )	{
		column( name: "id", value: "context b" )
	}

}

changeSet( author: "nvoxland", id: "contextstest-5", context: "" ){
	insert( tableName: "contextsTest" )	{
		column( name: "id", value: "empty contexts" )
	}

}

changeSet( id: "contextstest-6", author: "nvoxland" ){
	insert( tableName: "contextsTest" )	{
		column( name: "id", value: "null contexts" )
	}

}

changeSet( id: "datatype-conversion-teset", author: "nvoxland" ){
	createTable( tableName: "datatypeConversionTest" )	{
		column( name: "booleanColumn", type: "boolean" )
		column( name: "currencyColumn", type: "currency" )
		column( name: "uuidColumn", type: "uuid" )
		column( name: "blobColumn", type: "blob" )
		column( name: "clobColumn", type: "clob" )
		column( name: "dateColumn", type: "date" )
		column( name: "timeColumn", type: "time" )
		column( name: "datetimeColumn", type: "datetime" )
	}

}

changeSet( id: "standardTypes-test", author: "nvoxland" ){
	createTable( tableName: "standardTypesTest" )	{
		column( name: "timestampColumn", type: "java.sql.Types.TIMESTAMP" )
		column( name: "varcharColumn", type: "java.sql.Types.VARCHAR(255)" )
		column( name: "blobColumn", type: "java.sql.Types.BLOB" )
		column( name: "booleanColumn", type: "java.sql.Types.BOOLEAN" )
	}

}

changeSet( id: "createTableNamedUqConst", author: "nvoxland" ){
	createTable( tableName: "createTableNamedUqConst" )	{
		column( name: "id", type: "int" )		{
			constraints( uniqueConstraintName: "uq_uqtest1", unique: "true", nullable: "false" )
		}

	}

}

changeSet( id: "createTableUnNamedUqConst", author: "nvoxland" ){
	createTable( tableName: "createTableUnNamedUqConst" )	{
		column( name: "id", type: "int" )		{
			constraints( nullable: "false", unique: "true" )
		}

	}

}

changeSet( id: "commonTypes-test", author: "nvoxland" ){
	createTable( tableName: "commonTypesTest" )	{
		column( name: "intColumn", type: "int" )
		column( name: "floatColumn", type: "float" )
	}

}

changeSet( id: "addColumnWithInitialValue-test", author: "nvoxland" ){
	createTable( tableName: "tableWithInitialValue" )	{
		column( name: "id", type: "int" )
	}

	insert( tableName: "tableWithInitialValue" )	{
		column( name: "id", valueNumeric: "1" )
	}

	insert( tableName: "tableWithInitialValue" )	{
		column( name: "id", valueNumeric: "2" )
	}

	insert( tableName: "tableWithInitialValue" )	{
		column( name: "id", valueNumeric: "3" )
	}

	addColumn( tableName: "tableWithInitialValue" )	{
		column( type: "varchar(25)", name: "varcharColumn", value: "INITIAL_VALUE" )
		column( valueNumeric: "2", type: "int", name: "intColumn" )
		column( valueDate: "2008-03-04", type: "date", name: "dateCol" )
	}

}

changeSet( author: "nvoxland", id: "updateTest" ){
	createTable( tableName: "updateTest" )	{
		column( name: "id", type: "int" )
		column( name: "varcharColumn", type: "varchar(255)" )
		column( name: "dateCol", type: "date" )
		column( name: "intCol", type: "int" )
		column( name: "commonCol", type: "varchar(255)" )
	}

	insert( tableName: "updateTest" )	{
		column( name: "id", valueNumeric: "1" )
		column( name: "varcharColumn", value: "column 1 value" )
		column( name: "dateCol", valueDate: "2007-01-01" )
		column( name: "intCol", valueNumeric: "1" )
	}

	insert( tableName: "updateTest" )	{
		column( name: "id", valueNumeric: "2" )
		column( name: "varcharColumn", value: "column 2 value" )
		column( name: "dateCol", valueDate: "2007-01-02" )
		column( name: "intCol", valueNumeric: "2" )
	}

	insert( tableName: "updateTest" )	{
		column( name: "id", valueNumeric: "3" )
		column( name: "varcharColumn", value: "column 3 value" )
		column( name: "dateCol", valueDate: "2007-01-03" )
		column( name: "intCol", valueNumeric: "3" )
	}

	update( tableName: "updateTest" )	{
		column( name: "commonCol", value: "Value for everyone" )
	}

	update( tableName: "updateTest" )	{
		column( name: "varcharColumn", value: "new column 1 value" )
		column( name: "dateCol", valueDate: "2008-01-01" )
		column( name: "intCol", valueNumeric: "11" )
		where( "id=1" )
	}

	update( tableName: "updateTest" )	{
		column( name: "varcharColumn", value: "new column 2 value" )
		column( name: "dateCol", valueDate: "2008-01-02" )
		column( name: "intCol", valueNumeric: "12" )
		where( "id=2" )
	}

}

changeSet( author: "nvoxland", id: "deleteTest" ){
	createTable( tableName: "deleteTest" )	{
		column( name: "id", type: "int" )
		column( name: "varcharColumn", type: "varchar(255)" )
		column( name: "dateCol", type: "date" )
		column( name: "intCol", type: "int" )
		column( name: "commonCol", type: "varchar(255)" )
	}

	insert( tableName: "deleteTest" )	{
		column( name: "id", valueNumeric: "1" )
		column( name: "varcharColumn", value: "column 1 value" )
		column( name: "dateCol", valueDate: "2007-01-01" )
		column( name: "intCol", valueNumeric: "1" )
	}

	insert( tableName: "deleteTest" )	{
		column( name: "id", valueNumeric: "2" )
		column( name: "varcharColumn", value: "column 2 value" )
		column( name: "dateCol", valueDate: "2007-01-02" )
		column( name: "intCol", valueNumeric: "2" )
	}

	insert( tableName: "deleteTest" )	{
		column( name: "id", valueNumeric: "3" )
		column( name: "varcharColumn", value: "column 3 value" )
		column( name: "dateCol", valueDate: "2007-01-03" )
		column( name: "intCol", valueNumeric: "3" )
	}

	delete( tableName: "deleteTest" )	{
		where( "id=1" )
	}

	delete( tableName: "deleteTest" )
}

changeSet( failOnError: "false", author: "rs", id: "failOnErrorTest" ){
	dropTable( tableName: "DoesNotExist" )
}

changeSet( id: "createTableNamedPK", author: "nvoxland" ){
	createTable( tableName: "createtablenamedpk" )	{
		column( name: "id", type: "int" )		{
			constraints( primaryKey: "true", nullable: "false", primaryKeyName: "pk_pktest1" )
		}

	}

}

changeSet( id: "tableWithRemarks", author: "nvoxland" ){
	createTable( tableName: "tableWithRemarks", remarks: "some test remarks" )	{
		column( name: "id", type: "int" )
	}

}

changeSet( id: "sampleCSVData-table", author: "nvoxland" ){
	createTable( tableName: "csvdata" )	{
		column( name: "name", type: "varchar(50)" )
		column( name: "username", type: "varchar(50)" )
		column( name: "security_level", type: "int" )
		column( name: "last_login", type: "datetime" )
		column( name: "locked", type: "boolean" )
	}

}

changeSet( id: "sampleCSVData", author: "nvoxland" ){
	loadData( tableName: "csvdata", file: "sample.data1.csv" )
}

changeSet( id: "sampleCSVData2", author: "nvoxland" ){
	loadData( tableName: "csvdata", file: "sample.data2.csv" )	{
		column( header: "locked", type: "BOOLEAN" )
		column( header: "last_login", type: "DATE" )
		column( header: "security_level", type: "NUMERIC" )
	}

}

changeSet( id: "sampleCSVData3", author: "nvoxland" ){
	preConditions(  )	{
		sqlCheck( "select count(*) from csvdata", expectedResult: "4" )
	}

	loadData( tableName: "csvdata", file: "sample.data3.csv" )	{
		column( index: "0", type: "STRING", name: "name" )
		column( header: "last seen", type: "DATE", name: "last_login" )
		column( header: "locked", type: "BOOLEAN" )
		column( header: "security_level", type: "NUMERIC" )
	}

}

changeSet( id: "createTableNamedPKRef", author: "nvoxland" ){
	createTable( tableName: "createTableNamedPKRef" )	{
		column( name: "id", type: "int" )		{
			constraints( primaryKey: "true" )
		}

		column( name: "ref", type: "int" )		{
			constraints( references: "createtablenamedpk(id)", foreignKeyName: "fk_reftest" )
		}

	}

}

changeSet( id: "changeLogParameters", author: "nvoxland" ){
	createTable( tableName: "${table.name}" )	{
		column( name: "id", type: "int" )
		column( name: "${column1.name}", type: "varchar(${column1.length})" )
		column( name: "${column2.name}", type: "int" )
	}

}

changeSet( id: "customPreCondition", author: "nvoxland" ){
	preConditions(  )	{
		customPrecondition( className: "liquibase.preconditions.ExampleCustomPrecondition" )		{
			param( name: "name", value: "Sample Name" )
			param( name: "count", value: "3" )
		}

	}

}

changeSet( id: "failContinuePrecondition", author: "nvoxland" ){
	preConditions( onFail: "CONTINUE" )	{
		runningAs( username: "invaliduser" )
	}

	createTable( tableName: "fail_continue_ran" )	{
		column( name: "id", type: "int" )
	}

}

changeSet( id: "failWarnPrecondition", author: "nvoxland" ){
	preConditions( onFail: "WARN" )	{
		runningAs( username: "invaliduser" )
	}

	createTable( tableName: "fail_warn_ran" )	{
		column( name: "id", type: "int" )
	}

}

changeSet( id: "failMarkRanPrecondition", author: "nvoxland" ){
	preConditions( onError: "MARK_RAN" )	{
		sqlCheck( "select 1 from badtable" )
	}

	createTable( tableName: "fail_markran_ran" )	{
		column( name: "id", type: "int" )
	}

}

changeSet( id: "errorContinuePrecondition", author: "nvoxland" ){
	preConditions( onError: "CONTINUE" )	{
		sqlCheck( "select 1 from badtable" )
	}

	createTable( tableName: "error_continue_ran" )	{
		column( name: "id", type: "int" )
	}

}

changeSet( id: "errorWarnPrecondition", author: "nvoxland" ){
	preConditions( onError: "WARN" )	{
		sqlCheck( "select 1 from badtable" )
	}

	createTable( tableName: "error_warn_ran" )	{
		column( name: "id", type: "int" )
	}

}

changeSet( id: "errorMarkRanPrecondition", author: "nvoxland" ){
	preConditions( onError: "MARK_RAN" )	{
		sqlCheck( "select 1 from badtable" )
	}

	createTable( tableName: "error_markran_ran" )	{
		column( name: "id", type: "int" )
	}

}

changeSet( id: "changeSetRanPrecondition", author: "nvoxland" ){
	preConditions(  )	{
		changeSetExecuted( changeLogFile: "common.tests.changelog.groovy", author: "nvoxland", id: "changeLogParameters" )
	}

	createTable( tableName: "changesetprecondition_ran" )	{
		column( name: "id", type: "int" )
	}

}

changeSet( id: "tableExistsPrecondition", author: "nvoxland" ){
	preConditions(  )	{
		tableExists( tableName: "CSVDATA" )
	}

}

changeSet( id: "columnExistsPrecondition", author: "nvoxland" ){
	preConditions(  )	{
		columnExists( tableName: "CSVDATA", columnName: "name" )
	}

}

changeSet( id: "fkExistsPrecondition", author: "nvoxland" ){
	preConditions(  )	{
		foreignKeyConstraintExists( foreignKeyName: "fk_reftest" )
	}

}

changeSet( id: "indexPrecondition", author: "nvoxland" ){
	preConditions(  )	{
		indexExists( indexName: "idx_compoundtest" )
	}

}


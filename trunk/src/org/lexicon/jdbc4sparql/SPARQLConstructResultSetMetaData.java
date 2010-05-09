package org.lexicon.jdbc4sparql;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class SPARQLConstructResultSetMetaData implements ResultSetMetaData {

private SPARQLConstructResultSet rs;
	
	public SPARQLConstructResultSetMetaData(SPARQLConstructResultSet rs) {
		this.rs = rs;
	}
	
	
	public String getCatalogName(int column) throws SQLException {
		return "";
	}

	public String getColumnClassName(int column) throws SQLException {
		switch (column) {
			case 1 :
				return "com.hp.hpl.jena.rdf.model.Resource";
			case 2 :
				return "com.hp.hpl.jena.rdf.model.Property";
			case 3 :
				return "com.hp.hpl.jena.rdf.model.RDFNode";
			default:
				return "com.hp.hpl.jena.rdf.model.RDFNode";
		}
	}

	public int getColumnCount() throws SQLException {
		return 3;
	}

	public int getColumnDisplaySize(int column) throws SQLException {
		return 1;
	}

	public String getColumnLabel(int column) throws SQLException {
		return this.rs.getColumnNames().get(column);
	}

	public String getColumnName(int column) throws SQLException {
		return this.rs.getColumnNames().get(column);
	}

	public int getColumnType(int column) throws SQLException {
		return java.sql.Types.JAVA_OBJECT;		
	}

	public String getColumnTypeName(int column) throws SQLException {
		switch (column) {
			case 1 :
				return "Resource";
			case 2 :
				return "Property";
			case 3 :
				return "RDFNode";
			default:
				return "RDFNode";
		}
	}

	public int getPrecision(int column) throws SQLException {
		return 0;
	}

	public int getScale(int column) throws SQLException {
		return 0;
	}

	public String getSchemaName(int column) throws SQLException {
		return "";
	}

	public String getTableName(int column) throws SQLException {
		return "";
	}

	public boolean isAutoIncrement(int column) throws SQLException {
		return false;
	}

	public boolean isCaseSensitive(int column) throws SQLException {
		return false;
	}

	public boolean isCurrency(int column) throws SQLException {
		return false;
	}

	public boolean isDefinitelyWritable(int column) throws SQLException {
		return false;
	}

	public int isNullable(int column) throws SQLException {
		return 0;
	}

	public boolean isReadOnly(int column) throws SQLException {
		return true;
	}

	public boolean isSearchable(int column) throws SQLException {
		return false;
	}

	public boolean isSigned(int column) throws SQLException {
		return false;
	}

	public boolean isWritable(int column) throws SQLException {
		return false;
	}

	public boolean isWrapperFor(Class arg0) throws SQLException {
		if (arg0.getName() == "Model") {
			return true;
		}
		else {
			return false;
		}
	}

	public Object unwrap(Class arg0) throws SQLException {
		return this.rs.getModel();
	}

}

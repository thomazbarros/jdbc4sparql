package org.lexicon.jdbc4sparql;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class SPARQLAskResultSetMetaData implements ResultSetMetaData {

	private SPARQLAskResultSet rs;
	
	public SPARQLAskResultSetMetaData(SPARQLAskResultSet rs) {
		this.rs = rs;
	}
	
	public String getCatalogName(int column) throws SQLException {
		return "";
	}

	public String getColumnClassName(int column) throws SQLException {
		return "java.lang.Boolean";
	}

	public int getColumnCount() throws SQLException {
		return 1;
	}

	public int getColumnDisplaySize(int column) throws SQLException {
		return 255;
	}

	public String getColumnLabel(int column) throws SQLException {
		return SPARQLAskResultSet.columnLabel;
	}

	public String getColumnName(int column) throws SQLException {
		if (column == 1) {
			return SPARQLAskResultSet.columnLabel;
		}
		else {
			throw new SQLException("column not found");
		}
	}

	public int getColumnType(int column) throws SQLException {
		return java.sql.Types.BOOLEAN;	
	}

	public String getColumnTypeName(int column) throws SQLException {
		return "RDFNode";
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
		return false;
	}

	public Object unwrap(Class arg0) throws SQLException {
		return null;
	}

}

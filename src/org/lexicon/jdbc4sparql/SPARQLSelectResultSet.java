package org.lexicon.jdbc4sparql;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
import java.util.Vector;
import com.hp.hpl.jena.query.*;
import java.util.List;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.datatypes.xsd.*;

public class SPARQLSelectResultSet implements ResultSet {

	private com.hp.hpl.jena.query.ResultSet resultSet;
	private SPARQLStatement statement;
	private int currentRow;
	private int type;
	private Vector<QuerySolution> internalResultSet;
	private Vector<String> columnNames;
	private boolean closed;
	private SPARQLSelectResultSetMetaData rsm;
	private int concurrency;
	private int fetchDirection;
	private Query sparql;
	
	public SPARQLSelectResultSet (com.hp.hpl.jena.query.ResultSet resultSet, SPARQLStatement statement, Query sqarql){
		this.statement = statement;
		this.resultSet = resultSet;
		this.currentRow = 0;
		this.closed = false;
		this.sparql = sparql;
		this.fetchDirection = ResultSet.FETCH_FORWARD;
		this.concurrency = ResultSet.CONCUR_READ_ONLY;
		this.rsm = new SPARQLSelectResultSetMetaData(this);
		this.type = ResultSet.TYPE_FORWARD_ONLY;
		this.internalResultSet = new Vector<QuerySolution>();
		this.columnNames = new Vector<String>(this.resultSet.getResultVars());
		while (this.resultSet.hasNext()) {
			this.resultSet.next();
			this.internalResultSet.add(this.resultSet.next());
		}
	}
	
	public Vector<String> getColumnNames() {
		return this.columnNames;
	}
	
	public Vector<QuerySolution> getInternalResultSet () {
		return this.internalResultSet;
	}
	
	public boolean absolute(int row) throws SQLException {
		if (this.closed) {
			throw new SQLException("Resultset closed");
		}
		else {
			if (row > this.internalResultSet.size() || row <= -2) {
				this.afterLast();
				return false;
			}
			else if (row == -1) {
				this.last();
				return true;
			}
			else if (row == 1) {
				this.first();
				return true;
			}
			else {
				this.currentRow = row;
				return true;
			}
		}
	}

	
	public void afterLast() throws SQLException {
		if (this.closed) {
			throw new SQLException("Resultset closed");
		}
		else {
			this.currentRow = this.internalResultSet.size() + 1;
		}
	}

	
	public void beforeFirst() throws SQLException {
		if (this.closed) {
			throw new SQLException("Resultset closed");
		}
		else {
			this.currentRow = 0;
		}
	}

	
	public void cancelRowUpdates() throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void clearWarnings() throws SQLException {
		
	}

	
	public void close() throws SQLException {
		this.closed = true;
	}

	
	public void deleteRow() throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public int findColumn(String columnLabel) throws SQLException {
		return this.columnNames.indexOf(columnLabel);
	}

	
	public boolean first() throws SQLException {
		if (this.closed) {
			throw new SQLException("Resultset closed");
		}
		else {
			if (this.internalResultSet.size() > 0) {
				this.currentRow = 1;
				return true;
			}
			else {
				return false;
			}
		}
	}

	public Array getArray(int columnIndex) throws SQLException {
		return null;
	}

	
	public Array getArray(String columnLabel) throws SQLException {
		return null;
	}


	public InputStream getAsciiStream(int columnIndex) throws SQLException {
		return null;
	}

	public InputStream getAsciiStream(String columnLabel) throws SQLException {
		return null;
	}

	public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
		try {
			return new BigDecimal(this.getString(columnIndex));
		}
		catch (Exception e){
			throw new SQLException(e.getMessage());
		}
		
	}

	public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
		try {
			return new BigDecimal(this.getString(columnLabel));
		}
		catch (Exception e){
			throw new SQLException(e.getMessage());
		}
	}

	@Override
	public BigDecimal getBigDecimal(int columnIndex, int scale)
			throws SQLException {
		try {
			String result = this.getString(columnIndex);
			if (result.indexOf(".") > 0) {
				int count = result.indexOf(".") + scale;
				result = result.substring(0, count);
			}
			return new BigDecimal(result);
		}
		catch (Exception e){
			throw new SQLException(e.getMessage());
		}
	}

	@Override
	public BigDecimal getBigDecimal(String columnLabel, int scale)
			throws SQLException {
		try {
			String result = this.getString(columnLabel);
			if (result.indexOf(".") > 0) {
				int count = result.indexOf(".") + scale;
				result = result.substring(0, count);
			}
			return new BigDecimal(result);
		}
		catch (Exception e){
			throw new SQLException(e.getMessage());
		}
	}

	@Override
	public InputStream getBinaryStream(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getBinaryStream(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Blob getBlob(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Blob getBlob(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Literal getNextSolutionAsLiteral(int columnIndex) {
		return this.getNextSolutionAsLiteral(this.columnNames.get(columnIndex));
	}
	
	public Literal getNextSolutionAsLiteral(String columnName) {
		QuerySolution solution = this.internalResultSet.get(this.currentRow);
		return solution.getLiteral(columnName);
	}
	
	@Override
	public boolean getBoolean(int columnIndex) throws SQLException {
		try {
			return this.getNextSolutionAsLiteral(columnIndex).getBoolean();
		}
		catch (Exception e){
			throw new SQLException(e.getMessage());
		}
	}

	@Override
	public boolean getBoolean(String columnLabel) throws SQLException {
		try {
			return this.getNextSolutionAsLiteral(columnLabel).getBoolean();
		}
		catch (Exception e){
			throw new SQLException(e.getMessage());
		}
	}

	@Override
	public byte getByte(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte getByte(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte[] getBytes(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getBytes(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getCharacterStream(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Reader getCharacterStream(String columnLabel) throws SQLException {
		return null;
	}

	
	public Clob getClob(int columnIndex) throws SQLException {
		return null;
	}

	public Clob getClob(String columnLabel) throws SQLException {
		return null;
	}

	public int getConcurrency() throws SQLException {
		return this.concurrency;
	}

	public String getCursorName() throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	public Date getDate(int columnIndex) throws SQLException {
		try {
			XSDDateTime val = (XSDDateTime)this.getNextSolutionAsLiteral(columnIndex).getValue();
			return (Date)val.asCalendar().getTime();
		}
		catch (Exception e){
			throw new SQLException(e.getMessage());
		}
	}

	@Override
	public Date getDate(String columnLabel) throws SQLException {
		try {
			XSDDateTime val = (XSDDateTime)this.getNextSolutionAsLiteral(columnLabel).getValue();
			return (Date)val.asCalendar().getTime();
		}
		catch (Exception e){
			throw new SQLException(e.getMessage());
		}
	}

	public Date getDate(int columnIndex, Calendar cal) throws SQLException {
		return null;
	}

	public Date getDate(String columnLabel, Calendar cal) throws SQLException {
		return null;
	}

	public double getDouble(int columnIndex) throws SQLException {
		try {
			return this.getNextSolutionAsLiteral(columnIndex).getDouble();
		}
		catch (Exception e){
			throw new SQLException(e.getMessage());
		}
	}

	public double getDouble(String columnLabel) throws SQLException {
		try {
			return this.getNextSolutionAsLiteral(columnLabel).getDouble();
		}
		catch (Exception e){
			throw new SQLException(e.getMessage());
		}
	}

	public int getFetchDirection() throws SQLException {
		return this.fetchDirection;
	}

	public int getFetchSize() throws SQLException {
		try {
			return (int)this.sparql.getLimit();
		}
		catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
	}

	public float getFloat(int columnIndex) throws SQLException {
		try {
			return this.getNextSolutionAsLiteral(columnIndex).getFloat();
		}
		catch (Exception e){
			throw new SQLException(e.getMessage());
		}
	}

	
	public float getFloat(String columnLabel) throws SQLException {
		try {
			return this.getNextSolutionAsLiteral(columnLabel).getFloat();
		}
		catch (Exception e){
			throw new SQLException(e.getMessage());
		}
	}

	
	public int getHoldability() throws SQLException {
		return this.statement.getResultSetHoldability();
	}

	
	public int getInt(int columnIndex) throws SQLException {
		try {
			return this.getNextSolutionAsLiteral(columnIndex).getInt();
		}
		catch (Exception e){
			throw new SQLException(e.getMessage());
		}
	}

	public int getInt(String columnLabel) throws SQLException {
		try {
			return this.getNextSolutionAsLiteral(columnLabel).getInt();
		}
		catch (Exception e){
			throw new SQLException(e.getMessage());
		}
	}

	public long getLong(int columnIndex) throws SQLException {
		try {
			return this.getNextSolutionAsLiteral(columnIndex).getLong();
		}
		catch (Exception e){
			throw new SQLException(e.getMessage());
		}
	}

	@Override
	public long getLong(String columnLabel) throws SQLException {
		try {
			return this.getNextSolutionAsLiteral(columnLabel).getLong();
		}
		catch (Exception e){
			throw new SQLException(e.getMessage());
		}
	}

	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getNCharacterStream(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getNCharacterStream(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NClob getNClob(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NClob getNClob(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNString(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNString(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObject(int columnIndex) throws SQLException {
		try {
			String column = this.columnNames.get(columnIndex);
			return this.getObject(column);
		}
		catch (Exception e){
			throw new SQLException(e.getMessage());
		}
	}

	@Override
	public Object getObject(String columnLabel) throws SQLException {
		try {
			QuerySolution solution = this.internalResultSet.get(this.currentRow);
			return solution.get(columnLabel);
		}
		catch (Exception e){
			throw new SQLException(e.getMessage());
		}
	}

	@Override
	public Object getObject(int columnIndex, Map<String, Class<?>> map)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObject(String columnLabel, Map<String, Class<?>> map)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ref getRef(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ref getRef(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRow() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public RowId getRowId(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RowId getRowId(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML getSQLXML(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML getSQLXML(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public short getShort(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public short getShort(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Statement getStatement() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getString(int columnIndex) throws SQLException {
		try {
			return this.getNextSolutionAsLiteral(columnIndex).getString();
		}
		catch (Exception e){
			throw new SQLException(e.getMessage());
		}
	}

	@Override
	public String getString(String columnLabel) throws SQLException {
		try {
			return this.getNextSolutionAsLiteral(columnLabel).getString();
		}
		catch (Exception e){
			throw new SQLException(e.getMessage());
		}
	}

	@Override
	public Time getTime(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time getTime(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time getTime(int columnIndex, Calendar cal) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time getTime(String columnLabel, Calendar cal) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getTimestamp(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getTimestamp(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getTimestamp(int columnIndex, Calendar cal)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getTimestamp(String columnLabel, Calendar cal)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getType() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public URL getURL(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URL getURL(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getUnicodeStream(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getUnicodeStream(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertRow() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isAfterLast() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isBeforeFirst() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFirst() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLast() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean last() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void moveToCurrentRow() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveToInsertRow() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean next() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean previous() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void refreshRow() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean relative(int rows) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rowDeleted() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rowInserted() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rowUpdated() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setFetchDirection(int direction) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFetchSize(int rows) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateArray(int columnIndex, Array x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateArray(String columnLabel, Array x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x, int length)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x, int length)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x, long length)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x, long length)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBigDecimal(int columnIndex, BigDecimal x)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBigDecimal(String columnLabel, BigDecimal x)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x, int length)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x, int length)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x, long length)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x,
			long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBlob(int columnIndex, Blob x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBlob(String columnLabel, Blob x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBlob(int columnIndex, InputStream inputStream)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBlob(String columnLabel, InputStream inputStream)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBlob(int columnIndex, InputStream inputStream, long length)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBlob(String columnLabel, InputStream inputStream,
			long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBoolean(int columnIndex, boolean x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBoolean(String columnLabel, boolean x)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateByte(int columnIndex, byte x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateByte(String columnLabel, byte x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBytes(int columnIndex, byte[] x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBytes(String columnLabel, byte[] x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x, int length)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader,
			int length) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x, long length)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader,
			long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateClob(int columnIndex, Clob x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateClob(String columnLabel, Clob x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateClob(int columnIndex, Reader reader) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateClob(String columnLabel, Reader reader)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateClob(int columnIndex, Reader reader, long length)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateClob(String columnLabel, Reader reader, long length)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateDate(int columnIndex, Date x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateDate(String columnLabel, Date x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateDouble(int columnIndex, double x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateDouble(String columnLabel, double x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateFloat(int columnIndex, float x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateFloat(String columnLabel, float x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateInt(int columnIndex, int x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateInt(String columnLabel, int x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateLong(int columnIndex, long x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateLong(String columnLabel, long x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNCharacterStream(int columnIndex, Reader x)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNCharacterStream(String columnLabel, Reader reader)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNCharacterStream(int columnIndex, Reader x, long length)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNCharacterStream(String columnLabel, Reader reader,
			long length) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNClob(int columnIndex, NClob clob) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNClob(String columnLabel, NClob clob) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNClob(int columnIndex, Reader reader) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNClob(String columnLabel, Reader reader)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNClob(int columnIndex, Reader reader, long length)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNClob(String columnLabel, Reader reader, long length)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNString(int columnIndex, String string)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNString(String columnLabel, String string)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNull(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNull(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateObject(int columnIndex, Object x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateObject(String columnLabel, Object x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateObject(int columnIndex, Object x, int scaleOrLength)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateObject(String columnLabel, Object x, int scaleOrLength)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateRef(int columnIndex, Ref x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateRef(String columnLabel, Ref x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateRow() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateRowId(int columnIndex, RowId x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateRowId(String columnLabel, RowId x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateSQLXML(int columnIndex, SQLXML xmlObject)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateSQLXML(String columnLabel, SQLXML xmlObject)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateShort(int columnIndex, short x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateShort(String columnLabel, short x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateString(int columnIndex, String x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateString(String columnLabel, String x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateTime(int columnIndex, Time x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateTime(String columnLabel, Time x) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateTimestamp(int columnIndex, Timestamp x)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateTimestamp(String columnLabel, Timestamp x)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean wasNull() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}

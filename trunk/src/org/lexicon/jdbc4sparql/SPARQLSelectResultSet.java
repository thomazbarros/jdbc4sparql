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
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import com.hp.hpl.jena.query.*;
import java.util.List;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
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
	private int fetchSize;
	
	public SPARQLSelectResultSet (com.hp.hpl.jena.query.ResultSet resultSet, SPARQLStatement statement, Query sqarql){
		System.out.println("prob2");
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
			this.internalResultSet.add(this.resultSet.next());
		}
	}
	
	public void setRow(int row) {
		this.currentRow = row;
	}
	
	public Vector<String> getColumnNames() {
		return this.columnNames;
	}
	
	public Vector<QuerySolution> getInternalResultSet () {
		return this.internalResultSet;
	}
	
	public boolean absolute(int row) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
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
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			this.currentRow = this.internalResultSet.size() + 1;
		}
	}

	
	public void beforeFirst() throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
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
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return this.columnNames.indexOf(columnLabel);
		}
	}

	
	public boolean first() throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
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
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return null;
		}
	}
	
	public Array getArray(String columnLabel) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return null;
		}
	}


	public InputStream getAsciiStream(int columnIndex) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return null;
		}
	}

	public InputStream getAsciiStream(String columnLabel) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return null;
		}
	}

	public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			try {
				return new BigDecimal(this.getString(columnIndex));
			}
			catch (Exception e){
				throw new SQLException(e.getMessage());
			}
		}
	}

	public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			try {
				return new BigDecimal(this.getString(columnLabel));
			}
			catch (Exception e){
				throw new SQLException(e.getMessage());
			}
		}
	}

	public BigDecimal getBigDecimal(int columnIndex, int scale)
			throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
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
	}

	public BigDecimal getBigDecimal(String columnLabel, int scale)
			throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
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
	}

	public InputStream getBinaryStream(int columnIndex) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return null;
		}
	}

	public InputStream getBinaryStream(String columnLabel) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return null;
		}
	}

	
	public Blob getBlob(int columnIndex) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return null;
		}
	}

	
	public Blob getBlob(String columnLabel) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return null;
		}
	}

	
	public Literal getNextSolutionAsLiteral(int columnIndex) {
		return this.getNextSolutionAsLiteral(this.columnNames.get(columnIndex));
	}
	
	public Literal getNextSolutionAsLiteral(String columnName) {
		QuerySolution solution = this.internalResultSet.get(this.currentRow);
		return solution.getLiteral(columnName);
	}
	
	public Resource getNextSolutionAsResource(int columnIndex) {
		return this.getNextSolutionAsResource(this.columnNames.get(columnIndex));
	}
	
	public Resource getNextSolutionAsResource(String columnName) {
		QuerySolution solution = this.internalResultSet.get(this.currentRow);
		return solution.getResource(columnName);
	}
	
	
	public boolean getBoolean(int columnIndex) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			try {
				return this.getNextSolutionAsLiteral(columnIndex).getBoolean();
			}
			catch (Exception e){
				throw new SQLException(e.getMessage());
			}
		}
	}

	public boolean getBoolean(String columnLabel) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			try {
				return this.getNextSolutionAsLiteral(columnLabel).getBoolean();
			}
			catch (Exception e){
				throw new SQLException(e.getMessage());
			}
		}
	}

	public byte getByte(int columnIndex) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return 0;
		}
	}

	public byte getByte(String columnLabel) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return 0;
		}
	}

	public byte[] getBytes(int columnIndex) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return null;
		}
	}

	public byte[] getBytes(String columnLabel) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return null;
		}
	}

	public Reader getCharacterStream(int columnIndex) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return null;
		}
	}

	
	public Reader getCharacterStream(String columnLabel) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return null;
		}
	}

	
	public Clob getClob(int columnIndex) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return null;
		}
	}

	public Clob getClob(String columnLabel) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return null;
		}
	}

	public int getConcurrency() throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return this.concurrency;
		}
	}

	public String getCursorName() throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	public Date getDate(int columnIndex) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			try {
				XSDDateTime val = (XSDDateTime)this.getNextSolutionAsLiteral(columnIndex).getValue();
				return (Date)val.asCalendar().getTime();
			}
			catch (Exception e){
				throw new SQLException(e.getMessage());
			}
		}
	}

	public Date getDate(String columnLabel) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			try {
				XSDDateTime val = (XSDDateTime)this.getNextSolutionAsLiteral(columnLabel).getValue();
				return (Date)val.asCalendar().getTime();
			}
			catch (Exception e){
				throw new SQLException(e.getMessage());
			}
		}
	}

	public Date getDate(int columnIndex, Calendar cal) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return this.getDate(columnIndex);
		}
	}

	public Date getDate(String columnLabel, Calendar cal) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return this.getDate(columnLabel);
		}
	}

	public double getDouble(int columnIndex) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			try {
				return this.getNextSolutionAsLiteral(columnIndex).getDouble();
			}
			catch (Exception e){
				throw new SQLException(e.getMessage());
			}
		}
	}

	public double getDouble(String columnLabel) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			try {
				return this.getNextSolutionAsLiteral(columnLabel).getDouble();
			}
			catch (Exception e){
				throw new SQLException(e.getMessage());
			}
		}
	}

	public int getFetchDirection() throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return this.fetchDirection;
		}
	}

	public int getFetchSize() throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			try {
				return (int)this.sparql.getLimit();
			}
			catch (Exception e) {
				throw new SQLException(e.getMessage());
			}
		}
	}

	public float getFloat(int columnIndex) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			try {
				return this.getNextSolutionAsLiteral(columnIndex).getFloat();
			}
			catch (Exception e){
				throw new SQLException(e.getMessage());
			}
		}
	}

	
	public float getFloat(String columnLabel) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			try {
				return this.getNextSolutionAsLiteral(columnLabel).getFloat();
			}
			catch (Exception e){
				throw new SQLException(e.getMessage());
			}
		}
	}

	
	public int getHoldability() throws SQLException {
		return this.statement.getResultSetHoldability();
	}

	
	public int getInt(int columnIndex) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			try {
				return this.getNextSolutionAsLiteral(columnIndex).getInt();
			}
			catch (Exception e){
				throw new SQLException(e.getMessage());
			}
		}
	}

	public int getInt(String columnLabel) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			try {
				return this.getNextSolutionAsLiteral(columnLabel).getInt();
			}
			catch (Exception e){
				throw new SQLException(e.getMessage());
			}
		}
	}

	public long getLong(int columnIndex) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			try {
				return this.getNextSolutionAsLiteral(columnIndex).getLong();
			}
			catch (Exception e){
				throw new SQLException(e.getMessage());
			}
		}
	}

	public long getLong(String columnLabel) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			try {
				return this.getNextSolutionAsLiteral(columnLabel).getLong();
			}
			catch (Exception e){
				throw new SQLException(e.getMessage());
			}
		}
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		return this.rsm;
	}

	public Reader getNCharacterStream(int columnIndex) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return null;
		}
	}

	public Reader getNCharacterStream(String columnLabel) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return null;
		}
	}

	public NClob getNClob(int columnIndex) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return null;
		}
	}

	public NClob getNClob(String columnLabel) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return null;
		}
	}

	public String getNString(int columnIndex) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return null;
		}
	}

	public String getNString(String columnLabel) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return null;
		}
	}

	public Object getObject(int columnIndex) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			try {
				String column = this.columnNames.get(columnIndex-1);
				System.out.println(column);
				return this.getObject(column);
			}
			catch (Exception e){
				throw new SQLException(e.getMessage());
			}
		}
	}

	public Object getObject(String columnLabel) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			try {
				QuerySolution solution = this.internalResultSet.get(this.currentRow-1);
				return solution.get(columnLabel);
			}
			catch (Exception e){
				throw new SQLException(e.getMessage());
			}
		}
	}

	public Object getObject(int columnIndex, Map<String, Class<?>> map)
			throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return this.getObject(columnIndex);
		}
	}

	public Object getObject(String columnLabel, Map<String, Class<?>> map)
			throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return this.getObject(columnLabel);
		}
	}

	public Ref getRef(int columnIndex) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	public Ref getRef(String columnLabel) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported"); 
	}

	public int getRow() throws SQLException {
		return this.currentRow;
	}

	public RowId getRowId(int columnIndex) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported"); 
	}

	public RowId getRowId(String columnLabel) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported"); 
	}

	public SQLXML getSQLXML(int columnIndex) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	public SQLXML getSQLXML(String columnLabel) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	public short getShort(int columnIndex) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			try {
				return this.getNextSolutionAsLiteral(columnIndex).getShort();
			}
			catch (Exception e){
				throw new SQLException(e.getMessage());
			}
		}
	}

	public short getShort(String columnLabel) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			try {
				return this.getNextSolutionAsLiteral(columnLabel).getShort();
			}
			catch (Exception e){
				throw new SQLException(e.getMessage());
			}
		}
	}

	public Statement getStatement() throws SQLException {
		return this.statement;
	}

	public String getString(int columnIndex) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			try {
				return this.getString(this.columnNames.get(columnIndex));
			}
			catch (Exception e){
				throw new SQLException(e.getMessage());
			}
		}
	}

	public String getString(String columnLabel) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			try {
				return this.getObject(columnLabel).toString();
			}
			catch (Exception e){
				throw new SQLException(e.getMessage());
			}
		}
	}

	public Time getTime(int columnIndex) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return new Time(this.getDate(columnIndex).getTime());
		}
	}

	public Time getTime(String columnLabel) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return new Time(this.getDate(columnLabel).getTime());
		}
	}
	
	public Time getTime(int columnIndex, Calendar cal) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return new Time(this.getDate(columnIndex).getTime());
		}
	}

	public Time getTime(String columnLabel, Calendar cal) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return new Time(this.getDate(columnLabel).getTime());
		}
	}

	public Timestamp getTimestamp(int columnIndex) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return new Timestamp(this.getDate(columnIndex).getTime());
		}
	}

	public Timestamp getTimestamp(String columnLabel) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return new Timestamp(this.getDate(columnLabel).getTime());
		}
	}

	public Timestamp getTimestamp(int columnIndex, Calendar cal)
			throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return new Timestamp(this.getDate(columnIndex).getTime());
		}
	}

	public Timestamp getTimestamp(String columnLabel, Calendar cal)
			throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return new Timestamp(this.getDate(columnLabel).getTime());
		}
	}

	public int getType() throws SQLException {
		return this.type;
	}

	public URL getURL(int columnIndex) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return this.getURL(this.columnNames.get(columnIndex));
		}
	}

	public URL getURL(String columnLabel) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			try {
				RDFNode rdfn = (RDFNode)this.getObject(columnLabel);
				if (rdfn.isResource()) {
					Resource res = (Resource)rdfn;
					return new java.net.URL(res.getURI());
				}
				else {
					return new java.net.URL(rdfn.toString());
				}
			}
			catch (Exception e) {
				throw new SQLException(e.getMessage());
			}
		}
	}

	
	public InputStream getUnicodeStream(int columnIndex) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return null;
		}
	}

	public InputStream getUnicodeStream(String columnLabel) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			return null;
		}
	}

	public SQLWarning getWarnings() throws SQLException {
		return null;
	}

	public void insertRow() throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	public boolean isAfterLast() throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			if (this.currentRow > this.internalResultSet.size()) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	public boolean isBeforeFirst() throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			if (this.currentRow == 0) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	public boolean isClosed() throws SQLException {
		return this.closed;
	}

	public boolean isFirst() throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			if (this.currentRow == 0) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	public boolean isLast() throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else {
			if (this.currentRow == this.internalResultSet.size()) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	public boolean last() throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else{
			this.currentRow = this.internalResultSet.size();
			return true;
		}
	}

	public void moveToCurrentRow() throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");

	}

	public void moveToInsertRow() throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	public boolean next() throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else{
			if (this.isLast()) {
				return false;
			}
			else {
				this.currentRow++;
				return true;
			}
		}
	}

	public boolean previous() throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else{
			if (this.isBeforeFirst()) {
				return false;
			}
			else {
				this.currentRow--;
				return true;
			}
		}
	}

	
	public void refreshRow() throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	public boolean relative(int rows) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	public boolean rowDeleted() throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	public boolean rowInserted() throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	public boolean rowUpdated() throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	public void setFetchDirection(int direction) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else{
			this.fetchDirection = direction;
		}
	}

	public void setFetchSize(int rows) throws SQLException {
		if (this.isClosed()) {
			throw new SQLException("illegal operation");
		}
		else{
			this.fetchSize = rows;
		}
	}

	public void updateArray(int columnIndex, Array x) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	public void updateArray(String columnLabel, Array x) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	public void updateAsciiStream(int columnIndex, InputStream x)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	public void updateAsciiStream(String columnLabel, InputStream x)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	public void updateAsciiStream(int columnIndex, InputStream x, int length)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	public void updateAsciiStream(String columnLabel, InputStream x, int length)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	public void updateAsciiStream(int columnIndex, InputStream x, long length)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	public void updateAsciiStream(String columnLabel, InputStream x, long length)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	public void updateBigDecimal(int columnIndex, BigDecimal x)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	public void updateBigDecimal(String columnLabel, BigDecimal x)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	public void updateBinaryStream(int columnIndex, InputStream x)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	public void updateBinaryStream(String columnLabel, InputStream x)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	public void updateBinaryStream(int columnIndex, InputStream x, int length)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	public void updateBinaryStream(String columnLabel, InputStream x, int length)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	public void updateBinaryStream(int columnIndex, InputStream x, long length)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateBinaryStream(String columnLabel, InputStream x,
			long length) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateBlob(int columnIndex, Blob x) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateBlob(String columnLabel, Blob x) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateBlob(int columnIndex, InputStream inputStream)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateBlob(String columnLabel, InputStream inputStream)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateBlob(int columnIndex, InputStream inputStream, long length)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateBlob(String columnLabel, InputStream inputStream,
			long length) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateBoolean(int columnIndex, boolean x) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateBoolean(String columnLabel, boolean x)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateByte(int columnIndex, byte x) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateByte(String columnLabel, byte x) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateBytes(int columnIndex, byte[] x) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateBytes(String columnLabel, byte[] x) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateCharacterStream(int columnIndex, Reader x)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateCharacterStream(String columnLabel, Reader reader)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateCharacterStream(int columnIndex, Reader x, int length)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateCharacterStream(String columnLabel, Reader reader,
			int length) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateCharacterStream(int columnIndex, Reader x, long length)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateCharacterStream(String columnLabel, Reader reader,
			long length) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateClob(int columnIndex, Clob x) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateClob(String columnLabel, Clob x) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateClob(int columnIndex, Reader reader) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateClob(String columnLabel, Reader reader)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateClob(int columnIndex, Reader reader, long length)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateClob(String columnLabel, Reader reader, long length)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateDate(int columnIndex, Date x) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateDate(String columnLabel, Date x) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateDouble(int columnIndex, double x) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateDouble(String columnLabel, double x) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateFloat(int columnIndex, float x) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateFloat(String columnLabel, float x) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateInt(int columnIndex, int x) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateInt(String columnLabel, int x) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateLong(int columnIndex, long x) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateLong(String columnLabel, long x) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateNCharacterStream(int columnIndex, Reader x)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateNCharacterStream(String columnLabel, Reader reader)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateNCharacterStream(int columnIndex, Reader x, long length)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateNCharacterStream(String columnLabel, Reader reader,
			long length) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateNClob(int columnIndex, NClob clob) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateNClob(String columnLabel, NClob clob) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateNClob(int columnIndex, Reader reader) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateNClob(String columnLabel, Reader reader)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateNClob(int columnIndex, Reader reader, long length)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateNClob(String columnLabel, Reader reader, long length)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateNString(int columnIndex, String string)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateNString(String columnLabel, String string)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateNull(int columnIndex) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateNull(String columnLabel) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateObject(int columnIndex, Object x) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateObject(String columnLabel, Object x) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateObject(int columnIndex, Object x, int scaleOrLength)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateObject(String columnLabel, Object x, int scaleOrLength)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateRef(int columnIndex, Ref x) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateRef(String columnLabel, Ref x) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateRow() throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateRowId(int columnIndex, RowId x) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateRowId(String columnLabel, RowId x) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateSQLXML(int columnIndex, SQLXML xmlObject)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateSQLXML(String columnLabel, SQLXML xmlObject)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateShort(int columnIndex, short x) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateShort(String columnLabel, short x) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateString(int columnIndex, String x) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateString(String columnLabel, String x) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateTime(int columnIndex, Time x) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateTime(String columnLabel, Time x) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateTimestamp(int columnIndex, Timestamp x)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void updateTimestamp(String columnLabel, Timestamp x)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	public boolean wasNull() throws SQLException {
		return false;
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}

}

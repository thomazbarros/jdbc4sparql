package org.lexicon.jdbc4sparql;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.datatypes.xsd.XSDDateTime;
import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.graph.query.QueryHandler;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.shared.Command;
import com.hp.hpl.jena.shared.Lock;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.shared.ReificationStyle;
import com.hp.hpl.jena.query.*;

public class SPARQLConstructResultSet implements ResultSet, Model {

	private Model model;
	private Vector<com.hp.hpl.jena.rdf.model.Statement> internalResultSet; 
	private Query sparql;
	private SPARQLStatement statement;
	private int currentRow;
	private int type;
	private Vector<String> columnNames;
	private boolean closed;
	private SPARQLConstructResultSetMetaData rsm;
	private int concurrency;
	private int fetchDirection;
	private int fetchSize;
	
	public SPARQLConstructResultSet (Model model, SPARQLStatement statement, Query sparql) {
		
		this.model = model;
		this.statement = statement;
		this.sparql = sparql;
		this.currentRow = 0;
		this.rsm = new SPARQLConstructResultSetMetaData(this);
		
		this.closed = false;
		this.fetchDirection = ResultSet.FETCH_FORWARD;
		this.concurrency = ResultSet.CONCUR_READ_ONLY;
		this.internalResultSet = new Vector<com.hp.hpl.jena.rdf.model.Statement>();
		
		StmtIterator stmtIterator = this.model.listStatements();
		
		while (stmtIterator.hasNext()){
			this.internalResultSet.add((com.hp.hpl.jena.rdf.model.Statement)stmtIterator.nextStatement());
		}
		this.columnNames = new Vector<String>();
		this.columnNames.add(new String("s"));
		this.columnNames.add(new String("p"));
		this.columnNames.add(new String("o"));
		
	}
	
	public Model getModel() {
		return this.model;
	}
	
	public void setRow(int row) {
		this.currentRow = row;
	}
	
	public Vector<String> getColumnNames() {
		return this.columnNames;
	}
	
	
	
	public Vector<com.hp.hpl.jena.rdf.model.Statement> getInternalResultSet () {
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

	
	public void close() {
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
		return this.getNextSolutionAsLiteral(this.columnNames.get(columnIndex-1));
	}
	
	public Literal getNextSolutionAsLiteral(String columnName) {
		if (columnName == "s") {
			return null;
		}
		else if (columnName == "p") {
			return null;
		}
		else {
			return (Literal)this.internalResultSet.get(this.currentRow-1).getObject();
		}
	}
	
	public Property getNextSolutionAsProperty(int columnIndex) {
		return this.getNextSolutionAsProperty(this.columnNames.get(columnIndex-1));
	}
	
	public Property getNextSolutionAsProperty(String columnName) {
		if (columnName.equals("s")) {
			return null;
		}
		else if (columnName.equals("p")) {
			return (Property)this.internalResultSet.get(this.currentRow-1).getPredicate();
		}
		else {
			return null;
		}
	}
	
	public Resource getNextSolutionAsResource(int columnIndex) {
		return this.getNextSolutionAsResource(this.columnNames.get(columnIndex-1));
	}
	
	public Resource getNextSolutionAsResource(String columnName) {
		if (columnName.equals("s")) {
			return (Resource)this.internalResultSet.get(this.currentRow-1).getSubject();
		}
		else if (columnName.equals("p")) {
			return (Resource)this.internalResultSet.get(this.currentRow-1).getPredicate();
		}
		else {
			return (Resource)this.internalResultSet.get(this.currentRow-1).getObject();
		}
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
				if (columnLabel.equals("s")) {
					return this.getNextSolutionAsResource("s");
				}
				else if (columnLabel.equals("p")) {
					return this.getNextSolutionAsProperty("p");
				}
				else {
					return (RDFNode)this.internalResultSet.get(this.currentRow-1).getObject();
				}
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

	public boolean isClosed() {
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

	public Model abort() {
		return this.model.abort();
	}

	@Override
	public Model add(com.hp.hpl.jena.rdf.model.Statement arg0) {
		return this.model.add(arg0);
	}

	@Override
	public Model add(com.hp.hpl.jena.rdf.model.Statement[] arg0) {
		return this.model.add(arg0);
	}

	@Override
	public Model add(List<com.hp.hpl.jena.rdf.model.Statement> arg0) {
		return this.model.add(arg0);
	}

	@Override
	public Model add(StmtIterator arg0) {
		return this.model.add(arg0);
	}

	@Override
	public Model add(Model arg0) {
		return this.model.add(arg0);
	}

	@Override
	public Model add(Model arg0, boolean arg1) {
		return this.model.add(arg0);
	}

	@Override
	public Model begin() {
		return this.model.begin();
	}

	@Override
	public Model commit() {
		return this.model.commit();
	}

	@Override
	public boolean contains(com.hp.hpl.jena.rdf.model.Statement arg0) {
		return this.model.contains(arg0);
	}

	@Override
	public boolean contains(Resource arg0, Property arg1) {
		return this.model.contains(arg0, arg1);
	}

	@Override
	public boolean contains(Resource arg0, Property arg1, RDFNode arg2) {
		return this.model.contains(arg0, arg1, arg2);
	}

	@Override
	public boolean containsAll(StmtIterator arg0) {
		return this.model.containsAll(arg0);
	}

	@Override
	public boolean containsAll(Model arg0) {
		return this.model.containsAll(arg0);
	}

	@Override
	public boolean containsAny(StmtIterator arg0) {
		return this.model.containsAny(arg0);
	}

	@Override
	public boolean containsAny(Model arg0) {
		return this.model.containsAny(arg0);
	}

	@Override
	public boolean containsResource(RDFNode arg0) {
		return this.model.containsResource(arg0);
	}

	@Override
	public RDFList createList() {
		return this.model.createList();
	}

	@Override
	public RDFList createList(Iterator<? extends RDFNode> arg0) {
		return this.model.createList(arg0);
	}

	@Override
	public RDFList createList(RDFNode[] arg0) {
		return this.model.createList(arg0);
	}

	@Override
	public Literal createLiteral(String arg0, String arg1) {
		return this.model.createLiteral(arg0, arg1);
	}

	@Override
	public Literal createLiteral(String arg0, boolean arg1) {
		return this.model.createLiteral(arg0, arg1);
	}

	@Override
	public Property createProperty(String arg0, String arg1) {
		return this.model.createProperty(arg0, arg1);
	}

	@Override
	public ReifiedStatement createReifiedStatement(
			com.hp.hpl.jena.rdf.model.Statement arg0) {
		return this.model.createReifiedStatement(arg0);
	}

	@Override
	public ReifiedStatement createReifiedStatement(String arg0,
			com.hp.hpl.jena.rdf.model.Statement arg1) {
		return this.model.createReifiedStatement(arg0, arg1);
	}

	@Override
	public Resource createResource() {
		return this.model.createResource();
	}

	@Override
	public Resource createResource(AnonId arg0) {
		return this.model.createResource(arg0);
	}

	@Override
	public Resource createResource(String arg0) {
		return this.model.createResource(arg0);
	}

	@Override
	public com.hp.hpl.jena.rdf.model.Statement createStatement(Resource arg0,
			Property arg1, RDFNode arg2) {
		return this.model.createStatement(arg0, arg1, arg2);
	}

	@Override
	public Literal createTypedLiteral(Object arg0) {
		return this.model.createTypedLiteral(arg0);
	}

	@Override
	public Literal createTypedLiteral(String arg0, RDFDatatype arg1) {
		return this.model.createTypedLiteral(arg0, arg1);
	}

	@Override
	public Literal createTypedLiteral(Object arg0, RDFDatatype arg1) {
		return this.model.createTypedLiteral(arg0, arg1);
	}

	@Override
	public Model difference(Model arg0) {
		return this.model.difference(arg0);
	}

	@Override
	public Object executeInTransaction(Command arg0) {
		return this.model.executeInTransaction(arg0);
	}

	@Override
	public Resource getAnyReifiedStatement(
			com.hp.hpl.jena.rdf.model.Statement arg0) {
		return this.model.getAnyReifiedStatement(arg0);
	}

	@Override
	public Lock getLock() {
		return this.model.getLock();
	}

	@Override
	public Property getProperty(String arg0, String arg1) {
		return this.model.getProperty(arg0, arg1);
	}

	@Override
	public com.hp.hpl.jena.rdf.model.Statement getProperty(Resource arg0,
			Property arg1) {
		return this.model.getProperty(arg0, arg1);
	}

	@Override
	public ReificationStyle getReificationStyle() {
		return this.model.getReificationStyle();
	}

	@Override
	public com.hp.hpl.jena.rdf.model.Statement getRequiredProperty(
			Resource arg0, Property arg1) {
		return this.model.getRequiredProperty(arg0, arg1);
	}

	@Override
	public Resource getResource(String arg0) {
		return this.model.getResource(arg0);
	}

	@Override
	public boolean independent() {
		return this.model.independent();
	}

	@Override
	public Model intersection(Model arg0) {
		return this.model.intersection(arg0);
	}

	@Override
	public boolean isEmpty() {
		return this.model.isEmpty();
	}

	@Override
	public boolean isIsomorphicWith(Model arg0) {
		return this.model.isIsomorphicWith(arg0);
	}

	@Override
	public boolean isReified(com.hp.hpl.jena.rdf.model.Statement arg0) {
		return this.model.isReified(arg0);
	}

	@Override
	public NsIterator listNameSpaces() {
		return this.model.listNameSpaces();
	}

	@Override
	public NodeIterator listObjects() {
		return this.model.listObjects();
	}

	@Override
	public NodeIterator listObjectsOfProperty(Property arg0) {
		return this.model.listObjectsOfProperty(arg0);
	}

	@Override
	public NodeIterator listObjectsOfProperty(Resource arg0, Property arg1) {
		return this.model.listObjectsOfProperty(arg0, arg1);
	}

	@Override
	public RSIterator listReifiedStatements() {
		return this.model.listReifiedStatements();
	}

	@Override
	public RSIterator listReifiedStatements(
			com.hp.hpl.jena.rdf.model.Statement arg0) {
		return this.model.listReifiedStatements(arg0);
	}

	@Override
	public ResIterator listResourcesWithProperty(Property arg0) {
		return this.model.listResourcesWithProperty(arg0);
	}

	@Override
	public ResIterator listResourcesWithProperty(Property arg0, RDFNode arg1) {
		return this.model.listResourcesWithProperty(arg0, arg1);
	}

	@Override
	public StmtIterator listStatements() {
		return this.model.listStatements();
	}

	@Override
	public StmtIterator listStatements(Selector arg0) {
		return this.model.listStatements(arg0);
	}

	@Override
	public StmtIterator listStatements(Resource arg0, Property arg1,
			RDFNode arg2) {
		return this.model.listStatements(arg0, arg1, arg2);
	}

	@Override
	public ResIterator listSubjects() {
		return this.model.listSubjects();
	}

	@Override
	public ResIterator listSubjectsWithProperty(Property arg0) {
		return this.model.listSubjectsWithProperty(arg0);
	}

	@Override
	public ResIterator listSubjectsWithProperty(Property arg0, RDFNode arg1) {
		return this.model.listSubjectsWithProperty(arg0, arg1);
	}

	@Override
	public Model notifyEvent(Object arg0) {
		return this.model.notifyEvent(arg0);
	}

	@Override
	public Model query(Selector arg0) {
		return this.model.query(arg0);
	}

	@Override
	public Model read(String arg0) {
		return this.model.read(arg0);
	}

	@Override
	public Model read(InputStream arg0, String arg1) {
		return this.model.read(arg0, arg1);
	}

	@Override
	public Model read(Reader arg0, String arg1) {
		return this.model.read(arg0, arg1);
	}

	@Override
	public Model read(String arg0, String arg1) {
		return this.model.read(arg0, arg1);
	}

	@Override
	public Model read(InputStream arg0, String arg1, String arg2) {
		return this.model.read(arg0, arg1, arg2);
	}

	@Override
	public Model read(Reader arg0, String arg1, String arg2) {
		return this.model.read(arg0, arg1, arg2);
	}

	@Override
	public Model read(String arg0, String arg1, String arg2) {
		return this.model.read(arg0, arg1, arg2);
	}

	@Override
	public Model register(ModelChangedListener arg0) {
		return this.model.register(arg0);
	}

	@Override
	public Model remove(com.hp.hpl.jena.rdf.model.Statement[] arg0) {
		return this.model.remove(arg0);
	}

	@Override
	public Model remove(List<com.hp.hpl.jena.rdf.model.Statement> arg0) {
		return this.model.remove(arg0);
	}

	public Model remove(com.hp.hpl.jena.rdf.model.Statement arg0) {
		return this.model.remove(arg0);
	}

	public Model removeAll() {
		return this.model.removeAll();
	}

	@Override
	public Model removeAll(Resource arg0, Property arg1, RDFNode arg2) {
		return this.model.removeAll(arg0, arg1, arg2);
	}

	@Override
	public void removeAllReifications(com.hp.hpl.jena.rdf.model.Statement arg0) {
		this.model.removeAllReifications(arg0);
	}

	@Override
	public void removeReification(ReifiedStatement arg0) {
		this.model.removeReification(arg0);
	}

	@Override
	public long size() {
		return this.model.size();
	}

	@Override
	public boolean supportsSetOperations() {
		return this.model.supportsSetOperations();
	}

	@Override
	public boolean supportsTransactions() {
		return this.model.supportsTransactions();
	}

	@Override
	public Model union(Model arg0) {
		return this.model.union(arg0);
	}

	@Override
	public Model unregister(ModelChangedListener arg0) {
		return this.model.unregister(arg0);
	}

	@Override
	public Model write(Writer arg0) {
		return this.model.write(arg0);
	}

	@Override
	public Model write(OutputStream arg0) {
		return this.model.write(arg0);
	}

	@Override
	public Model write(Writer arg0, String arg1) {
		return this.model.write(arg0, arg1);
	}

	@Override
	public Model write(OutputStream arg0, String arg1) {
		return this.model.write(arg0, arg1);
	}

	@Override
	public Model write(Writer arg0, String arg1, String arg2) {
		return this.model.write(arg0, arg1, arg2);
	}

	@Override
	public Model write(OutputStream arg0, String arg1, String arg2) {
		return this.model.write(arg0, arg1, arg2);
	}

	@Override
	public Model add(Resource arg0, Property arg1, RDFNode arg2) {
		return this.model.add(arg0, arg1, arg2);
	}

	@Override
	public Model add(Resource arg0, Property arg1, String arg2) {
		return this.model.add(arg0, arg1, arg2);
	}

	@Override
	public Model add(Resource arg0, Property arg1, String arg2, RDFDatatype arg3) {
		return this.model.add(arg0, arg1, arg2, arg3);
	}

	@Override
	public Model add(Resource arg0, Property arg1, String arg2, boolean arg3) {
		return this.model.add(arg0, arg1, arg2, arg3);
	}

	@Override
	public Model add(Resource arg0, Property arg1, String arg2, String arg3) {
		return this.model.add(arg0, arg1, arg2, arg3);
	}

	@Override
	public Model addLiteral(Resource arg0, Property arg1, boolean arg2) {
		return this.model.addLiteral(arg0, arg1, arg2);
	}

	@Override
	public Model addLiteral(Resource arg0, Property arg1, long arg2) {
		return this.model.addLiteral(arg0, arg1, arg2);
	}

	@Override
	public Model addLiteral(Resource arg0, Property arg1, int arg2) {
		return this.model.addLiteral(arg0, arg1, arg2);
	}

	@Override
	public Model addLiteral(Resource arg0, Property arg1, char arg2) {
		return this.model.addLiteral(arg0, arg1, arg2);
	}

	@Override
	public Model addLiteral(Resource arg0, Property arg1, float arg2) {
		return this.model.addLiteral(arg0, arg1, arg2);
	}

	@Override
	public Model addLiteral(Resource arg0, Property arg1, double arg2) {
		return this.model.addLiteral(arg0, arg1, arg2);
	}

	@Override
	public Model addLiteral(Resource arg0, Property arg1, Object arg2) {
		return this.model.addLiteral(arg0, arg1, arg2);
	}

	@Override
	public Model addLiteral(Resource arg0, Property arg1, Literal arg2) {
		return this.model.addLiteral(arg0, arg1, arg2);
	}

	@Override
	public boolean contains(Resource arg0, Property arg1, String arg2) {
		return this.model.contains(arg0, arg1, arg2);
	}

	@Override
	public boolean contains(Resource arg0, Property arg1, String arg2,
			String arg3) {
		return this.model.contains(arg0, arg1, arg2, arg3);
	}

	@Override
	public boolean containsLiteral(Resource arg0, Property arg1, boolean arg2) {
		return this.model.containsLiteral(arg0, arg1, arg2);
	}

	@Override
	public boolean containsLiteral(Resource arg0, Property arg1, long arg2) {
		return this.model.containsLiteral(arg0, arg1, arg2);
	}

	@Override
	public boolean containsLiteral(Resource arg0, Property arg1, int arg2) {
		return this.model.containsLiteral(arg0, arg1, arg2);
	}

	@Override
	public boolean containsLiteral(Resource arg0, Property arg1, char arg2) {
		return this.model.containsLiteral(arg0, arg1, arg2);
	}

	@Override
	public boolean containsLiteral(Resource arg0, Property arg1, float arg2) {
		return this.model.containsLiteral(arg0, arg1, arg2);
	}

	@Override
	public boolean containsLiteral(Resource arg0, Property arg1, double arg2) {
		return this.model.containsLiteral(arg0, arg1, arg2);
	}

	@Override
	public boolean containsLiteral(Resource arg0, Property arg1, Object arg2) {
		return this.model.containsLiteral(arg0, arg1, arg2);
	}

	@Override
	public Alt createAlt() {
		return this.model.createAlt();
	}

	@Override
	public Alt createAlt(String arg0) {
		return this.model.createAlt(arg0);
	}

	@Override
	public Bag createBag() {
		return this.model.createBag();
	}

	@Override
	public Bag createBag(String arg0) {
		return this.model.createBag(arg0);
	}

	@Override
	public Literal createLiteral(String arg0) {
		return this.model.createLiteral(arg0);
	}

	@Override
	public com.hp.hpl.jena.rdf.model.Statement createLiteralStatement(
			Resource arg0, Property arg1, boolean arg2) {
		return this.model.createLiteralStatement(arg0, arg1, arg2);
	}

	@Override
	public com.hp.hpl.jena.rdf.model.Statement createLiteralStatement(
			Resource arg0, Property arg1, float arg2) {
		return this.model.createLiteralStatement(arg0, arg1, arg2);
	}

	@Override
	public com.hp.hpl.jena.rdf.model.Statement createLiteralStatement(
			Resource arg0, Property arg1, double arg2) {
		return this.model.createLiteralStatement(arg0, arg1, arg2);
	}

	@Override
	public com.hp.hpl.jena.rdf.model.Statement createLiteralStatement(
			Resource arg0, Property arg1, long arg2) {
		return this.model.createLiteralStatement(arg0, arg1, arg2);
	}

	@Override
	public com.hp.hpl.jena.rdf.model.Statement createLiteralStatement(
			Resource arg0, Property arg1, int arg2) {
		return this.model.createLiteralStatement(arg0, arg1, arg2);
	}

	@Override
	public com.hp.hpl.jena.rdf.model.Statement createLiteralStatement(
			Resource arg0, Property arg1, char arg2) {
		return this.model.createLiteralStatement(arg0, arg1, arg2);
	}

	@Override
	public com.hp.hpl.jena.rdf.model.Statement createLiteralStatement(
			Resource arg0, Property arg1, Object arg2) {
		return this.model.createLiteralStatement(arg0, arg1, arg2);
	}

	@Override
	public Property createProperty(String arg0) {
		return this.model.createProperty(arg0);
	}

	@Override
	public Resource createResource(Resource arg0) {
		return this.model.createResource(arg0);
	}

	@Override
	public Resource createResource(ResourceF arg0) {
		return this.model.createResource(arg0);
	}

	@Override
	public Resource createResource(String arg0, Resource arg1) {
		return this.model.createResource(arg0, arg1);
	}

	@Override
	public Resource createResource(String arg0, ResourceF arg1) {
		return this.model.createResource(arg0, arg1);
	}

	@Override
	public Seq createSeq() {
		return this.model.createSeq();
	}

	@Override
	public Seq createSeq(String arg0) {
		return this.model.createSeq(arg0);
	}

	@Override
	public com.hp.hpl.jena.rdf.model.Statement createStatement(Resource arg0,
			Property arg1, String arg2) {
		return this.model.createStatement(arg0, arg1, arg2);
	}

	@Override
	public com.hp.hpl.jena.rdf.model.Statement createStatement(Resource arg0,
			Property arg1, String arg2, String arg3) {
		return this.model.createStatement(arg0, arg1, arg2, arg3);
	}

	@Override
	public com.hp.hpl.jena.rdf.model.Statement createStatement(Resource arg0,
			Property arg1, String arg2, boolean arg3) {
		return this.model.createStatement(arg0, arg1, arg2, arg3);
	}

	@Override
	public com.hp.hpl.jena.rdf.model.Statement createStatement(Resource arg0,
			Property arg1, String arg2, String arg3, boolean arg4) {
		return this.model.createStatement(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public Literal createTypedLiteral(boolean arg0) {
		return this.model.createTypedLiteral(arg0);
	}

	@Override
	public Literal createTypedLiteral(int arg0) {
		return this.model.createTypedLiteral(arg0);
	}

	@Override
	public Literal createTypedLiteral(long arg0) {
		return this.model.createTypedLiteral(arg0);
	}

	@Override
	public Literal createTypedLiteral(Calendar arg0) {
		return this.model.createTypedLiteral(arg0);
	}

	@Override
	public Literal createTypedLiteral(char arg0) {
		return this.model.createTypedLiteral(arg0);
	}

	@Override
	public Literal createTypedLiteral(float arg0) {
		return this.model.createTypedLiteral(arg0);
	}

	@Override
	public Literal createTypedLiteral(double arg0) {
		return this.model.createTypedLiteral(arg0);
	}

	@Override
	public Literal createTypedLiteral(String arg0) {
		return this.model.createTypedLiteral(arg0);
	}

	@Override
	public Literal createTypedLiteral(String arg0, String arg1) {
		return this.model.createTypedLiteral(arg0, arg1);
	}

	@Override
	public Literal createTypedLiteral(Object arg0, String arg1) {
		return this.model.createTypedLiteral(arg0, arg1);
	}

	@Override
	public Alt getAlt(String arg0) {
		return this.model.getAlt(arg0);
	}

	@Override
	public Alt getAlt(Resource arg0) {
		return this.model.getAlt(arg0);
	}

	@Override
	public Bag getBag(String arg0) {
		return this.model.getBag(arg0);
	}

	@Override
	public Bag getBag(Resource arg0) {
		return this.model.getBag(arg0);
	}

	@Override
	public Property getProperty(String arg0) {
		return this.model.getProperty(arg0);
	}

	@Override
	public RDFNode getRDFNode(Node arg0) {
		return this.model.getRDFNode(arg0);
	}

	@Override
	public Resource getResource(String arg0, ResourceF arg1) {
		return this.model.getResource(arg0, arg1);
	}

	@Override
	public Seq getSeq(String arg0) {
		return this.model.getSeq(arg0);
	}

	@Override
	public Seq getSeq(Resource arg0) {
		return this.model.getSeq(arg0);
	}

	@Override
	public StmtIterator listLiteralStatements(Resource arg0, Property arg1,
			boolean arg2) {
		return this.model.listLiteralStatements(arg0, arg1, arg2);
	}

	@Override
	public StmtIterator listLiteralStatements(Resource arg0, Property arg1,
			char arg2) {
		return this.model.listLiteralStatements(arg0, arg1, arg2);
	}

	@Override
	public StmtIterator listLiteralStatements(Resource arg0, Property arg1,
			long arg2) {
		return this.model.listLiteralStatements(arg0, arg1, arg2);
	}

	@Override
	public StmtIterator listLiteralStatements(Resource arg0, Property arg1,
			float arg2) {
		return this.model.listLiteralStatements(arg0, arg1, arg2);
	}

	@Override
	public StmtIterator listLiteralStatements(Resource arg0, Property arg1,
			double arg2) {
		return this.model.listLiteralStatements(arg0, arg1, arg2);
	}

	@Override
	public ResIterator listResourcesWithProperty(Property arg0, boolean arg1) {
		return this.model.listResourcesWithProperty(arg0, arg1);
	}

	@Override
	public ResIterator listResourcesWithProperty(Property arg0, long arg1) {
		return this.model.listResourcesWithProperty(arg0, arg1);
	}

	@Override
	public ResIterator listResourcesWithProperty(Property arg0, char arg1) {
		return this.model.listResourcesWithProperty(arg0, arg1);
	}

	@Override
	public ResIterator listResourcesWithProperty(Property arg0, float arg1) {
		return this.model.listResourcesWithProperty(arg0, arg1);
	}

	@Override
	public ResIterator listResourcesWithProperty(Property arg0, double arg1) {
		return this.model.listResourcesWithProperty(arg0, arg1);
	}

	@Override
	public ResIterator listResourcesWithProperty(Property arg0, Object arg1) {
		return this.model.listResourcesWithProperty(arg0, arg1);
	}

	@Override
	public StmtIterator listStatements(Resource arg0, Property arg1, String arg2) {
		return this.model.listStatements(arg0, arg1, arg2);
	}

	@Override
	public StmtIterator listStatements(Resource arg0, Property arg1,
			String arg2, String arg3) {
		return this.model.listStatements(arg0, arg1, arg2, arg3);
	}

	@Override
	public ResIterator listSubjectsWithProperty(Property arg0, String arg1) {
		return this.model.listSubjectsWithProperty(arg0, arg1);
	}

	@Override
	public ResIterator listSubjectsWithProperty(Property arg0, String arg1,
			String arg2) {
		return this.model.listSubjectsWithProperty(arg0, arg1, arg2);
	}

	@Override
	public Model remove(StmtIterator arg0) {
		return this.model.remove(arg0);
	}

	@Override
	public Model remove(Model arg0) {
		return this.model.remove(arg0);
	}

	@Override
	public Model remove(Model arg0, boolean arg1) {
		return this.model.remove(arg0, arg1);
	}

	@Override
	public Model remove(Resource arg0, Property arg1, RDFNode arg2) {
		return this.model.remove(arg0, arg1, arg2);
	}

	@Override
	public RDFNode asRDFNode(Node arg0) {
		return this.model.asRDFNode(arg0);
	}

	@Override
	public com.hp.hpl.jena.rdf.model.Statement asStatement(Triple arg0) {
		return this.model.asStatement(arg0);
	}

	@Override
	public Graph getGraph() {
		return this.model.getGraph();
	}

	@Override
	public QueryHandler queryHandler() {
		return this.model.queryHandler();
	}

	@Override
	public RDFReader getReader() {
		return this.model.getReader();
	}

	@Override
	public RDFReader getReader(String arg0) {
		return this.model.getReader(arg0);
	}

	@Override
	public String setReaderClassName(String arg0, String arg1) {
		return this.model.setReaderClassName(arg0, arg1);
	}

	@Override
	public RDFWriter getWriter() {
		return this.model.getWriter();
	}

	@Override
	public RDFWriter getWriter(String arg0) {
		return this.model.getWriter(arg0);
	}

	@Override
	public String setWriterClassName(String arg0, String arg1) {
		return this.model.setWriterClassName(arg0, arg1);
	}

	@Override
	public String expandPrefix(String arg0) {
		return this.model.expandPrefix(arg0);
	}

	@Override
	public Map<String, String> getNsPrefixMap() {
		return this.model.getNsPrefixMap();
	}

	@Override
	public String getNsPrefixURI(String arg0) {
		return this.model.getNsPrefixURI(arg0);
	}

	@Override
	public String getNsURIPrefix(String arg0) {
		return this.model.getNsPrefixURI(arg0);
	}

	@Override
	public PrefixMapping lock() {
		return this.model.lock();
	}

	@Override
	public String qnameFor(String arg0) {
		return this.model.qnameFor(arg0);
	}

	@Override
	public PrefixMapping removeNsPrefix(String arg0) {
		return this.model.removeNsPrefix(arg0);
	}

	@Override
	public boolean samePrefixMappingAs(PrefixMapping arg0) {
		return this.model.samePrefixMappingAs(arg0);
	}

	@Override
	public PrefixMapping setNsPrefix(String arg0, String arg1) {
		return this.model.setNsPrefix(arg0, arg1);
	}

	@Override
	public PrefixMapping setNsPrefixes(PrefixMapping arg0) {
		return this.model.setNsPrefixes(arg0);
	}

	@Override
	public PrefixMapping setNsPrefixes(Map<String, String> arg0) {
		return this.model.setNsPrefixes(arg0);
	}

	@Override
	public String shortForm(String arg0) {
		return this.model.shortForm(arg0);
	}

	@Override
	public PrefixMapping withDefaultMappings(PrefixMapping arg0) {
		return this.model.withDefaultMappings(arg0);
	}

	@Override
	public void enterCriticalSection(boolean arg0) {
		this.model.enterCriticalSection(arg0);
	}

	@Override
	public void leaveCriticalSection() {
		this.model.leaveCriticalSection();
	}
	
	public Resource wrapAsResource(Node n) {
		return this.model.wrapAsResource(n);
	}

}

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
import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.graph.query.QueryHandler;
import com.hp.hpl.jena.rdf.model.Alt;
import com.hp.hpl.jena.rdf.model.AnonId;
import com.hp.hpl.jena.rdf.model.Bag;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelChangedListener;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.NsIterator;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFList;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.RDFReader;
import com.hp.hpl.jena.rdf.model.RDFWriter;
import com.hp.hpl.jena.rdf.model.RSIterator;
import com.hp.hpl.jena.rdf.model.ReifiedStatement;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceF;
import com.hp.hpl.jena.rdf.model.Selector;
import com.hp.hpl.jena.rdf.model.Seq;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.shared.Command;
import com.hp.hpl.jena.shared.Lock;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.shared.ReificationStyle;

public class SPARQLConstructResultSet implements ResultSet, Model {

	private Model model;
	private Vector<Statement> internalResultSet; 
	
	public SPARQLConstructResultSet (Model model) {
		this.model = model;
		this.internalResultSet = new Vector<Statement>();
		StmtIterator stmtIterator = this.model.listStatements();
		while (stmtIterator.hasNext()){
			this.internalResultSet.add((Statement)stmtIterator.nextStatement());
		}
	}
	
	@Override
	public boolean absolute(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void afterLast() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeFirst() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void cancelRowUpdates() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearWarnings() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteRow() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public int findColumn(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean first() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Array getArray(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Array getArray(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getAsciiStream(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getAsciiStream(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getBigDecimal(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getBigDecimal(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getBigDecimal(int arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getBigDecimal(String arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getBinaryStream(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getBinaryStream(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Blob getBlob(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Blob getBlob(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getBoolean(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getBoolean(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public byte getByte(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte getByte(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte[] getBytes(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getBytes(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getCharacterStream(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getCharacterStream(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Clob getClob(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Clob getClob(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getConcurrency() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCursorName() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getDate(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getDate(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getDate(int arg0, Calendar arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getDate(String arg0, Calendar arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getDouble(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getDouble(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFetchDirection() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFetchSize() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getFloat(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getFloat(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHoldability() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getInt(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getInt(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getLong(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getLong(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getNCharacterStream(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getNCharacterStream(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NClob getNClob(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NClob getNClob(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNString(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNString(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObject(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObject(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObject(int arg0, Map<String, Class<?>> arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObject(String arg0, Map<String, Class<?>> arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ref getRef(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ref getRef(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRow() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public RowId getRowId(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RowId getRowId(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML getSQLXML(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML getSQLXML(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public short getShort(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public short getShort(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Statement getStatement() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getString(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getString(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time getTime(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time getTime(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time getTime(int arg0, Calendar arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time getTime(String arg0, Calendar arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getTimestamp(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getTimestamp(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getTimestamp(int arg0, Calendar arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getTimestamp(String arg0, Calendar arg1)
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
	public URL getURL(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URL getURL(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getUnicodeStream(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getUnicodeStream(String arg0) throws SQLException {
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
	public boolean isClosed() {
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
	public boolean relative(int arg0) throws SQLException {
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
	public void setFetchDirection(int arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFetchSize(int arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateArray(int arg0, Array arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateArray(String arg0, Array arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAsciiStream(int arg0, InputStream arg1)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAsciiStream(String arg0, InputStream arg1)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAsciiStream(int arg0, InputStream arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAsciiStream(String arg0, InputStream arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAsciiStream(int arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAsciiStream(String arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBigDecimal(int arg0, BigDecimal arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBigDecimal(String arg0, BigDecimal arg1)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBinaryStream(int arg0, InputStream arg1)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBinaryStream(String arg0, InputStream arg1)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBinaryStream(int arg0, InputStream arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBinaryStream(String arg0, InputStream arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBinaryStream(int arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBinaryStream(String arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBlob(int arg0, Blob arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBlob(String arg0, Blob arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBlob(int arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBlob(String arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBlob(int arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBlob(String arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBoolean(int arg0, boolean arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBoolean(String arg0, boolean arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateByte(int arg0, byte arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateByte(String arg0, byte arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBytes(int arg0, byte[] arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateBytes(String arg0, byte[] arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCharacterStream(int arg0, Reader arg1)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCharacterStream(String arg0, Reader arg1)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCharacterStream(int arg0, Reader arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCharacterStream(String arg0, Reader arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCharacterStream(int arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCharacterStream(String arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateClob(int arg0, Clob arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateClob(String arg0, Clob arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateClob(int arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateClob(String arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateClob(int arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateClob(String arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateDate(int arg0, Date arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateDate(String arg0, Date arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateDouble(int arg0, double arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateDouble(String arg0, double arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateFloat(int arg0, float arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateFloat(String arg0, float arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateInt(int arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateInt(String arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateLong(int arg0, long arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateLong(String arg0, long arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNCharacterStream(int arg0, Reader arg1)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNCharacterStream(String arg0, Reader arg1)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNCharacterStream(int arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNCharacterStream(String arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNClob(int arg0, NClob arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNClob(String arg0, NClob arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNClob(int arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNClob(String arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNClob(int arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNClob(String arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNString(int arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNString(String arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNull(int arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNull(String arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateObject(int arg0, Object arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateObject(String arg0, Object arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateObject(int arg0, Object arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateObject(String arg0, Object arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateRef(int arg0, Ref arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateRef(String arg0, Ref arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateRow() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateRowId(int arg0, RowId arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateRowId(String arg0, RowId arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateSQLXML(int arg0, SQLXML arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateSQLXML(String arg0, SQLXML arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateShort(int arg0, short arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateShort(String arg0, short arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateString(int arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateString(String arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateTime(int arg0, Time arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateTime(String arg0, Time arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateTimestamp(int arg0, Timestamp arg1) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateTimestamp(String arg0, Timestamp arg1)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean wasNull() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model abort() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model add(com.hp.hpl.jena.rdf.model.Statement arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model add(com.hp.hpl.jena.rdf.model.Statement[] arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model add(List<com.hp.hpl.jena.rdf.model.Statement> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model add(StmtIterator arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model add(Model arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model add(Model arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model begin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model commit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(com.hp.hpl.jena.rdf.model.Statement arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(Resource arg0, Property arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(Resource arg0, Property arg1, RDFNode arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(StmtIterator arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Model arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAny(StmtIterator arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAny(Model arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsResource(RDFNode arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RDFList createList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RDFList createList(Iterator<? extends RDFNode> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RDFList createList(RDFNode[] arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Literal createLiteral(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Literal createLiteral(String arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Property createProperty(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReifiedStatement createReifiedStatement(
			com.hp.hpl.jena.rdf.model.Statement arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReifiedStatement createReifiedStatement(String arg0,
			com.hp.hpl.jena.rdf.model.Statement arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource createResource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource createResource(AnonId arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource createResource(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.hp.hpl.jena.rdf.model.Statement createStatement(Resource arg0,
			Property arg1, RDFNode arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Literal createTypedLiteral(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Literal createTypedLiteral(String arg0, RDFDatatype arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Literal createTypedLiteral(Object arg0, RDFDatatype arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model difference(Model arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object executeInTransaction(Command arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource getAnyReifiedStatement(
			com.hp.hpl.jena.rdf.model.Statement arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Lock getLock() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Property getProperty(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.hp.hpl.jena.rdf.model.Statement getProperty(Resource arg0,
			Property arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReificationStyle getReificationStyle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.hp.hpl.jena.rdf.model.Statement getRequiredProperty(
			Resource arg0, Property arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource getResource(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean independent() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Model intersection(Model arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isIsomorphicWith(Model arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isReified(com.hp.hpl.jena.rdf.model.Statement arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public NsIterator listNameSpaces() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NodeIterator listObjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NodeIterator listObjectsOfProperty(Property arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NodeIterator listObjectsOfProperty(Resource arg0, Property arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RSIterator listReifiedStatements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RSIterator listReifiedStatements(
			com.hp.hpl.jena.rdf.model.Statement arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResIterator listResourcesWithProperty(Property arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResIterator listResourcesWithProperty(Property arg0, RDFNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StmtIterator listStatements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StmtIterator listStatements(Selector arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StmtIterator listStatements(Resource arg0, Property arg1,
			RDFNode arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResIterator listSubjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResIterator listSubjectsWithProperty(Property arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResIterator listSubjectsWithProperty(Property arg0, RDFNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model notifyEvent(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model query(Selector arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model read(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model read(InputStream arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model read(Reader arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model read(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model read(InputStream arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model read(Reader arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model read(String arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model register(ModelChangedListener arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model remove(com.hp.hpl.jena.rdf.model.Statement[] arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model remove(List<com.hp.hpl.jena.rdf.model.Statement> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model remove(com.hp.hpl.jena.rdf.model.Statement arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model removeAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model removeAll(Resource arg0, Property arg1, RDFNode arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeAllReifications(com.hp.hpl.jena.rdf.model.Statement arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeReification(ReifiedStatement arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public long size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean supportsSetOperations() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supportsTransactions() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Model union(Model arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model unregister(ModelChangedListener arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model write(Writer arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model write(OutputStream arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model write(Writer arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model write(OutputStream arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model write(Writer arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model write(OutputStream arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model add(Resource arg0, Property arg1, RDFNode arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model add(Resource arg0, Property arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model add(Resource arg0, Property arg1, String arg2, RDFDatatype arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model add(Resource arg0, Property arg1, String arg2, boolean arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model add(Resource arg0, Property arg1, String arg2, String arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model addLiteral(Resource arg0, Property arg1, boolean arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model addLiteral(Resource arg0, Property arg1, long arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model addLiteral(Resource arg0, Property arg1, int arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model addLiteral(Resource arg0, Property arg1, char arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model addLiteral(Resource arg0, Property arg1, float arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model addLiteral(Resource arg0, Property arg1, double arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model addLiteral(Resource arg0, Property arg1, Object arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model addLiteral(Resource arg0, Property arg1, Literal arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(Resource arg0, Property arg1, String arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(Resource arg0, Property arg1, String arg2,
			String arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsLiteral(Resource arg0, Property arg1, boolean arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsLiteral(Resource arg0, Property arg1, long arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsLiteral(Resource arg0, Property arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsLiteral(Resource arg0, Property arg1, char arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsLiteral(Resource arg0, Property arg1, float arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsLiteral(Resource arg0, Property arg1, double arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsLiteral(Resource arg0, Property arg1, Object arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Alt createAlt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Alt createAlt(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bag createBag() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bag createBag(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Literal createLiteral(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.hp.hpl.jena.rdf.model.Statement createLiteralStatement(
			Resource arg0, Property arg1, boolean arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.hp.hpl.jena.rdf.model.Statement createLiteralStatement(
			Resource arg0, Property arg1, float arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.hp.hpl.jena.rdf.model.Statement createLiteralStatement(
			Resource arg0, Property arg1, double arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.hp.hpl.jena.rdf.model.Statement createLiteralStatement(
			Resource arg0, Property arg1, long arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.hp.hpl.jena.rdf.model.Statement createLiteralStatement(
			Resource arg0, Property arg1, int arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.hp.hpl.jena.rdf.model.Statement createLiteralStatement(
			Resource arg0, Property arg1, char arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.hp.hpl.jena.rdf.model.Statement createLiteralStatement(
			Resource arg0, Property arg1, Object arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Property createProperty(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource createResource(Resource arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource createResource(ResourceF arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource createResource(String arg0, Resource arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource createResource(String arg0, ResourceF arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Seq createSeq() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Seq createSeq(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.hp.hpl.jena.rdf.model.Statement createStatement(Resource arg0,
			Property arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.hp.hpl.jena.rdf.model.Statement createStatement(Resource arg0,
			Property arg1, String arg2, String arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.hp.hpl.jena.rdf.model.Statement createStatement(Resource arg0,
			Property arg1, String arg2, boolean arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.hp.hpl.jena.rdf.model.Statement createStatement(Resource arg0,
			Property arg1, String arg2, String arg3, boolean arg4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Literal createTypedLiteral(boolean arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Literal createTypedLiteral(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Literal createTypedLiteral(long arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Literal createTypedLiteral(Calendar arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Literal createTypedLiteral(char arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Literal createTypedLiteral(float arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Literal createTypedLiteral(double arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Literal createTypedLiteral(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Literal createTypedLiteral(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Literal createTypedLiteral(Object arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Alt getAlt(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Alt getAlt(Resource arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bag getBag(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bag getBag(Resource arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Property getProperty(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RDFNode getRDFNode(Node arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource getResource(String arg0, ResourceF arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Seq getSeq(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Seq getSeq(Resource arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StmtIterator listLiteralStatements(Resource arg0, Property arg1,
			boolean arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StmtIterator listLiteralStatements(Resource arg0, Property arg1,
			char arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StmtIterator listLiteralStatements(Resource arg0, Property arg1,
			long arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StmtIterator listLiteralStatements(Resource arg0, Property arg1,
			float arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StmtIterator listLiteralStatements(Resource arg0, Property arg1,
			double arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResIterator listResourcesWithProperty(Property arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResIterator listResourcesWithProperty(Property arg0, long arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResIterator listResourcesWithProperty(Property arg0, char arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResIterator listResourcesWithProperty(Property arg0, float arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResIterator listResourcesWithProperty(Property arg0, double arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResIterator listResourcesWithProperty(Property arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StmtIterator listStatements(Resource arg0, Property arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StmtIterator listStatements(Resource arg0, Property arg1,
			String arg2, String arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResIterator listSubjectsWithProperty(Property arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResIterator listSubjectsWithProperty(Property arg0, String arg1,
			String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model remove(StmtIterator arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model remove(Model arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model remove(Model arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model remove(Resource arg0, Property arg1, RDFNode arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RDFNode asRDFNode(Node arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.hp.hpl.jena.rdf.model.Statement asStatement(Triple arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Graph getGraph() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QueryHandler queryHandler() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RDFReader getReader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RDFReader getReader(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setReaderClassName(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RDFWriter getWriter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RDFWriter getWriter(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setWriterClassName(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String expandPrefix(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getNsPrefixMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNsPrefixURI(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNsURIPrefix(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PrefixMapping lock() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String qnameFor(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PrefixMapping removeNsPrefix(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean samePrefixMappingAs(PrefixMapping arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PrefixMapping setNsPrefix(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PrefixMapping setNsPrefixes(PrefixMapping arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PrefixMapping setNsPrefixes(Map<String, String> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String shortForm(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PrefixMapping withDefaultMappings(PrefixMapping arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void enterCriticalSection(boolean arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void leaveCriticalSection() {
		// TODO Auto-generated method stub

	}

}

package org.lexicon.jdbc4sparql;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.LinkedList;


public class SPARQLConnection implements Connection {

	private String connectionURL;
	private String username;
	private String password;
	private LinkedList<String> defaultGraphs;
	private LinkedList<String> namedGraphs;
	private int holdability;
	private int transactionIsolation;
	private boolean readOnly;
	private boolean autoCommit;
	private boolean closed;
	
	public SPARQLConnection(String connectionURL)throws SQLException {
		
	}
	
	public SPARQLConnection() {
		
	}
	
	public void init() throws SQLException {
		this.holdability = ResultSet.CLOSE_CURSORS_AT_COMMIT;
        this.transactionIsolation = Connection.TRANSACTION_NONE;
        this.autoCommit = true;
        this.readOnly = false;
        this.closed = false;
        String[] parameters = this.connectionURL.split("?")[1].split("&");
        for (int x = 0; x < parameters.length; x++) {
        	if (parameters[x].split("=")[0] == "default-graph-uri") {
        		this.defaultGraphs.add(parameters[x].split("=")[1]);
        	}
        	if (parameters[x].split("=")[0] == "named-graph-uri") {
        		this.namedGraphs.add(parameters[x].split("=")[1]);
        	}
        	if (parameters[x].split("=")[0] == "username") {
        		this.username = parameters[x].split("=")[1];
        	}
        	if (parameters[x].split("=")[0] == "password") {
        		this.password = parameters[x].split("=")[1];
        	}
        }
	}
	
	
	public void clearWarnings() throws SQLException {
	}

	
	public void close() throws SQLException {
		this.closed = true;
	}

	
	public void commit() throws SQLException {
	}

	
	public Array createArrayOf(String arg0, Object[] arg1) throws SQLException {
		return null;
	}

	
	public Blob createBlob() throws SQLException {
		return null;
	}

	@Override
	public Clob createClob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NClob createNClob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML createSQLXML() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createStatement() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createStatement(int arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createStatement(int arg0, int arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Struct createStruct(String arg0, Object[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean getAutoCommit() throws SQLException {
		return this.autoCommit;
	}

	@Override
	public String getCatalog() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Properties getClientInfo() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getClientInfo(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getHoldability() throws SQLException {
		// TODO Auto-generated method stub
		return this.holdability;
	}

	@Override
	public DatabaseMetaData getMetaData() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTransactionIsolation() throws SQLException {
		// TODO Auto-generated method stub
		return this.transactionIsolation;
	}

	@Override
	public Map<String, Class<?>> getTypeMap() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		return this.closed;
	}

	@Override
	public boolean isReadOnly() throws SQLException {
		// TODO Auto-generated method stub
		return this.readOnly;
	}

	@Override
	public boolean isValid(int arg0) throws SQLException {
		if (this.closed) return false;
		else return true;
	}

	@Override
	public String nativeSQL(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CallableStatement prepareCall(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CallableStatement prepareCall(String arg0, int arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CallableStatement prepareCall(String arg0, int arg1, int arg2,
			int arg3) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PreparedStatement prepareStatement(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PreparedStatement prepareStatement(String arg0, int arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PreparedStatement prepareStatement(String arg0, int[] arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PreparedStatement prepareStatement(String arg0, String[] arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PreparedStatement prepareStatement(String arg0, int arg1, int arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PreparedStatement prepareStatement(String arg0, int arg1, int arg2,
			int arg3) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void releaseSavepoint(Savepoint arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void rollback() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void rollback(Savepoint arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAutoCommit(boolean arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCatalog(String arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setClientInfo(Properties arg0) throws SQLClientInfoException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setClientInfo(String arg0, String arg1)
			throws SQLClientInfoException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setHoldability(int arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setReadOnly(boolean arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public Savepoint setSavepoint() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Savepoint setSavepoint(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTransactionIsolation(int arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTypeMap(Map<String, Class<?>> arg0) throws SQLException {
		// TODO Auto-generated method stub

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
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public LinkedList<String> getDefaultGraphs(){
		return this.defaultGraphs;
	}
	
	public void setDefaultGraphs (LinkedList<String> defaultGraphs) {
		this.defaultGraphs = defaultGraphs;
	}
	
	public LinkedList<String> getNamedGraphs(){
		return this.namedGraphs;
	}
	
	public void setNamedGraphs (LinkedList<String> namedGraphs) {
		this.namedGraphs = namedGraphs;
	}
	
	public String getConnectionURL() {
		return this.connectionURL;
	}
	
	public void setConnectionURL (String connectionURL) {
		this.connectionURL = connectionURL;
	}

}

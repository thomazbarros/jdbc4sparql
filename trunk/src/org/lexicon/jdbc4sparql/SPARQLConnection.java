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
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.HashMap;
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
	private Map<String, Class<?>> typeMap;
	private String endPoint;
	private Properties connectionProperties;
	
	public SPARQLConnection(String connectionURL, Properties connectionProperties)throws SQLException {
		this.connectionURL = connectionURL;
		this.connectionProperties = connectionProperties;
        try {
            this.init();
        }
        catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
	}
	
	public SPARQLConnection() {
		
	}
	
	@SuppressWarnings("unchecked")
	public void init() throws SQLException {
		
		//construction
		this.holdability = ResultSet.CLOSE_CURSORS_AT_COMMIT;
        this.transactionIsolation = Connection.TRANSACTION_NONE;
        this.autoCommit = true;
        this.readOnly = false;
        this.closed = false;
        this.typeMap = (Map)new HashMap();
        this.defaultGraphs = new LinkedList<String>();
        this.namedGraphs = new LinkedList<String>();
        
        if (this.connectionProperties != null) {
	        this.username = this.connectionProperties.getProperty("username");
	        if (this.username == null) this.username = this.connectionProperties.getProperty("Username");
	        if (this.username == null) this.username = this.connectionProperties.getProperty("USERNAME");
	        
	        this.password = this.connectionProperties.getProperty("password");
	        if (this.password == null) this.password = this.connectionProperties.getProperty("Password");
	        if (this.password == null) this.password = this.connectionProperties.getProperty("PASSWORD");
	    
        }
        //resolve connection URL
        String sparqlURI = this.connectionURL.replaceFirst(SPARQLDriver.DRIVER_PREFIX, "");
        
        //Parse connection URL
        String[] tmp = sparqlURI.split("[?]");
        this.endPoint = tmp[0];
        if (tmp.length > 1) {
	        String[] parameters = tmp[1].split("&");
	        for (int x = 0; x < parameters.length; x++) {
	        	      	
	        	if (parameters[x].split("=")[0].equals("default-graph-uri")) {
	        		this.defaultGraphs.add(parameters[x].split("=")[1]);
	        	}
	        	if (parameters[x].split("=")[0].equals("named-graph-uri")) {
	        		this.namedGraphs.add(parameters[x].split("=")[1]);
	        	}
	        	if (parameters[x].split("=")[0].equals("username")) {
	        		this.username = parameters[x].split("=")[1];
	        	}
	        	if (parameters[x].split("=")[0].equals("password")) {
	        		this.password = parameters[x].split("=")[1];
	        	}
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

	
	public Clob createClob() throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public NClob createNClob() throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public SQLXML createSQLXML() throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	public Statement createStatement() throws SQLException {
		return new SPARQLStatement(this);
	}

	public Statement createStatement(int arg0, int arg1) throws SQLException {
		return this.createStatement();
	}

	public Statement createStatement(int arg0, int arg1, int arg2)
			throws SQLException {
		return this.createStatement();
	}

	
	public Struct createStruct(String arg0, Object[] arg1) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	public boolean getAutoCommit() throws SQLException {
		return this.autoCommit;
	}

	public String getCatalog() throws SQLException {
		return null;
	}

	public Properties getClientInfo() throws SQLException {
		return null;
	}

	public String getClientInfo(String arg0) throws SQLException {
		return null;
	}

	
	public int getHoldability() throws SQLException {
		return this.holdability;
	}

	public DatabaseMetaData getMetaData() throws SQLException {
		return null;
	}

	
	public int getTransactionIsolation() throws SQLException {
		return this.transactionIsolation;
	}

	
	public Map<String, Class<?>> getTypeMap() throws SQLException {
		return this.typeMap;
	}

	
	public SQLWarning getWarnings() throws SQLException {
		
		return null;
	}

	public boolean isClosed() throws SQLException {
		
		return this.closed;
	}

	
	public boolean isReadOnly() throws SQLException {
		
		return this.readOnly;
	}

	public boolean isValid(int arg0) throws SQLException {
		if (this.closed) return false;
		else return true;
	}

	
	public String nativeSQL(String arg0) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public CallableStatement prepareCall(String arg0) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public CallableStatement prepareCall(String arg0, int arg1, int arg2)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public CallableStatement prepareCall(String arg0, int arg1, int arg2,
			int arg3) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public PreparedStatement prepareStatement(String arg0) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public PreparedStatement prepareStatement(String arg0, int arg1)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public PreparedStatement prepareStatement(String arg0, int[] arg1)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public PreparedStatement prepareStatement(String arg0, String[] arg1)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public PreparedStatement prepareStatement(String arg0, int arg1, int arg2)
			throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public PreparedStatement prepareStatement(String arg0, int arg1, int arg2,
			int arg3) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void releaseSavepoint(Savepoint arg0) throws SQLException {
		
	}

	
	public void rollback() throws SQLException {
		
	}

	
	public void rollback(Savepoint arg0) throws SQLException {
		
	}

	
	public void setAutoCommit(boolean autoCommit) throws SQLException {
		this.autoCommit = autoCommit;
	}

	
	public void setCatalog(String arg0) throws SQLException {
		
	}

	public void setClientInfo(Properties arg0) throws SQLClientInfoException {
	}

	public void setClientInfo(String arg0, String arg1)
			throws SQLClientInfoException {
	}

	
	public void setHoldability(int holdability) throws SQLException {
		this.holdability = holdability;
	}

	
	public void setReadOnly(boolean arg0) throws SQLException {
		
	}

	public Savepoint setSavepoint() throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	public Savepoint setSavepoint(String arg0) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	public void setTransactionIsolation(int transactionIsolation) throws SQLException {
		this.transactionIsolation = transactionIsolation;
	}

	public void setTypeMap(Map<String, Class<?>> typeMap) throws SQLException {
		this.typeMap = typeMap;
	}

	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		return false;
	}

	public <T> T unwrap(Class<T> arg0) throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
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
	
	public String getEndPoint() {
		return this.endPoint;
	}
	
	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

}

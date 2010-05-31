package org.lexicon.jdbc4sparql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLWarning;
import java.sql.Statement;
import com.hp.hpl.jena.query.*;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Vector;

public class SPARQLStatement implements Statement {

	private SPARQLConnection conn;
	private ResultSet resultSet;
	private Vector<String> batches;
	private boolean closed;
	private int timeout;
	
	public SPARQLStatement(SPARQLConnection conn) {
		this.conn = conn;
		this.batches = new Vector<String>();
		this.closed = false;
		this.timeout = 300;
	}
	
	
	public void addBatch(String sparql) throws SQLException {
		this.batches.add(sparql);
	}

	
	public void cancel() throws SQLException {
		throw new SQLFeatureNotSupportedException("Feature not supported");
	}

	
	public void clearBatch() throws SQLException {
		this.batches.clear();
	}

	
	public void clearWarnings() throws SQLException {
		
	}

	
	public void close() throws SQLException {
		this.closed = true;
		this.resultSet.close();
	}

	@Override
	public boolean execute(String sparql) throws SQLException {
		try {
			Query query = QueryFactory.create(sparql);
			if (query.isSelectType()){
				QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.conn.getEndPoint(), query, this.conn.getDefaultGraphs(), this.conn.getNamedGraphs());
				this.resultSet = new SPARQLSelectResultSet(queryExecution.execSelect(), this, query);
				return true;
			}
			if (query.isConstructType()){
				
				QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.conn.getEndPoint(), query, this.conn.getDefaultGraphs(), this.conn.getNamedGraphs());
				this.resultSet = new SPARQLConstructResultSet(queryExecution.execConstruct(), this, query);
				return true;
			}
			if (query.isDescribeType()){
				QueryExecution queryExecution = QueryExecutionFactory.sparqlService(this.conn.getEndPoint(), query, this.conn.getDefaultGraphs(), this.conn.getNamedGraphs());
				this.resultSet = new SPARQLConstructResultSet(queryExecution.execDescribe(), this, query);
				return true;
			}
			if (query.isAskType()){
				//TODO
				return true;
			}
			if (query.isUnknownType()){
				//TODO
				return true;
			}
			return false;
		}
		catch (Exception e) {
			throw new SQLException (e.getMessage());
		}
	}

	public boolean execute(String arg0, int arg1) throws SQLException {
		return this.execute(arg0);
	}

	public boolean execute(String arg0, int[] arg1) throws SQLException {
		return this.execute(arg0);
	}

	public boolean execute(String arg0, String[] arg1) throws SQLException {
		return this.execute(arg0);
	}

	@Override
	public int[] executeBatch() throws SQLException {
		for (int i=0; i<this.batches.size(); i++){
			this.execute(this.batches.get(i));
		}
		return new int[0];
	}

	public ResultSet executeQuery(String arg0) throws SQLException {
		this.execute(arg0);
		return this.resultSet;
	}

	public int executeUpdate(String arg0) throws SQLException {
		this.execute(arg0);
		return 0;
	}

	public int executeUpdate(String arg0, int arg1) throws SQLException {
		this.execute(arg0);
		return 0;
	}

	public int executeUpdate(String arg0, int[] arg1) throws SQLException {
		this.execute(arg0);
		return 0;
	}

	public int executeUpdate(String arg0, String[] arg1) throws SQLException {
		this.execute(arg0);
		return 0;
	}

	public Connection getConnection() throws SQLException {
		return this.conn;
	}

	public int getFetchDirection() throws SQLException {
		return ResultSet.FETCH_FORWARD;
	}

	public int getFetchSize() throws SQLException {
		return 0;
	}

	public ResultSet getGeneratedKeys() throws SQLException {
		return null;
	}

	public int getMaxFieldSize() throws SQLException {
		return 1000000;
	}

	public int getMaxRows() throws SQLException {
		return 1000000;
	}

	public boolean getMoreResults() throws SQLException {
		return false;
	}

	public boolean getMoreResults(int arg0) throws SQLException {
		return false;
	}

	public int getQueryTimeout() throws SQLException {
		return this.timeout;
	}

	public ResultSet getResultSet() throws SQLException {
		return this.resultSet;
	}

	public int getResultSetConcurrency() throws SQLException {
		return java.sql.ResultSet.CONCUR_READ_ONLY;
	}

	public int getResultSetHoldability() throws SQLException {
		return ResultSet.CLOSE_CURSORS_AT_COMMIT;
	}

	public int getResultSetType() throws SQLException {
		return ResultSet.TYPE_SCROLL_INSENSITIVE; 
	}

	public int getUpdateCount() throws SQLException {
		return -1;
	}

	public SQLWarning getWarnings() throws SQLException {
		return null;
	}

	public boolean isClosed() throws SQLException {
		return this.isClosed();
	}

	public boolean isPoolable() throws SQLException {
		return false;
	}

	public void setCursorName(String arg0) throws SQLException {
		
	}

	public void setEscapeProcessing(boolean arg0) throws SQLException {
		
	}

	public void setFetchDirection(int arg0) throws SQLException {
		
	}

	public void setFetchSize(int arg0) throws SQLException {
		
	}

	public void setMaxFieldSize(int arg0) throws SQLException {
		
	}

	public void setMaxRows(int arg0) throws SQLException {
		
	}

	public void setPoolable(boolean arg0) throws SQLException {
		
	}

	public void setQueryTimeout(int arg0) throws SQLException {
		this.timeout = arg0;
	}

	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		return false;
	}

	public <T> T unwrap(Class<T> arg0) throws SQLException {
		return null;
	}

}

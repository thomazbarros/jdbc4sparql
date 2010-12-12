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
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import com.hp.hpl.jena.rdf.model.*;
import java.io.DataOutputStream;


public class SPARQLStatement implements Statement {

	private SPARQLConnection conn;
	private ResultSet resultSet;
	private Vector<String> batches;
	private boolean closed;
	private int timeout;
	public static final String acceptHeader = "application/sparql-results+xml";
	public static final String userAgentHeader = "jdbc4sparql";
	
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
	
	public boolean execute(String sparql) throws SQLException {
		try {
			//create SPARQL service
			Query query = QueryFactory.create(sparql);
			QueryEngineHTTP qeHTTP = QueryExecutionFactory.createServiceRequest(this.conn.getEndPoint(), query); 
			
			//set parameters
			if (this.conn.getUsername() != null && this.conn.getPassword() != null) {
				qeHTTP.setBasicAuthentication(this.conn.getUsername(), this.conn.getPassword().toCharArray());
			}
			
			if (this.conn.getDefaultGraphs().size() > 0) {
				Iterator i = this.conn.getDefaultGraphs().iterator();
				while (i.hasNext()){
					qeHTTP.addDefaultGraph((String)i.next());
				}
			}
		
			if (this.conn.getNamedGraphs().size() > 0) {
				Iterator i = this.conn.getNamedGraphs().iterator();
				while (i.hasNext()){
					qeHTTP.addNamedGraph((String)i.next());
				}
			}
			
			if (query.isSelectType()){
				this.resultSet = new SPARQLSelectResultSet(qeHTTP.execSelect(), this, query);
				return true;
			}
			if (query.isConstructType()){
				this.resultSet = new SPARQLConstructResultSet(qeHTTP.execConstruct(), this, query);
				
				return true;
			}
			if (query.isDescribeType()){
				this.resultSet = new SPARQLConstructResultSet(qeHTTP.execDescribe(), this, query);
				return true;
			}
			if (query.isAskType()){
				this.resultSet = new SPARQLAskResultSet(qeHTTP.execAsk(), this, query);
				return true;
			}
			
			//Counted as SPARQL Update
			if (query.isUnknownType()){
				this.executeUpdate(sparql);
				return true;
			}
			
			return false;
		}
		catch (QueryParseException e) {
			this.executeUpdate(sparql);
			return true;
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

	public int executeUpdate(String sparql) throws SQLException {
		URL servicePoint = null;
		try {
			servicePoint = new URL(this.conn.getEndPoint());
			HttpURLConnection http = null;
			http = (HttpURLConnection) servicePoint.openConnection();
			if (this.conn.getUsername() != null && this.conn.getPassword() != null) {
				http.setRequestProperty("Authorization", "Basic " + base64Encode((this.conn.getUsername() + ":" + this.conn.getPassword()).getBytes()));
			}
			http.setRequestProperty("Accept", SPARQLStatement.acceptHeader);
			http.setRequestProperty("User-agent", SPARQLStatement.userAgentHeader);
			http.setRequestMethod("POST");
			http.setRequestProperty("Content-Length", "" + Integer.toString(sparql.getBytes().length));
			http.setDoInput(true);
			http.setDoOutput(true);
			
			//Send request
		    DataOutputStream wr = new DataOutputStream (http.getOutputStream ());
		    wr.writeBytes (sparql);
		    wr.flush ();
		    wr.close ();
			
			//http.connect();
			int code = http.getResponseCode() ;
			if (code >= 400) {
				String message = URLDecoder.decode(http.getResponseMessage(), "UTF-8");
				throw new SQLException (code+":"+message);
			}	
		}
		catch (MalformedURLException e) {
			String msg = "The server URL was malformed: " + servicePoint;
			throw new SQLException (msg);
		} 
		catch (IOException e) {
			String msg = "Could not make http request due to " + e.toString();
			throw new SQLException (msg);
		}
		catch (Exception e) {
			throw new SQLException (e.toString());
		}
		return 0;
	}

	public int executeUpdate(String arg0, int arg1) throws SQLException {
		return this.executeUpdate(arg0);
	}

	public int executeUpdate(String arg0, int[] arg1) throws SQLException {
		return this.executeUpdate(arg0);
	}

	public int executeUpdate(String arg0, String[] arg1) throws SQLException {
		return this.executeUpdate(arg0);
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
	
	public final static byte[] base64Encode(byte[] byteData) {
		if (byteData == null) {
			return null;
		}
		int iSrcIdx; // index into source (byteData)
		int iDestIdx; // index into destination (byteDest)
		byte byteDest[] = new byte[((byteData.length+2)/3)*4];
			
		for (iSrcIdx=0, iDestIdx=0; iSrcIdx < byteData.length-2; iSrcIdx += 3) {
			byteDest[iDestIdx++] = (byte) ((byteData[iSrcIdx] >>> 2) & 077);
			byteDest[iDestIdx++] = (byte) ((byteData[iSrcIdx+1] >>> 4) & 017 | (byteData[iSrcIdx] << 4) & 077);
			byteDest[iDestIdx++] = (byte) ((byteData[iSrcIdx+2] >>> 6) & 003 | (byteData[iSrcIdx+1] << 2) & 077);
			byteDest[iDestIdx++] = (byte) (byteData[iSrcIdx+2] & 077);
		}
		
		if (iSrcIdx < byteData.length) {
			byteDest[iDestIdx++] = (byte) ((byteData[iSrcIdx] >>> 2) & 077);
		if (iSrcIdx < byteData.length-1) {
			byteDest[iDestIdx++] = (byte) ((byteData[iSrcIdx+1] >>> 4) & 017 | (byteData[iSrcIdx] << 4) & 077);
			byteDest[iDestIdx++] = (byte) ((byteData[iSrcIdx+1] << 2) & 077);
		}
		else
			byteDest[iDestIdx++] = (byte) ((byteData[iSrcIdx] << 4) & 077);
		}
		
		for (iSrcIdx = 0; iSrcIdx < iDestIdx; iSrcIdx++) {
			if (byteDest[iSrcIdx] < 26) byteDest[iSrcIdx] = (byte)(byteDest[iSrcIdx] + 'A');
			else if (byteDest[iSrcIdx] < 52) byteDest[iSrcIdx] = (byte)(byteDest[iSrcIdx] + 'a'-26);
			else if (byteDest[iSrcIdx] < 62) byteDest[iSrcIdx] = (byte)(byteDest[iSrcIdx] + '0'-52);
			else if (byteDest[iSrcIdx] < 63) byteDest[iSrcIdx] = '+';
			else byteDest[iSrcIdx] = '/';
		}
		
		for ( ; iSrcIdx < byteDest.length; iSrcIdx++) {
			byteDest[iSrcIdx] = '=';
		}
		
		return byteDest;
	}
		

}

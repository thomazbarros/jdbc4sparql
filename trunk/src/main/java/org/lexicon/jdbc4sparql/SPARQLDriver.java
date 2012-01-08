package org.lexicon.jdbc4sparql;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Properties;

public class SPARQLDriver implements Driver {

	public static final String DRIVER_PREFIX = "jdbc:sparql:";
	static final int MAJOR_VERSION = 0;
	static final int MINOR_VERSION = 1;
	
	static {
		try {
			DriverManager.registerDriver(new SPARQLDriver());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	} 
	
	public SPARQLDriver() {
		
	}
	
	public boolean acceptsURL(String url) throws SQLException {
		try {
            SPARQLConnection conn = new SPARQLConnection();
            conn.setConnectionURL(url);
            conn.init();
        }
        catch (Exception e) {
            return false;
        }
        return true;
	}

	public Connection connect(String url, Properties arg1) throws SQLException {
		try {
			SPARQLConnection conn = new SPARQLConnection(url, arg1);
            return conn;
        }
        catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
	}

	public int getMajorVersion() {
		return SPARQLDriver.MAJOR_VERSION;
	}

	public int getMinorVersion() {
		return SPARQLDriver.MINOR_VERSION;
	}

	public DriverPropertyInfo[] getPropertyInfo(String arg0, Properties arg1) throws SQLException {
		return null;
	}

	public boolean jdbcCompliant() {
		return true;
	}

}

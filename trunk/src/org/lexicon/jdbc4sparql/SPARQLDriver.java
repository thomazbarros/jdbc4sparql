package org.lexicon.jdbc4sparql;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Properties;

public class SPARQLDriver implements Driver {

	public SPARQLDriver() {
		try {
			DriverManager.registerDriver(this);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
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

	@Override
	public Connection connect(String url, Properties arg1) throws SQLException {
		try {
			SPARQLConnection conn = new SPARQLConnection(url);
            return conn;
        }
        catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
	}

	@Override
	public int getMajorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMinorVersion() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public DriverPropertyInfo[] getPropertyInfo(String arg0, Properties arg1) throws SQLException {
		return null;
	}

	@Override
	public boolean jdbcCompliant() {
		// TODO Auto-generated method stub
		return false;
	}

}

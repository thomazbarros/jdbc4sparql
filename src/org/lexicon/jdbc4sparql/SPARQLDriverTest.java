package org.lexicon.jdbc4sparql;
import static org.junit.Assert.*;

import java.sql.DriverManager;
import java.util.Enumeration;

import org.junit.Test;

public class SPARQLDriverTest {

	@Test
	public void testDriverInitiation() throws Exception {
		String driverName = "org.lexicon.jdbc4sparql.SPARQLDriver"; 
		SPARQLDriver sd = new SPARQLDriver();
		try {
			assert sd.acceptsURL("jdbc:sparql:http://172.16.0.72/virtuoso/sparql");
		}
		catch (Exception e) {
			throw new Exception (e.getMessage());
		}
		
	}
	
	@Test
	public void testDriverRegistration() {
		String driverName = "org.lexicon.jdbc4sparql.SPARQLDriver"; 
		try {
			Class.forName(driverName);
		}
		catch (Exception e) {
			
		}
		Enumeration<java.sql.Driver> en = DriverManager.getDrivers();
		boolean init = false;
		while (en.hasMoreElements()) {
			java.sql.Driver d = en.nextElement();
			System.out.println(d.getClass().getName());
			if (d.getClass().getName().equals("org.lexicon.jdbc4sparql.SPARQLDriver")) {
				init = true;
			}
		}
		assertTrue(init);
	}
	
}

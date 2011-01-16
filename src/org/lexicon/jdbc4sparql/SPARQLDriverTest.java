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
		Enumeration<java.sql.Driver> en = DriverManager.getDrivers();
		boolean init = false;
		while (en.hasMoreElements()) {
			java.sql.Driver d = en.nextElement();
			if (d.getClass().getName().equals("org.lexicon.jdbc4sparql.SPARQLDriver")) {
				init = true;
			}
		}
		assert (init);
		try {
			assert sd.acceptsURL("jdbc:sparql:http://dbpedia.org/sparql");
		}
		catch (Exception e) {
			throw new Exception (e.getMessage());
		}
	}
	
}

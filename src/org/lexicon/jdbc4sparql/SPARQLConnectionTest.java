package org.lexicon.jdbc4sparql;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class SPARQLConnectionTest {

	String driverName;
	SPARQLDriver sd;
	SPARQLConnection con1;
	
	@Before
	public void setUp() throws Exception {
		this.driverName = "org.lexicon.jdbc4sparql.SPARQLDriver"; 
		this.sd = new SPARQLDriver();
		this.con1 = (SPARQLConnection)sd.connect("jdbc:sparql:http://localhost:8890/sparql?username=dba&password=dynamite", null);
	}
	
	@Test
	public void testConnectionProperties() {
		assert (con1.getUsername().equals("dba") && con1.getPassword().equals("dynamite"));
		assert (con1.getEndPoint().equals("http://localhost:8890/sparql"));
	}
	
}

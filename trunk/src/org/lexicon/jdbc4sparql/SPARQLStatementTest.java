package org.lexicon.jdbc4sparql;
import static org.junit.Assert.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.datatypes.xsd.XSDDateTime;
import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.graph.query.QueryHandler;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.shared.Command;
import com.hp.hpl.jena.shared.Lock;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.shared.ReificationStyle;
import com.hp.hpl.jena.query.*;

import java.util.*;
import org.junit.Before;
import org.junit.Test;

public class SPARQLStatementTest {

	String driverName;
	SPARQLDriver sd;
	SPARQLConnection con1;
	
	@Before
	public void setUp() throws Exception {
		this.driverName = "org.lexicon.jdbc4sparql.SPARQLDriver"; 
		Class.forName(driverName);
		Enumeration<java.sql.Driver> en = DriverManager.getDrivers();
		
		while (en.hasMoreElements()) {
			java.sql.Driver d = en.nextElement();
			System.out.println(d.getClass().getName());
			
		}
		this.con1 = (SPARQLConnection)DriverManager.getConnection("jdbc:sparql:http://172.16.0.72/virtuoso/sparql?username=dba&password=w1llgr33nly", null);
	}
	
	@Test
	public void testSelectStatement() throws Exception {
		try {
			Statement st = this.con1.createStatement();
			ResultSet rs = st.executeQuery("SELECT ?s WHERE {?s ?p ?o} LIMIT 1");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testConstructStatement() {
		try {
			Statement st = this.con1.createStatement();
			Model rs = (Model)st.executeQuery("CONSTRUCT { ?s ?p ?o } WHERE {GRAPH ?g { ?s ?p ?o }. FILTER (?s = <http://www.openlinksw.com/schemas/virtrdf#DefaultQuadMap-G>)}");
			assertTrue(rs.size() >= 0);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testInsertStatement() {
		try {
			Statement st = this.con1.createStatement();
			st.executeUpdate("PREFIX dc: <http://purl.org/dc/elements/1.1/> INSERT DATA { <http://example/book3> dc:title \"A new book\" ; dc:creator  \"A.N.Other\" .}");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testDeleteStatement() {
		
	}
	
	@Test
	public void testAskStatement() {
		
	}
	

}

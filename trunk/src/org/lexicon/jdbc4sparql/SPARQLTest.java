package org.lexicon.jdbc4sparql;
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


public class SPARQLTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try { 
			
			String driverName = "org.lexicon.jdbc4sparql.SPARQLDriver"; 
			//Class.forName(driverName).newInstance(); 
			//java.sql.Connection con = DriverManager.getConnection("http://www123.com/test");
			
			
			SPARQLDriver sd = new SPARQLDriver();
			SPARQLConnection con1 = (SPARQLConnection)sd.connect("jdbc:sparql:http://dbpedia.org/sparql?username=john&password=smith", null);
			System.out.println(con1.getUsername() + " " + con1.getPassword());
			/*
			SPARQLStatement st = (SPARQLStatement)con.createStatement();
			ResultSet rs = st.executeQuery("SELECT ?s ?p ?o WHERE {?s ?p ?o} LIMIT 1");
			ResultSetMetaData rsm = rs.getMetaData();
			System.out.println(rsm.getColumnCount());
			while (rs.next()) {
				//Resource rdfn = (Resource)rs.getObject(1);
				//Resource rdfn = (Resource)rs.getObject(1);
				//Resource rdfn = (Resource)rs.getObject(1);
				System.out.println(rs.getObject(1).toString());
				System.out.println(rs.getObject(2).toString());
				System.out.println(rs.getObject(3).toString());
			}*/
			/*DriverManager.registerDriver(sd);
			String driverName = "org.lexicon.jdbc4sparql.SPARQLDriver"; 
			Class.forName(driverName).newInstance(); 
			java.sql.Connection con = DriverManager.getConnection("http://dbpedia.org/sparql");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT ?s WHERE {?s ?p ?o} LIMIT 1");
			while (rs.next()) {
				//System.out.println("1");
			}*/
			/*Enumeration<java.sql.Driver> en = DriverManager.getDrivers();
			
			while (en.hasMoreElements()) {
				java.sql.Driver d = en.nextElement();
				System.out.println(d.getClass().getName());
			}*/
			//java.sql.Connection con = sd.connect("http://dbpedia.org/sparql", null);
			Class.forName(driverName);
			Enumeration<java.sql.Driver> en = DriverManager.getDrivers();
			
			while (en.hasMoreElements()) {
				java.sql.Driver d = en.nextElement();
				System.out.println(d.getClass().getName());
			}
			java.sql.Connection con = DriverManager.getConnection("jdbc:sparql:http://dbpedia.org/sparql");
			SPARQLStatement st = (SPARQLStatement)con.createStatement();
			ResultSet rs = st.executeQuery("CONSTRUCT { ?s ?p ?o } WHERE {GRAPH ?g { ?s ?p ?o }. FILTER (?s = <http://www.openlinksw.com/schemas/virtrdf#DefaultQuadMap-G>)}");
			//ResultSet rs2 = st.executeQuery("PREFIX dc: <http://purl.org/dc/elements/1.1/> INSERT DATA { <http://example/book3> dc:title \"A new book\" ; dc:creator  \"A.N.Other\" .}");
			//ResultSetMetaData rsm = rs.getMetaData();
			//System.out.println(rsm.getColumnCount());
			//while (rs2.next()) {
			//	System.out.println(rs2.getObject(1).toString());
			//	System.out.println(rs2.getObject(2).toString());
			//	System.out.println(rs2.getObject(3).toString());
			//}
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			Model m = (Model)rs;
			m.write(b);
			System.out.println(b.toString());
			/*com.hp.hpl.jena.query.ResultSet testRS = ResultSetFactory.fromRDF(m);
			Iterator t = testRS.getResultVars().iterator();
			while (t.hasNext()) {
				System.out.println(t.next());
			}
			
			//Query query = QueryFactory.create("CONSTRUCT { ?s ?p ?o } WHERE {GRAPH ?g { ?s ?p ?o }. FILTER (?s = <http://www.openlinksw.com/schemas/virtrdf#DefaultQuadMap-G>)}");
			//QueryExecution queryExecution = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
			//	this.resultSet = new SPARQLConstructResultSet(queryExecution.execConstruct(), this, query);
			//	return true;
			//}*/
			
		} 
		catch (Exception e) { 
			System.out.println(e.getMessage());
		}

	}

}

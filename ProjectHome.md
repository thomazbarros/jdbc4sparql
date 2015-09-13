To create a JDBC driver that can execute a SPARQL query against a SPARQL endpoint.

Installtion guidelines as follows:

1. Download dependencies and add to classpath

http://code.google.com/p/jdbc4sparql/downloads/detail?name=jdbc4sparql-dependencies.zip

2. Download the jdbc4sparql Driver and add to classpath

http://code.google.com/p/jdbc4sparql/downloads/detail?name=jdbc4sparql-0.0.1.jar

3. Driver name:

org.lexicon.jdbc4sparql.SPARQLDriver

4. Connection URL as follows:

jdbc:sparql:http://<URL of SPARQL endpoint>?<SPARQL Protocol Parameters>

e.g

jdbc:sparql:http://dbpedia.org/sparql
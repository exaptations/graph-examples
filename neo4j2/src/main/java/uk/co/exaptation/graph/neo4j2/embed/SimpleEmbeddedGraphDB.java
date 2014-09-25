package uk.co.exaptation.graph.neo4j2.embed;

import java.io.File;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class SimpleEmbeddedGraphDB extends AbstractEmbeddedDB {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SimpleEmbeddedGraphDB embedded = new SimpleEmbeddedGraphDB();
		dbDir = new File("./target/neo4j-graph");
		embedded.cleardown(false);
		embedded.create();
		embedded.addData();
		embedded.searchDatabase();
		graphDb.shutdown();
	}

	private void searchDatabase() {
		ExecutionEngine engine = new ExecutionEngine(graphDb);
		ExecutionResult result1 = engine.execute("match (n {name: 'my node'}) return n, n.name");
		printResults(result1);
		ExecutionResult result2 = engine.execute("match (n {message: 'Hello'}) return n, n.message");
		printResults(result2);
	}

	private void create() {
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(dbDir.getPath());
		registerShutdownHook(graphDb);
	}

	@Override
	protected void addData() {
		Transaction tx = graphDb.beginTx();
		try {
			Node myNode = graphDb.createNode();
			myNode.setProperty("name", "my node");
			Node firstNode = graphDb.createNode();
			firstNode.setProperty("message", "Hello");
			Node secondNode = graphDb.createNode();
			secondNode.setProperty("message", "World!");
			Relationship relationship = firstNode.createRelationshipTo(secondNode, RelationshipTypes.KNOWS);
			relationship.setProperty("message", "brave Neo4j ");
			tx.success();
		} finally {
			tx.close();
		}
	}

}

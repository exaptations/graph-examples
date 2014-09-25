package uk.co.exaptation.graph.neo4j2.embed;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.test.TestGraphDatabaseFactory;

public class TestEmbeddedGraphDB extends AbstractEmbeddedDB {
	public static void main(String[] args) {
		TestEmbeddedGraphDB embedded = new TestEmbeddedGraphDB();
		embedded.create();
		embedded.addData();
		embedded.searchDatabase();
		graphDb.shutdown();
	}

	private void create() {
		graphDb = new TestGraphDatabaseFactory().newImpermanentDatabase();
		registerShutdownHook(graphDb);
	}
	
	private void searchDatabase() {
		ExecutionEngine engine = new ExecutionEngine(graphDb);
		ExecutionResult result1 = engine.execute("match (n {name: 'my node'}) return n, n.name");
		printResults(result1);
		ExecutionResult result2 = engine.execute("match (n {message: 'Hello'}) return n, n.message");
		printResults(result2);
	}
}

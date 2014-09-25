package uk.co.exaptation.graph.neo4j2.embed;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;

public abstract class AbstractEmbeddedDB {

	protected static GraphDatabaseService graphDb;

	protected static File dbDir;

	protected static enum RelationshipTypes implements RelationshipType {
		KNOWS
	}

	protected void cleardown(boolean cleardown) {
		if (cleardown) {
			try {
				FileUtils.deleteDirectory(dbDir);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	protected static void registerShutdownHook(final GraphDatabaseService graphDb) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				graphDb.shutdown();
			}
		});
	}
	
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
	
	protected void printResults(ExecutionResult result) {
		System.out.println(result.dumpToString());
	}
}

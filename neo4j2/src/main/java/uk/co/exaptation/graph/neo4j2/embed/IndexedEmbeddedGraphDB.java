package uk.co.exaptation.graph.neo4j2.embed;

import java.io.File;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.schema.IndexCreator;
import org.neo4j.graphdb.schema.Schema;

public class IndexedEmbeddedGraphDB extends AbstractEmbeddedDB {

	public static void main(String[] args) {
		IndexedEmbeddedGraphDB embedded = new IndexedEmbeddedGraphDB();
		dbDir = new File("./target/neo4j-graph-index");
		embedded.cleardown(false);
		embedded.create();
		graphDb.shutdown();
	}

	private void create() {
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(dbDir.getPath());
		registerShutdownHook(graphDb);
		createIndex();
		addData();
		queryIndex();
	}

	protected void queryIndex() {
		Label label = DynamicLabel.label( "User" );
		int idToFind = 45;
		String nameToFind = "user" + idToFind + "@neo4j.org";
		Transaction tx = graphDb.beginTx();
		try {
			ResourceIterator<Node> users = graphDb.findNodesByLabelAndProperty( label, "username", nameToFind ).iterator();
			while(users.hasNext()){
				Node node = users.next();
				System.out.println(node.getProperty("username"));
			}
			tx.success();
		} finally {
			tx.close();
		}
	}

	@Override
	protected void addData() {
		Transaction tx = graphDb.beginTx();
		try {
			Label label = DynamicLabel.label("User");
			for (int id = 0; id < 10000; id++) {
				Node userNode = graphDb.createNode(label);
				userNode.setProperty("username", "user" + id + "@neo4j.org");
				tx.success();
			}
		} finally {
			tx.close();
		}
	}

	private void createIndex() {
		Transaction tx = graphDb.beginTx();
		try {
			Schema schema = graphDb.schema();
			IndexCreator indexFor = schema.indexFor(DynamicLabel.label("User"));
			indexFor.on("username").create();
			tx.success();
		} finally {
			tx.close();
		}
	}
}

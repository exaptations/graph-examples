package uk.co.exaptation.graph.neo4j2.embed;

import java.io.File;

import org.neo4j.graphdb.factory.GraphDatabaseBuilder;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;

public class ReadOnlyEmbeddedGraphDB extends IndexedEmbeddedGraphDB {
	public static void main(String[] args) {
		ReadOnlyEmbeddedGraphDB embedded = new ReadOnlyEmbeddedGraphDB();
		dbDir = new File("./target/neo4j-graph-index");
		embedded.create();
		embedded.queryIndex();
		graphDb.shutdown();
	}

	private void create() {
		if (dbDir.exists()) {
			GraphDatabaseFactory graphDatabaseFactory = new GraphDatabaseFactory();
			GraphDatabaseBuilder builder = graphDatabaseFactory.newEmbeddedDatabaseBuilder(dbDir.getPath());
			builder.setConfig(GraphDatabaseSettings.read_only, "true");
			graphDb = builder.newGraphDatabase();
		} else {
			IndexedEmbeddedGraphDB.main(new String[] {});
		}
	}
}

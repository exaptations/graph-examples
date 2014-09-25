package uk.co.exaptation.graph.blueprints;

import java.io.File;
import java.io.FileOutputStream;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.neo4j2.Neo4j2Graph;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import com.tinkerpop.blueprints.util.io.graphml.GraphMLWriter;

public class SimplePropertyGraph {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SimplePropertyGraph propertyGraph = new SimplePropertyGraph();
		propertyGraph.createSimpleGraph();
		propertyGraph.createNeo2Graph();
	}

	private void createNeo2Graph() {
		Graph graph = new Neo4j2Graph("./target/neo_graph");
		Vertex a = graph.addVertex(null);
		Vertex b = graph.addVertex(null);
		a.setProperty("name", "marko");
		b.setProperty("name", "peter");
		Edge e = graph.addEdge(null, a, b, "knows");
		e.setProperty("since", 2006);
		printGraph(graph);
		graph.shutdown();
	}

	private void createSimpleGraph() {
		Graph graph = new TinkerGraph("./target/my_graph");
		Vertex a = graph.addVertex(null);
		Vertex b = graph.addVertex(null);
		a.setProperty("name", "marko");
		b.setProperty("name", "peter");
		Edge e = graph.addEdge(null, a, b, "knows");
		e.setProperty("since", 2006);
		printGraph(graph);
		graph.shutdown();
	}

	private void printGraph(Graph graph) {
		try {
			FileOutputStream fos = new FileOutputStream(new File("./target/graph-print.xml"));
			GraphMLWriter.outputGraph(graph, fos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

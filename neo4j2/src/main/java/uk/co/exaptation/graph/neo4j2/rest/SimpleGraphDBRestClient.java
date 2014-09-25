package uk.co.exaptation.graph.neo4j2.rest;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class SimpleGraphDBRestClient {

	public final String SERVER_ROOT_URI = "http://127.0.0.1:7474/db/data/";

	public static void main(String[] args) {
		SimpleGraphDBRestClient client = new SimpleGraphDBRestClient();
		client.checkDatabaseIsRunning();
		client.sendTransactionalCypherQuery("MATCH (n) WHERE has(n.name) RETURN n.name AS name");
	}

	private void sendTransactionalCypherQuery(String query) {
		// START SNIPPET: queryAllNodes
		final String txUri = SERVER_ROOT_URI + "transaction/commit";
		WebResource resource = Client.create().resource(txUri);

		String payload = "{\"statements\" : [ {\"statement\" : \"" + query + "\"} ]}";
		ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).entity(payload).post(ClientResponse.class);

		System.out.println(String.format("POST [%s] to [%s], status code [%d], returned data: " + System.getProperty("line.separator") + "%s", payload, txUri, response.getStatus(), response.getEntity(String.class)));

		response.close();
		// END SNIPPET: queryAllNodes
	}

	private void checkDatabaseIsRunning() {
		WebResource resource = Client.create().resource(SERVER_ROOT_URI);
		ClientResponse response = resource.get(ClientResponse.class);
		System.out.println(String.format("GET on [%s], status code [%d]", SERVER_ROOT_URI, response.getStatus()));
		response.close();
	}
}

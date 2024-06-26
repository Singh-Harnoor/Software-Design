package ca.yorku.eecs;



import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Config;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.StatementResult;
import static org.neo4j.driver.v1.Values.parameters;

import java.beans.Statement;


public class Database {
	
	public static String uriDb = "bolt://localhost:7687";
	public static String uriUser ="http://localhost:8080";
	public static Config config = Config.builder().withoutEncryption().build();
	public static Driver driver; 
	
	
	public Database() {
		uriDb = "bolt://localhost:7687";
		driver = GraphDatabase.driver(uriDb, AuthTokens.basic("neo4j","123456"), config);
	}
	
	public void addActor(String name, String actorId) {
		StatementResult response;
		try(Session session = driver.session()){
			try(Transaction tx = session.beginTransaction()){
				response = tx.run("MATCH (a:Actor {id: $x})"
								+ "RETURN a", parameters("x", actorId));
				session.close();
			}
		}
		
		if(response.hasNext()) {
			try (Session session = driver.session()){
				session.writeTransaction(tx -> tx.run("MATCH (a:Actor {id: $i})"
						+ "SET a.name = $x", 
						parameters("x", name, "i", actorId)));	
				session.close();
			}
		} else {
			try (Session session = driver.session()){
				session.writeTransaction(tx -> tx.run("MERGE (a:Actor {id: $i, name: $x})", 
						parameters("x", name, "i", actorId)));
				session.close();
				
			}
		}
		
	}
	
	public void addMovie(String name, String movieId) {
		StatementResult response;
		try(Session session = driver.session()){
			try(Transaction tx = session.beginTransaction()){
				response = tx.run("MATCH (m:Movie {id: $x})"
								+ "RETURN m", parameters("x", movieId));
				session.close();
			}
		}
		
		if(response.hasNext()) {
			try (Session session = driver.session()){
				session.writeTransaction(tx -> tx.run("MATCH (m:Movie {id: $i})"
						+ "SET m.name = $x", 
						parameters("x", name, "i", movieId)));	
				session.close();
			}
		} else {
			try (Session session = driver.session()){
				session.writeTransaction(tx -> tx.run("MERGE (m:Movie {id: $i, name: $x})", 
						parameters("x", name, "i", movieId)));
				session.close();
				
			}
		}
	}
	
	public void addRelationship(String actorId, String movieId) {

		try (Session session = driver.session()){
			session.writeTransaction(tx -> tx.run("MATCH (a:Actor {id:$i}),"
					+ "(m:Movie {id:$id})\n" + 
					 "MERGE (a)-[r:ACTED_IN]->(m)\n" + 
					 "RETURN r", parameters("i", actorId, "id", movieId)));
			session.close();
		}
	}
	
	public void addRating(String movieId, Double rating) {
		try (Session session = driver.session()){
			session.writeTransaction(tx -> tx.run("MATCH (m:Movie {id:$x}) "
					+ "SET m.rating = $y "
					+ "RETURN m.name, m.rating", 
					parameters("y", rating, "x", movieId)));
			session.close();
		}
	}
	
	
	
	
	public StatementResult getActor(String actorId)
    {	
		StatementResult node_boolean;
		StatementResult response = null;
        try (Session session = driver.session())
        {
        	try (Transaction tx = session.beginTransaction()) {
        		 node_boolean = tx.run("MATCH (a:Actor {id:$x})"
        				+ "RETURN a.name is NOT NULL"
        				,parameters("x", actorId) );       		
        	}
        }
        try (Session session = driver.session())
        {
        	try (Transaction tx = session.beginTransaction()){

            	if (node_boolean.hasNext()) {
        			response =  tx.run("MATCH p=(a:Actor {id: $x})-[r:ACTED_IN]->(m:Movie)" + 
        					"RETURN a.id, a.name, m.name"
        					,parameters("x", actorId));
        			session.close();
        			return response;
        		}else {
        			session.close();
        			return response;
        		}
        	}
        }
    }
	
	public StatementResult getMovie(String movieId)

    {	
		StatementResult node_boolean;
		StatementResult response = null;
        try (Session session = driver.session())
        {
        	try (Transaction tx = session.beginTransaction()) {
        		 node_boolean = tx.run("MATCH (m:Movie {id:$x})"
        				+ "RETURN m.name is NOT NULL"
        				,parameters("x", movieId) );       		
        	}
        }
        try (Session session = driver.session())
        {
        	try (Transaction tx = session.beginTransaction()){

            	if (node_boolean.hasNext()) {
        			response =  tx.run("MATCH p=(a:Actor)-[r:ACTED_IN]->(m:Movie {id: $x})" + 
        					"RETURN m.name, a.name"
        					,parameters("x", movieId));
        			session.close();
        			return response;
        		}else {
        			System.out.println("404 Not Found!");
        			session.close();
        			return response;
        		}
        	}
        }
    }

	public StatementResult getActorName(String actorId) {
		
		try(Session session = driver.session()){
			try (Transaction tx = session.beginTransaction()){
				StatementResult response =  tx.run("MATCH (a:Actor {id: $x})" + 
    					"RETURN a.name"
    					,parameters("x", actorId));
    			session.close();
    			return response;
			}
		}
	}
	
	public StatementResult getMovieName(String movieId) {
		
		try(Session session = driver.session()){
			try (Transaction tx = session.beginTransaction()){
				StatementResult response =  tx.run("MATCH (m:Movie {id: $x})" + 
    					"RETURN m.name"
    					,parameters("x", movieId));
    			session.close();
    			return response;
			}
		}
	}
	
	public StatementResult hasRelationship(String movieId, String actorId) {
		StatementResult response;
		try(Session session = driver.session()){
			try (Transaction tx = session.beginTransaction()){
				response =  tx.run("MATCH (a:Actor {id: $x})-[r:ACTED_IN]->(m:Movie {id: $y})"
						+ "return r is NOT NULL"
    					,parameters("y", movieId, "x", actorId));
    			session.close();
    			return response;
			}
		}
	}
	
	public StatementResult conputeBaconNumber(String actorId) {
		StatementResult response;
		try(Session session = driver.session()){
			try (Transaction tx = session.beginTransaction()){
				response =  tx.run("MATCH (KevinB:Actor {id: \"nm0000102\"} )"
						+ "MATCH (a:Actor {id: $x})"
						+ "MATCH p = shortestPath((KevinB)-[:ACTED_IN*]-(a))"
						+ "RETURN length(p)/2"
    					,parameters("x", actorId));
    			session.close();
    			return response;
			}
		}
	}
	
	public StatementResult conputeBaconPath(String actorId) {
		StatementResult response;
		try(Session session = driver.session()){
			try (Transaction tx = session.beginTransaction()){
				response =  tx.run("MATCH (KevinB:Actor {id: \"nm0000102\"} )"
						+ "MATCH (a:Actor {id: $i})"
						+ "MATCH p = shortestPath((KevinB)-[:ACTED_IN*]-(a))"
						+ "RETURN [x in nodes(p) | ID(x)]"	
    					,parameters("i", actorId));
    			session.close();
    			return response;
			}
		}
	}
	
	public StatementResult getIdNode(int node) {
		StatementResult response;
		try(Session session = driver.session()){
			try (Transaction tx = session.beginTransaction()){
				response =  tx.run("MATCH (a)"
						+ "WHERE ID(a) = $x"
						+ " RETURN a.id "	
    					,parameters("x", node));
    			session.close();
    			return response;
			}
		}
	}
	
	public StatementResult getMoviesOfRating(Double rating) {
		StatementResult response;
		try(Session session = driver.session()){
			try (Transaction tx = session.beginTransaction()){
				response =  tx.run("MATCH (m:Movie) "
						+ "WHERE m.rating >= $x "
						+ "RETURN m.rating, m.name"	
    					,parameters("x", rating));
    			session.close();
    			return response;
			}
		}
	}
	
	



	
	
	
	
	
}

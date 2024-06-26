package ca.yorku.eecs;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Value;


public class Requests implements HttpHandler{
	
	Database data = new Database();

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		//
    	System.out.println(exchange.getRequestMethod());
		 try {	
	            if (exchange.getRequestMethod().equals("GET")) {
	                handleGet(exchange);
	                System.out.println("get");
	            } else if (exchange.getRequestMethod().equals("PUT")) {
	            	handlePut(exchange);
	            	System.out.println("put");
	            }   
	            else {
	            	sendString(exchange, "Bad Request\n", 400);
	            }
	        } catch (Exception e) {
	        	e.printStackTrace();
	        	sendString(exchange, "Server error\n", 500);
	        }
	}
	
	
	private void sendString(HttpExchange request, String data, int restCode) 
			throws IOException {
		request.sendResponseHeaders(restCode, data.length());
        OutputStream os = request.getResponseBody();
        os.write(data.getBytes());
        os.close();
	}
	
	
	private void handlePut(HttpExchange request) throws IOException {
		String body = Utils.convert(request.getRequestBody());
		URI uri = request.getRequestURI();
		String req = uri.toString();
		System.out.println(req);
		String methodCall = methodCall(req);
		System.out.println(methodCall);
		Map<String, String> queryParam = new LinkedHashMap<String, String>();
		String response;
		try {
			JSONObject deserialised = new JSONObject(body);
			String actorId;
			String name;
			String movieId;

			if(request.getRequestMethod().equals("PUT") &&
					(deserialised.length() == 1 || deserialised.length() == 2)&&
					(deserialised.has("actorId")  || deserialised.has("movieId") || deserialised.has("actorId"))){
				if (deserialised.has("actorId")) {
					actorId = deserialised.getString("actorId");
					queryParam.put("actorId", actorId);
				}
				else if(deserialised.has("movieId")) {
					movieId = deserialised.getString("movieId");
					queryParam.put("movieId", movieId);
				}
				if(deserialised.has("name")) {
					name = deserialised.getString("name");
					queryParam.put("name", name);
				}
				System.out.println(queryParam.toString());
				//method
				//if(deserialised.has("addActor")) {
				//	methodCall = deserialised.getString("addActor");
				//}
				//	else if(deserialised.has("addMovie")) {
				//	methodCall = deserialised.getString("addMovie");
				//	}
				//	else if(deserialised.has("addRelationship")) {
				//		methodCall = deserialised.getString("addRelationship");
				//	}
				//	else if(deserialised.has("addMovieRating")) {
				//		methodCall = deserialised.getString("addMovieRating");
				//	}
			}
			else {
				sendString(request, "BAD REQUEST! ", 400);
			}
		}catch(Exception e) {
			sendString(request, e.getMessage(), 500);
		}

		if (methodCall.equals("addActor")) {
			boolean run = true;
			if(queryParam.get("name") == null  || queryParam.get("actorId")  == null)
			{
				run = false;
				sendString(request, "BAD REQUEST!", 400);
			}
			if (run) {
				try {
					System.out.println("Adding Actor");
					this.data.addActor(queryParam.get("name"), queryParam.get("actorId"));
					response = "Actor name: " + queryParam.get("name") + " ID: " + queryParam.get("actorId") + "\n" ;
					System.out.println("Actor Added!");
					sendString(request, response, 200);
				} catch (Exception e) {
					sendString(request, "Fail in adding the actor!", 500);
				}

			}
			// If the method call is to add a movie to the database
		} else if(methodCall.equals("addMovie")){
			boolean run = true;
			if(queryParam.get("name") == null  || queryParam.get("movieId")  == null)
			{
				run = false;
				sendString(request, "BAD REQUEST!", 400);
			}
			if (run) {
				try {
					this.data.addMovie(queryParam.get("name"), queryParam.get("movieId"));
					response = "Movie name: " + queryParam.get("name") + " ID: " + queryParam.get("movieId") + "\n" ;
					sendString(request, response, 200);
					System.out.println("Actor Added!");
				}catch (Exception e) {
					sendString(request, "Fail in adding the Movie!", 500);
				}
			}

			// Add a relationship between an actor and a movie
		} else if(methodCall.equals("addRelationship")) {
			boolean run = true;
			if(queryParam.get("movieId") == null  || queryParam.get("actorId")  == null)
			{
				run = false;
				sendString(request, "BAD REQUEST!", 400);
			}
			if (run) {
				try {
					this.data.addRelationship(queryParam.get("actorId"), queryParam.get("movieId"));
					response = "Movie ID: " + queryParam.get("movieId") + " Actor ID: " + queryParam.get("actorId") + "\n" ;
					sendString(request, response, 200);
					System.out.println("Relationship Added!");
				}catch (Exception e) {
					sendString(request, "Fail in adding the Relationship!", 500);
				}
			}

		}

		//Add movie rating for new functionality
		else if(methodCall.equals("addMovieRating")) {
			boolean run = true;
			if(queryParam.get("movieId") == null  || queryParam.get("rating")  == null)
			{
				run = false;
				sendString(request, "BAD REQUEST!", 400);
			}
			if(run) {
				try {
					Double i = Double.parseDouble(queryParam.get("rating"));
					this.data.addRating(queryParam.get("movieId"), i);
					response = "Movie ID: " + queryParam.get("movieId") + " is Rated " + queryParam.get("rating") + "\n" ;
					sendString(request, response, 200);
				}catch (Exception e) {
					sendString(request, "Fail to add movie rating", 500);
				}
			}
		}
		else {
			sendString(request, "Method Not implemented!", 500);
		}
    }
	
	
	private void handleGet(HttpExchange request) throws IOException {
		URI uri = request.getRequestURI();
        String query = uri.getQuery();
        System.out.println(query);
        String req = uri.toString();
        String methodCall = methodCall(req); 
        System.out.println(methodCall);      
        Map<String, String> queryParam = putAtrributes(query);
        System.out.println(queryParam);
        //-- GET from the database
        //=============================
        //GET ACTOR FROM THE DATABASE
        if(methodCall.equals("getActor")) {
        	 boolean run = true;
        	 if(queryParam.get("actorId") == null)
        	 {
        		 run = false;
        		 sendString(request, "BAD REQUEST!", 400);
        	}
        	 if(run) {
        		 try {
        			 StatementResult response = this.data.getActor(queryParam.get("actorId"));
                  	if(response == null) {
                  		System.out.println("404: Not Found!");
                  		sendString(request, "404: Not Found", 404);
                  	} else {
                  		if(response.hasNext()) {
                  			ArrayList<String> movies = new ArrayList<String>();
                          	Record record = response.next();
                          	String responseString = "Actor Name: " + record.get("a.name") +
                  									"\nActor ID: " + record.get("a.id");
                          	movies.add(""+record.get("m.name"));
                          	while(response.hasNext()) {
                          		record = response.next();
                          		movies.add(""+record.get("m.name"));
                          	}	
                          	 responseString += "\nMovies: " + movies.toString();
                          	 System.out.println("200: OK!");
                               sendString(request, responseString, 200);
                  		}
                  		else {
                  			StatementResult result = this.data.getActorName(queryParam.get("actorId"));
                  			Record record = result.next();
                  			String responseString = "Actor Name: " + record.get("a.name") +
          							"\nActor ID: " + queryParam.get("actorId")
                  					+ "\nMovies: []";
                  			sendString(request, responseString, 200);
                  		}
                  	} 
        		 } 
        		 catch(Exception e) {
        			 sendString(request, "Server ERROR! " + e.getMessage(), 500);
        		 }
        		
        	 }
        //===================================	
        	 
        // GET THE MOVIE FROM THE DATABASE   
        } else if(methodCall.equals("getMovie")) {
        	boolean run = true;
       	 	if(queryParam.get("movieId") == null)
       	 	{
       	 		run = false;
       	 		sendString(request, "BAD REQUEST!", 400);
       	 	}
       	 	if(run) {
       	 		try {
       	 		StatementResult response = this.data.getMovie(queryParam.get("movieId"));
            	if(response == null) {
            		System.out.println("404: Not Found!");
            		sendString(request, "404: Not Found", 404);
            	} else {
            		if (response.hasNext()) {
                		ArrayList<String> actors = new ArrayList<String>();
                    	Record record = response.next();
                    	String responseString = "Movie Name: " + record.get("m.name") +
            									"\nMovie ID: " + queryParam.get("movieId");
                    	actors.add(""+record.get("a.name"));
                    	while(response.hasNext()) {
                    		record = response.next();
                    		actors.add(""+record.get("a.name"));
                    	}	
                    	 responseString += "\nActors: " + actors.toString();
                    	 System.out.println("200: OK!");
                         sendString(request, responseString, 200);
            		}
            		else {
            			StatementResult result = this.data.getMovieName(queryParam.get("movieId"));
            			Record record = result.next();
            			String responseString = "Movie Name: " + record.get("m.name") +
    							"\nMovie ID: " + queryParam.get("movieId")
            					+ "\nActors: []";
            			sendString(request, responseString, 200);
            			
            		}
            	}
       	 		}catch(Exception e) {
       	 			sendString(request, "Server Error! " + e.getMessage(), 500);
       	 		}
       	 	}
        // ===========================================
       	
       	// GET IF AN ACTOR ACTED IN A MOVIE
        	
        } else if(methodCall.equals("hasRelationship")) {
        	boolean run = true;
       	 	if(queryParam.get("movieId") == null || queryParam.get("actorId")  == null)
       	 	{
       	 		run = false;
       	 		sendString(request, "BAD REQUEST!", 400);
       	 	}
       	 	if (run) {
       	 	try {
        		StatementResult response = this.data.hasRelationship(queryParam.get("movieId"), queryParam.get("actorId"));
            	if(response.hasNext()) {
            		String result = "Actor ID: " + queryParam.get("actorId") + " has ACTED_IN Movie ID:" + queryParam.get("movieId");
            		sendString(request, result, 200);
            	}else if (this.data.getMovie(queryParam.get("movieId")) == null){
            		sendString(request, "Movie not Found", 404);
            	}
            	else if (this.data.getActor(queryParam.get("actorId")) == null){
            		sendString(request, "Actor not Found", 404);
            	}
            	else {
            		sendString(request, "No Relationship", 200);
            	}
        	}catch (Exception e) {
        		sendString(request, "SERVER ERROR! " + e.getMessage(), 500);
        	}
       	 	}
        //=================================================
       	
       	//Computer Bacon Number 
        	
        } else if(methodCall.equals("computeBaconNumber")) {
        	boolean run = true;
       	 	if(queryParam.get("actorId") == null)
       	 	{
       	 		run = false;
       	 		sendString(request, "BAD REQUEST!", 400);
       	 	}
       	 	if(run) {
       	 		try {
       	 		if(queryParam.get("actorId").equals("nm0000102")) {
       	 			sendString(request, "Actor: " + queryParam.get("actorId") + " has Bacon number = " + "0" , 200);
       	 		}
       	 		else {
       	 			StatementResult response = this.data.conputeBaconNumber(queryParam.get("actorId"));
       	 			if(response.hasNext()) {
            			Record record = response.next();
            			String result = "Actor: " + queryParam.get("actorId") + " has Bacon number = " + record.get("length(p)/2");
            			sendString(request, result, 200);
            		}
            		else {
            			sendString(request, "Path Not Found!", 404);
            		}
       	 		}
  
       	 		}catch(Exception e) {
       	 			sendString(request, "SERVER ERROR! "+ e.getMessage(), 500);
       	 		}
       	 	}
        //===================================================================
       	
       	// COMPUTE THE BACON PATH
       	 	
        } else if(methodCall.equals("computeBaconPath")) {
        	boolean run = true;
       	 	if(queryParam.get("actorId") == null)
       	 	{
       	 		run = false;
       	 		sendString(request, "BAD REQUEST!", 400);
       	 	}
       	 	if(run) {
       	 		try {
       	 		if(queryParam.get("actorId").equals("nm0000102")) {
       	 			sendString(request, "Actor: " + queryParam.get("actorId") + " has Bacon Path = " + "[]" , 200);
       	 		}
       	 		else {
       	 		StatementResult response = this.data.conputeBaconPath(queryParam.get("actorId"));
            	if(response.hasNext()) {
            		Record record = response.next();
            		Value nodeString = record.get("[x in nodes(p) | ID(x)]");
            		List<Object> nodes = nodeString.asList();
            		ArrayList<String> actors = new ArrayList<String>();
            		for(int i = 0; i < nodes.size(); i++) {
            			StatementResult actor = this.data.getIdNode(Integer.parseInt((String.valueOf(nodes.get(i))) ) );
            			if(actor.hasNext()) {
            				Record recordActor = actor.next();
            				actors.add(recordActor.get("a.id").toString());
            			}
            		}
            		String result = "Actor: " + queryParam.get("actorId") + " has Bacon Path as => " + actors.toString();
            		sendString(request, result, 200);
            	}
            	else {
            		sendString(request, "Path Not Found!", 404);
            	}
       	 		}	
       	 		}catch(Exception e) {
       	 			sendString(request, "SERVER ERROR! " + e.getMessage(), 500);
       	 		}
       	 	}
        //===========================================================
       	 	
       	//Number of movies an actor acted in
        }
        else if(methodCall.equals("getNumberOfMovies")){
        	boolean run = true;
       	 	if(queryParam.get("actorId") == null)
       	 	{
       	 		run = false;
       	 		sendString(request, "BAD REQUEST!", 400);
       	 	}
       	 	if(run) {
       	 		try{
       	 		StatementResult response = this.data.getActor(queryParam.get("actorId"));
            	if(response == null) {
            		System.out.println("404: Not Found!");
            		sendString(request, "404: Not Found", 404);
            	}
            	else {
            		if (response.hasNext()) {
            			ArrayList<String> movies = new ArrayList<String>();
                    	Record record = response.next();
                    	String responseString = "Actor Name: " + record.get("a.name");
                    	int nMovies = 0;
                    	movies.add(""+record.get("m.name"));
                    	nMovies++;
                    	while(response.hasNext()) {
                    		record = response.next();
                    		movies.add(""+record.get("m.name"));
                    		nMovies++;
                    	}
                    	responseString += " Number Of Movies: " + nMovies; 
                    	sendString(request, responseString, 200);
            		}
            		else {
            			StatementResult result = this.data.getActorName(queryParam.get("actorId"));
            			Record record = result.next();
            			String responseString = "Actor Name: " + record.get("a.name") + " Number Of Movies: 0";
            			sendString(request, responseString, 200);
            		}		
            	}
       	 		}catch(Exception e) {
       	 			sendString(request, "SERVER ERROR! " + e.getMessage(), 500);
       	 		}
       	 	}
        	
        }
        //=========================================================================
        
        // GET the movies rated "rating" or above
        else if(methodCall.equals("getMoviesOfRating")) {
        	boolean run = true;
       	 	if(queryParam.get("rating") == null)
       	 	{
       	 		run = false;
       	 		sendString(request, "BAD REQUEST!", 400);
       	 	}
       	 	if(run) {
       	 		try {
       	 		double i = Double.parseDouble(queryParam.get("rating"));
            	StatementResult response = this.data.getMoviesOfRating(i);
            	if(response == null) {
            		sendString(request, "404: No Movies of this rating or higher", 404);
            	}
            	else if(response.hasNext()){
            		HashMap<Value, Value> movies = new HashMap<>();
            		Record record = response.next();
            		movies.put(record.get("m.name"), record.get("m.rating"));
            		while(response.hasNext()) {
                		record = response.next();
                		movies.put(record.get("m.name"), record.get("m.rating"));
                	}
            		sendString(request, movies.toString(), 200);		
            	}
            	else {
            		sendString(request, "[]", 200);
            	}
       	 		}catch(Exception e) {
       	 			sendString(request, "SERVER ERROR! " + e.getMessage(), 500);
       	 		}
       	 	}
        	
        }
        //=================================================================
        
        else {
        	sendString(request, "Method Not implemented!", 500);
        }
        
        
    }
	
	
	private static String methodCall(String query) throws UnsupportedEncodingException{
		String method;
		int i = query.indexOf("/api/v1/");
		int startIndex = i + 8;
		int endIndex = query.indexOf("?");
		method = query.substring(startIndex, endIndex);
		return method;
		
	}
	
	
	private static Map<String, String> putAtrributes(String query) throws UnsupportedEncodingException {
        Map<String, String> query_pairs = new LinkedHashMap<String, String>();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        return query_pairs;
    }
	
	
	
	

}

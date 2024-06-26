package basic;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class Adder implements HttpHandler{

	@Override
	public void handle(HttpExchange request) throws IOException {
		// TODO Auto-generated method stub
		
        try {
            if (request.getRequestMethod().equals("GET")) {
                handleGet(request);
                System.out.println("get");
            } else
            	sendString(request, "Unimplemented method\n", 501);
        } catch (Exception e) {
        	e.printStackTrace();
        	sendString(request, "Server error\n", 500);
        }
		
	}
	
	private void sendString(HttpExchange request, String data, int restCode) 
			throws IOException {
		request.sendResponseHeaders(restCode, data.length());
        OutputStream os = request.getResponseBody();
        os.write(data.getBytes());
        os.close();
	}
	
    
    private void handleGet(HttpExchange request) throws IOException {
    	
        URI uri = request.getRequestURI();
        String query = uri.getQuery();
        System.out.println(query);
        Map<String, String> queryParam = splitQuery(query);
        System.out.println(queryParam);
        long first = Long.parseLong(queryParam.get("firstNumber"));
        long second = Long.parseLong(queryParam.get("secondNumber"));

        
        // add code for incorrect parameters

        /* TODO: Implement the math logic */
        long answer = first + second;
        System.out.println(first+","+second+","+answer);
        String response = Long.toString(answer) + "\n";
        sendString(request, response, 200);
    }

    
    private static Map<String, String> splitQuery(String query) throws UnsupportedEncodingException {
        Map<String, String> query_pairs = new LinkedHashMap<String, String>();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        return query_pairs;
    }
}

package basic;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;


public class BasicHttpServerExample {

	  public static void main(String[] args) throws IOException {
	      HttpServer server = HttpServer.create(new InetSocketAddress(8500), 0);
	      Adder adder = new Adder();
	      server.createContext("/api/addTwoNumbers", adder::handle);
	      server.start();
	      System.out.println("Server started");
	  }

//http://localhost:8500/api/addTwoNumbers?firstNumber=1&secondNumber=2
	}

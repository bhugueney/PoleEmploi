package co.simplon.PoleEmploi;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.*;

import co.simplon.PoleEmploi.HelloServlet;


public class Application {

	public static void main(String args[] ) throws Exception{
		Server server = new Server(9092);
		final ServletContextHandler context = new ServletContextHandler();
      	context.addServlet(DefaultServlet.class, "/*");
      	String [] welcomeFiles = {"indexTOTO.html"};
	    context.setWelcomeFiles(welcomeFiles);
	    context.setResourceBase("./src/main/resources/");
        System.err.println("resourcesBase:"+context.getResourceBase());

	    context.addServlet(HelloServlet.class, "/dynamic/*");
        server.setHandler(context);
        server.setStopAtShutdown(true);
        server.start();
        server.join();
	}
	
}

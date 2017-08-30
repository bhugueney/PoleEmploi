package co.simplon.PoleEmploi;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.*;

import co.simplon.PoleEmploi.HelloServlet;


public class Application {

	public static void main(String args[] ) throws Exception{
		Server server = new Server(9092);
		final ServletContextHandler context = new ServletContextHandler();
      	context.addServlet(DefaultServlet.class, "/*");
      	String [] welcomeFiles = {"index.html"};
	    context.setWelcomeFiles(welcomeFiles);
	    context.setResourceBase("./src/main/resources/");

	    ServletHolder holderDynamic = new ServletHolder("dynamic", HelloServlet.class);
        context.addServlet(holderDynamic, "/dynamic/*");
	    
        System.err.println("resourcesBase:"+context.getResourceBase());
        server.setHandler(context);
        server.setStopAtShutdown(true);
        server.start();
        server.join();
	}
	
}

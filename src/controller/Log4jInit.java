package controller;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.xml.DOMConfigurator;

public class Log4jInit implements ServletContextListener{

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        final ServletContext servletContext = event.getServletContext();
        final String log4jConfigLocation = servletContext.getInitParameter("log4jConfigLocation");
        final String log4jFilename = servletContext.getRealPath(log4jConfigLocation);
        final DOMConfigurator configurator = new DOMConfigurator();
        configurator.doConfigure(log4jFilename, LogManager.getLoggerRepository());
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {}

}

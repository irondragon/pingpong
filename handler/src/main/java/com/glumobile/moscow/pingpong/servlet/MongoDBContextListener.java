package com.glumobile.moscow.pingpong.servlet;

import com.mongodb.MongoClient;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class MongoDBContextListener implements ServletContextListener {

    private final static Logger LOGGER = Logger.getLogger(MongoDBContextListener.class);
    public final static String MONGO_CLIENT = "MONGO_CLIENT";
    public final static String MONGODB_HOST = "MONGODB_HOST";
    public final static String MONGODB_PORT = "MONGODB_PORT";

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        final MongoClient mongo = (MongoClient) servletContextEvent.getServletContext()
                .getAttribute(MONGO_CLIENT);
        if (mongo != null) {
            mongo.close();
        }
        LOGGER.info("Mongo client was closed successfully");
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        final MongoClient mongo = new MongoClient(
                servletContext.getInitParameter(MONGODB_HOST),
                Integer.parseInt(servletContext.getInitParameter(MONGODB_PORT)));
        LOGGER.info("Mongo client was initialized successfully");
        servletContextEvent.getServletContext().setAttribute(MONGO_CLIENT, mongo);
    }
}
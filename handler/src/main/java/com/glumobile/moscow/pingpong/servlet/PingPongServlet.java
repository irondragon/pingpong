package com.glumobile.moscow.pingpong.servlet;

import com.glumobile.moscow.pingpong.Handler;
import com.glumobile.moscow.pingpong.IHandler;
import com.mongodb.MongoClient;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;


public class PingPongServlet extends HttpServlet {

    private final static Logger LOGGER = Logger.getLogger(PingPongServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String userJsonString = "";
        if (br != null) {
            userJsonString = br.readLine();
            LOGGER.info("Request json string = " + userJsonString);
        }
        br.close();
        final MongoClient mongo =
                (MongoClient) request.getServletContext()
                        .getAttribute(MongoDBContextListener.MONGO_CLIENT);
        final IHandler handler = new Handler(mongo, userJsonString);
        userJsonString = handler.work();

        response.setContentType("application/json");
        final PrintWriter out = response.getWriter();
        out.print(userJsonString);
        out.flush();
        out.close();
        LOGGER.info("Sent " + userJsonString);

    }
}

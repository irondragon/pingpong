package com.glumobile.moscow.pingpong;

import com.glumobile.moscow.pingpong.commands.CommandType;
import com.glumobile.moscow.pingpong.commands.ICommand;
import com.glumobile.moscow.pingpong.commands.PingImplCommand;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import org.apache.log4j.Logger;
import org.bson.Document;


public class Handler implements IHandler {

    private final static Logger LOGGER = Logger.getLogger(Handler.class);

    private final MongoClient mongo;
    private final String json;

    public Handler(final MongoClient mongo, final String json) {
        this.json = json;
        this.mongo = mongo;
    }

    @Override
    public String work() {
        final Document ping = Document.parse(json);
        final String commandString = (String)ping.get("commandType");
        final CommandType commandType = commandString == null ? CommandType.EMPTY : CommandType.valueOf(commandString);
        final ICommand command;
        String result = null;
        switch (commandType) {
            case PING:
                command = new PingImplCommand(mongo, ping);
                LOGGER.info("Ping command will be executed");
                Document pong = command.execute();
                result = pong.toJson();
                LOGGER.info("Result of PING Command = " + result);
                break;
        }
        return result;
    }
}

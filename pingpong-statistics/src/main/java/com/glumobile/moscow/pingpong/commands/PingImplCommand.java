package com.glumobile.moscow.pingpong.commands;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;

public class PingImplCommand implements ICommand {

    private final static Logger LOGGER = Logger.getLogger(PingImplCommand.class);
    private final static String USERS_COLLECTION = "Users";
    private final MongoClient mongo;
    private final Document ping;

    public PingImplCommand(final MongoClient mongo, final Document ping) {
        this.mongo = mongo;
        this.ping = ping;
    }

    @Override
    public Document execute() {
        final MongoCollection<Document> users = mongo.getDatabase(MONGO_DATABASE_NAME).getCollection(USERS_COLLECTION);
        LOGGER.info("User will be inserted");
        users.insertOne(ping);
        LOGGER.info("User was inserted");
        final String sessionId = (String) ping.get("sessionId");
        final Bson filter = (BasicDBObject) BasicDBObjectBuilder.start().append("sessionId", sessionId).get();
        final long count = users.count(filter);
        LOGGER.info("Get user with sessionId= " + sessionId + " and count=" + count);
        Document pong = ping;
        pong.put("commandType", CommandType.PONG.toString());
        pong.put("pingCount", String.valueOf(count));
        pong.remove("_id");
        return pong;
    }

}

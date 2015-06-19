package com.glumobile.moscow.pingpong.commands;

import org.bson.Document;

public interface ICommand {

    public final static String MONGO_DATABASE_NAME = "PingPong";

    public Document execute();

}

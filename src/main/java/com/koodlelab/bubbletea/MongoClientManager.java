package com.koodlelab.bubbletea;

import com.mongodb.MongoClient;
import com.yammer.dropwizard.lifecycle.Managed;
import org.eclipse.jetty.util.component.LifeCycle;

public class MongoClientManager implements Managed {

    private MongoClient mongoClient;

    public MongoClientManager(MongoClient mongoClient) {

        this.mongoClient = mongoClient;
    }

    @Override
    public void start() throws Exception {

    }

    @Override
    public void stop() throws Exception {
        this.mongoClient.close();
    }
}

package com.koodlelab.bubbletea;

import com.mongodb.MongoClient;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

public class BubbleTeaShopService extends Service<BubbleTeaShopConfiguration> {

    public static void main(String[] args) throws Exception {
        new BubbleTeaShopService().run(args);
    }

    @Override
    public void initialize(Bootstrap<BubbleTeaShopConfiguration> bootstrap) {
        AssetsBundle bundle = new AssetsBundle("/html", "/");
        bootstrap.addBundle(bundle);
    }

    @Override
    public void run(BubbleTeaShopConfiguration configuration, Environment environment) throws Exception {
        MongoClient mongoClient = new MongoClient();
        environment.manage(new MongoClientManager(mongoClient));
        environment.addResource(new BubbleTeaShopResource(mongoClient));
    }
}

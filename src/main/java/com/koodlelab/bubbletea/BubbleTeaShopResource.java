package com.koodlelab.bubbletea;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.QueryBuilder;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/BubbleTeaShop")
@Produces(MediaType.APPLICATION_JSON)
public class BubbleTeaShopResource {

    private Datastore datastore;

    public BubbleTeaShopResource(final MongoClient mongoClient) {
        this.datastore = new Morphia().createDatastore(mongoClient, "bubbleTea");
    }

    @Path("nearest/{latitude}/{longitude}")
    @GET
    public Object getNearest(@PathParam("latitude") double latitude,
                             @PathParam("longitude") double longitude) {
        DBCollection collection = datastore.getDB().getCollection("BubbleTeaShop");
        DBObject shop = collection.findOne(QueryBuilder.start("location").nearSphere(longitude, latitude).get());
        if (shop == null) {
            throw new WebApplicationException(404);
        }
        return shop;
    }

    @Path("{id}/order/")
    @POST()
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createOrder(@PathParam("id") long shopId, Order  order) {
        order.setShopId(shopId);
        datastore.save(order);
        return Response.created(URI.create(order.getId())).entity(order).build();
    }

}

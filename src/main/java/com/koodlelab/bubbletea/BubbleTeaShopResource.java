package com.koodlelab.bubbletea;

import com.mongodb.MongoClient;
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

    @Path("{id}/order/")
    @POST()
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createOrder(@PathParam("id") long shopId, Order  order) {
        order.setShopId(shopId);
        datastore.save(order);
        return Response.created(URI.create(order.getId())).entity(order).build();
    }

}

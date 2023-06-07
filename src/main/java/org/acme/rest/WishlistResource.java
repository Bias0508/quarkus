package org.acme.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.acme.domain.Wishlist;
import org.acme.domain.WishlistService;

import java.util.List;
import java.util.UUID;

@Path("/wishlist")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WishlistResource {

    private WishlistService wishlistService = new WishlistService();

    @POST
    public Response addToWishlist(@QueryParam("eventId") UUID eventId, @QueryParam("customerId") UUID customerId) {
        System.out.println("add to wishlist");
        try {
            wishlistService.addToWishlist(customerId, eventId);
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid customer ID").build();
        }
    }

    @DELETE
    public Response removeFromWishlist(@QueryParam("eventId") String eventId, @QueryParam("customerId") String customerId) {
        System.out.println("rem to wishlist");
        try {
            UUID customerUUID = UUID.fromString(customerId);
            UUID eventUUID = UUID.fromString(eventId);
            wishlistService.removeFromWishlist(customerUUID, eventUUID);
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid customer or event ID").build();
        }
    }

    @GET
    public Response getWishlist(@QueryParam("customerId") String customerId) {
        System.out.println("get to wishlist");
        try {
            UUID customerUUID = UUID.fromString(customerId);
            Wishlist wishlist = wishlistService.getWishlist(customerUUID);
            List<UUID> events = wishlist.getEvents();
            return Response.ok(events).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid customer ID").build();
        }
    }
}

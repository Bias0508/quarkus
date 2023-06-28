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

    @GET
    @Path("/add")
    public Response addToWishlist(@QueryParam("eventId") UUID eventId, @QueryParam("customerId") String customerId) {
        System.out.println("add to wishlist");
        try {
            wishlistService.addToWishlist(customerId, eventId);
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid customer ID").build();
        }
    }

    @GET
    @Path("/remove")
    public Response removeFromWishlist(@QueryParam("eventId") UUID eventId, @QueryParam("customerId") String customerId) {
        System.out.println("rem from wishlist");
        try {
            wishlistService.removeFromWishlist(customerId, eventId);
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid customer or event ID").build();
        }
    }

    @GET
    @Path("display")
    public Response getWishlist(@QueryParam("customerId") String customerId) {
        System.out.println("get wishlist");
        try {
            Wishlist wishlist = wishlistService.getWishlist(customerId);
            List<UUID> events = wishlist.getEvents();
            return Response.ok(events).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid customer ID").build();
        }
    }
}

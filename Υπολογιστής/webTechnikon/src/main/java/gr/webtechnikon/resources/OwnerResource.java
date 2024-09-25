package gr.webtechnikon.resources;

import gr.webtechnikon.model.Owner;
import gr.webtechnikon.services.OwnerService;
import gr.webtechnikon.services.OwnerServiceInterface;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("owners")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class OwnerResource {

   @Inject
   private  OwnerServiceInterface ownerService;
    
//    @Inject
//    public OwnerResource(OwnerServiceInterface ownerService) {
//        this.ownerService = ownerService;
//    }
    
    
    @GET
    @Path("{id}")
    public Response getOwnerById(@PathParam("id") Long ownerId) {
        Optional<Owner> owner = ownerService.findOwnerById(ownerId);
        if (owner.isPresent()) {
            return Response.ok(owner.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Owner not found").build();
        }
    }
    
    @GET
    public Response getAllOwners() {
        List<Owner> owners = ownerService.findAllOwners();
        return Response.ok(owners).build();
    }
    
    @POST
   @Consumes("application/json")
   @Produces("application/json")
    public Response createOwner(Owner owner) {
        Optional<Owner> newOwner = ownerService.createOwner(owner);
        if (newOwner.isPresent()) {
            return Response.status(Response.Status.CREATED).entity(newOwner.get()).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to create owner").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateOwner(@PathParam("id") Long ownerId, Owner owner) {
        owner.setOwnerId(ownerId);
        Optional<Owner> updatedOwner = ownerService.updateOwner(owner);
        if (updatedOwner.isPresent()) {
            return Response.ok(updatedOwner.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Owner not found").build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Consumes("application/json")
   @Produces("application/json")
    public Response deleteOwner(@PathParam("id") Long ownerId) {
        boolean isDeleted = ownerService.safeDeleteOwnerById(ownerId);
        if (isDeleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Owner not found").build();
        }
    }
}

package gr.technikonweb.resources;

import gr.technikonweb.models.Owner;
import gr.technikonweb.models.Property;
import gr.technikonweb.models.Repair;
import gr.technikonweb.services.OwnerServiceInterface;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import lombok.Builder.Default;


@Path("/owners")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OwnerResource {
    
    

    @Inject
    private OwnerServiceInterface ownerService;
//
//    @GET
//    @Path("/{ownerId}")
//    public Response getOwner(@PathParam("ownerId") Long ownerId) {
//        Optional<Owner> owner = ownerService.getOwnerDetails(ownerId);
//        return owner.map(Response::ok)
//                   
//                    .entity(owner.get())
//                    .build();
//    }

    @GET
    @Path("/{ownerId}/properties")
    public Response getPropertiesByOwner(@PathParam("ownerId") Long ownerId) {
        List<Property> properties = ownerService.getPropertiesByOwnerId(ownerId);
        return Response.ok(properties).build();
    }

    @POST
    public Response createOwner(Owner owner) {
        Optional<Owner> createdOwner = ownerService.updateOwnerDetails(owner);
        return createdOwner.map(o -> Response.status(Response.Status.CREATED).entity(o).build())
                           .orElseGet(() -> Response.status(Response.Status.BAD_REQUEST).build());
    }

//    @PUT
//    @Path("/{ownerId}")
//    public Response updateOwner(@PathParam("ownerId") Long ownerId, Owner owner) {
//        owner.setId(ownerId);
//        Optional<Owner> updatedOwner = ownerService.updateOwnerDetails(owner);
//        return updatedOwner.map(o -> Response.ok(o).build())
//                           .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
//    }

    @DELETE
    @Path("/{ownerId}")
    public Response deleteOwner(@PathParam("ownerId") Long ownerId) {
        // Implement deletion logic for owners if necessary
        // For now, we're returning a NOT_IMPLEMENTED response
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }

    @GET
    @Path("/{ownerId}/repairs")
    public Response getRepairsByOwner(@PathParam("ownerId") Long ownerId) {
        List<Repair> repairs = ownerService.getRepairs(ownerId);
        return Response.ok(repairs).build();
    }

//    @POST
//    @Path("/{ownerId}/properties")
//    public Response createProperty(@PathParam("ownerId") Long ownerId, Property property) {
//        property.setOwnerId(ownerId); // Assuming Property has a method to set ownerId
//        Optional<Property> createdProperty = ownerService.createProperty(property);
//        return createdProperty.map(p -> Response.status(Response.Status.CREATED).entity(p).build())
//                              .orElseGet(() -> Response.status(Response.Status.BAD_REQUEST).build());
//    }
//
//    @POST
//    @Path("/{ownerId}/repairs")
//    public Response createRepair(@PathParam("ownerId") Long ownerId, Repair repair) {
//        repair.setOwnerId(ownerId); // Assuming Repair has a method to set ownerId
//        Optional<Repair> createdRepair = ownerService.createRepair(repair);
//        return createdRepair.map(r -> Response.status(Response.Status.CREATED).entity(r).build())
//                            .orElseGet(() -> Response.status(Response.Status.BAD_REQUEST).build());
//    }
}

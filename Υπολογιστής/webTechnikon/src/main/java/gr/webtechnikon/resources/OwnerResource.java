package gr.webtechnikon.resources;

import gr.webtechnikon.model.Owner;
import gr.webtechnikon.model.Property;
import gr.webtechnikon.model.Repair;
import gr.webtechnikon.services.OwnerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/owners")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OwnerResource {

    @Inject
    private OwnerService ownerService;

    @GET
    public Response getAllOwners() {
        List<Owner> owners = ownerService.findAllOwners();
        return Response.ok(owners).build();
    }

 
//    @GET
//    @Path("/{id}")
//    public Response getOwnerById(@PathParam("id") Long id) {
//        Optional<Owner> owner = ownerService.findOwnerById(id);
//        if (owner.isPresent()) {
//            return Response.ok(owner.get()).build();
//        } else {
//            return Response.status(Response.Status.NOT_FOUND).entity("Owner not found").build();
//        }
//    }
//
//    @POST
//    public Response createOwner(Owner owner) {
//        Optional<Owner> createdOwner = ownerService.createOwner(owner);
//        if (createdOwner.isPresent()) {
//            return Response.status(Response.Status.CREATED).entity(createdOwner.get()).build();
//        } else {
//            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to create owner").build();
//        }
//    }


    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateOwner(@PathParam("id") Long id, Owner owner) {
        if (owner.getOwnerId() != id) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Owner ID in the path does not match the ID in the request body").build();
        }

        Optional<Owner> updatedOwner = ownerService.updateOwner(owner);

        if (updatedOwner.isPresent()) {
            return Response.ok(updatedOwner.get()).build();
        }

        return Response.status(Response.Status.NOT_FOUND)
                .entity("Owner with ID " + id + " not found").build();
    }

    
    @DELETE
    @Path("/{id}")
    public Response deleteOwner(@PathParam("id") Long id) {
        boolean isDeleted = ownerService.safeDeleteOwnerById(id);
        if (isDeleted) {
            return Response.noContent().build(); 
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Owner not found").build();
        }
    }

    
    @GET
    @Path("/{ownerId}/properties")
    public Response getPropertiesByOwnerId(@PathParam("ownerId") Long ownerId) {
        List<Property> properties = ownerService.getPropertiesByOwnerId(ownerId);
        return Response.ok(properties).build();
    }
    
    @GET
    @Path("/{ownerId}/properties")
    public Response getPropertiesDetails(@PathParam("propertyId") Long ownerId) {
        List<Property> properties = ownerService.getPropertiesByOwnerId(ownerId);
        return Response.ok(properties).build();
    }


//    
//    @GET
//    @Path("/{ownerId}/repairs")
//    public Response getRepairsForOwner(@PathParam("ownerId") Long ownerId) {
//        List<Repair> repairs = ownerService.getRepairsForOwner(ownerId);
//        return Response.ok(repairs).build();
//    }

    
    @POST
    @Path("/{ownerId}/properties")
    public Response createProperty(@PathParam("ownerId") Long ownerId, Property property) {
        
        Optional<Owner> ownerOpt = ownerService.findOwnerById(ownerId);

        if (ownerOpt.isPresent()) {
            Owner owner = ownerOpt.get();

            
            Property newProperty = new Property(
                    property.getPropertyCode(),
                    property.getAddress(),
                    property.getYearOfConstruction(),
                    property.getPropertyType(),
                    owner
            );

            Optional<Property> createdProperty = ownerService.createProperty(newProperty);
            if (createdProperty.isPresent()) {
                return Response.status(Response.Status.CREATED).entity(createdProperty.get()).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Failed to create property").build();
            }
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Owner not found").build();
        }
    }

    
    @PUT
    @Path("/properties/{propertyId}")
    public Response updateProperty(@PathParam("propertyId") Long propertyId, Property property) {
        Optional<Property> updatedProperty = ownerService.updateProperty(propertyId, property);
        if (updatedProperty.isPresent()) {
            return Response.ok(updatedProperty.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Property not found").build();
        }
    }

    
    @DELETE
    @Path("/properties/{propertyId}")
    public Response deleteProperty(@PathParam("propertyId") Long propertyId) {
        boolean isDeleted = ownerService.deleteProperty(propertyId);
        if (isDeleted) {
            return Response.noContent().build(); 
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Property not found").build();
        }
    }

   
    @POST
    @Path("/repairs")
    public Response createRepair(Repair repair) {
        Optional<Repair> createdRepair = ownerService.createRepair(repair);
        if (createdRepair.isPresent()) {
            return Response.status(Response.Status.CREATED).entity(createdRepair.get()).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to create repair").build();
        }
    }

    
    @PUT
    @Path("/repairs/{repairId}")
    public Response updateRepair(@PathParam("repairId") Long repairId, Repair repair) {
        Optional<Repair> updatedRepair = ownerService.updateRepair(repairId, repair);
        if (updatedRepair.isPresent()) {
            return Response.ok(updatedRepair.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Repair not found").build();
        }
    }

    // Delete a repair
    @DELETE
    @Path("/repairs/{repairId}")
    public Response deleteRepair(@PathParam("repairId") Long repairId) {
        boolean isDeleted = ownerService.deleteRepair(repairId);
        if (isDeleted) {
            return Response.noContent().build(); // 204 No Content
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Repair not found").build();
        }
    }
}

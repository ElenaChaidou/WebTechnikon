package gr.webtechnikon.resources;

import gr.webtechnikon.model.Owner;
import gr.webtechnikon.model.Property;
import gr.webtechnikon.model.Repair;
import gr.webtechnikon.services.OwnerService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Path("/owners")
@Slf4j
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OwnerResource {

    @Inject
    private OwnerService ownerService;

    //works
    @GET
    public Response getAllOwners() {
        List<Owner> owners = ownerService.findAllOwners();
        return Response.ok(owners).build();
    }

    //works
    @Path("/{id}")
    @GET
    public Response getOwnerById(@PathParam("id") Long id) {
        Optional<Owner> owner = ownerService.findOwnerById(id);
        if (owner.isPresent()) {
            return Response.ok(owner.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Owner not found").build();
        }
    }
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

    //works
    @Path("/{id}")
    @PUT
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

    //works
    @Path("/{id}")
    @DELETE
    public Response deleteOwner(@PathParam("id") Long id) {
        boolean isDeleted = ownerService.safeDeleteOwnerById(id);
        if (isDeleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Owner not found").build();
        }
    }
    
    //works
    @Path("/properties/ownerId/{ownerId}")
    @GET
    public List<Property> getPropertiesByOwnerId(@PathParam("ownerId") Long ownerId) {
        
        return ownerService.getPropertiesByOwnerId(ownerId);
    }

    //works
    @Path("/properties/propertyId/{propertyId}")
    @GET
    public Response getPropertiesDetails(@PathParam("propertyId") Long propertyId) {
        Optional<Property> propertyOptional = ownerService.getPropertyDetails(propertyId);
        if (propertyOptional.isPresent()) {
            return Response.ok(propertyOptional.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

//    
    @GET
    @Path("/{ownerId}/repairs")
    public Response getRepairsForOwner(@PathParam("ownerId") Long ownerId) {
        List<Repair> repairs = ownerService.getRepairsForOwner(ownerId);
        return Response.ok(repairs).build();
    }
    
    //works
    @Path("/{ownerId}/properties")
    @POST
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

    //works
    @Path("/properties/{propertyId}")
    @PUT
    public Response updateProperty(@PathParam("propertyId") Long propertyId, Property property) {
        Optional<Property> updatedProperty = ownerService.updateProperty(propertyId, property);
        if (updatedProperty.isPresent()) {
            return Response.ok(updatedProperty.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Property not found").build();
        }
    }

    @Path("/properties/{propertyId}")
    @DELETE
    public Response deleteProperty(@PathParam("propertyId") Long id) {
        boolean isDeleted = ownerService.deleteProperty(id);
        if (isDeleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Property not found").build();
        }
    }

    //works
    @Path("/repairs")
    @POST
    public Response createRepair(Repair repair) {
        Optional<Repair> createdRepair = ownerService.createRepair(repair);
        if (createdRepair.isPresent()) {
            return Response.status(Response.Status.CREATED).entity(createdRepair.get()).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to create repair").build();
        }
    }
    
    //works
    @Path("/repairs/{repairId}")
    @PUT
    public Response updateRepair(@PathParam("repairId") Long repairId, Repair repair) {
        Optional<Repair> updatedRepair = ownerService.updateRepair(repairId, repair);
        if (updatedRepair.isPresent()) {
            return Response.ok(updatedRepair.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Repair not found").build();
        }
    }

    // works
    @Path("/repairs/{repairId}")
    @DELETE
    public Response deleteRepair(@PathParam("repairId") Long repairId) {
        boolean isDeleted = ownerService.deleteRepair(repairId);
        if (isDeleted) {
            return Response.noContent().build(); // 204 No Content
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Repair not found").build();
        }
    }
}

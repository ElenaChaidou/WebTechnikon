package gr.webtechnikon.resources;

import gr.webtechnikon.enums.RepairStatus;
import gr.webtechnikon.model.Owner;
import gr.webtechnikon.model.Property;
import gr.webtechnikon.model.Repair;
import gr.webtechnikon.services.AdminService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Path("/admin")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AdminResource {

    @Inject
    private AdminService adminService;

    @POST
    @Path("/active-repairs")
    public List<Repair> getAllRepairsOfTheDay() {
        LocalDate today = LocalDate.now();
        Logger.getLogger(AdminResource.class.getName()).log(Level.INFO, "Today is: " + today);
        try {
            return adminService.getAllRepairs().stream()
                    .filter(repair -> repair.getRepairStatus().equals(RepairStatus.IN_PROGRESS))
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            Logger.getLogger(AdminResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @POST
    @Path("/createOwners")
    public Response createOwner(Owner owner) {
        Optional<Owner> createdOwner = adminService.createOwner(owner);
        if (createdOwner.isPresent()) {
            return Response.status(Response.Status.CREATED).entity(createdOwner.get()).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to create owner").build();
        }
    }

    @PUT
    @Path("/updateOwner/{id}")
    public Response updateOwner(@PathParam("id") Long id, Owner owner) {
        if (owner.getOwnerId() != id) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Owner ID in the path does not match the ID in the request body").build();
        }

        Optional<Owner> updatedOwner = adminService.updateOwner(owner);
        if (updatedOwner.isPresent()) {
            return Response.ok(updatedOwner.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Owner with ID " + id + " not found").build();
        }
    }

//    @DELETE
//    @Path("/owners/{id}")
//    public Response deleteOwner(@PathParam("id") Long id) {
//        boolean deleted = adminService.deleteOwner(id);
//        if (deleted) {
//            return Response.ok("Owner deleted successfully").build();
//        } else {
//            return Response.status(Response.Status.NOT_FOUND)
//                    .entity("Owner with ID " + id + " not found").build();
//        }
//    }
    @GET
    @Path("/owners/{id}")
    public Response getOwnerById(@PathParam("id") Long id) {
        Optional<Owner> owner = adminService.searchOwnerById(id);
        return owner.map(value -> Response.ok(value).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).entity("Owner not found").build());
    }

    @GET
    @Path("/owners/vat/{vatNumber}")
    public Response getOwnerByVatNumber(@PathParam("vatNumber") Long vatNumber) {
        Optional<Owner> owner = adminService.searchOwnerByVatNumber(vatNumber);
        return owner.map(value -> Response.ok(value).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).entity("Owner not found").build());
    }

    @GET
    @Path("/owners/email/{email}")
    public Response getOwnerByEmail(@PathParam("email") String Email) {
        Optional<Owner> owner = adminService.searchOwnerByEmail(Email);
        return owner.map(value -> Response.ok(value).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).entity("Owner not found").build());
    }

    @POST
    @Path("/createProperties")
    public Response createProperty(Property property) {
        Optional<Property> createdProperty = adminService.createProperty(property);
        if (createdProperty.isPresent()) {
            return Response.status(Response.Status.CREATED).entity(createdProperty.get()).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to create property").build();
        }
    }

    @PUT
    @Path("/updateProperties/{id}")
    public Response updateProperty(@PathParam("id") Long id, Property property) {
        if (property.getPropertyId() != id) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Property ID in the path does not match the ID in the request body").build();
        }

        Optional<Property> updatedProperty = adminService.updateProperty(property);
        if (updatedProperty.isPresent()) {
            return Response.ok(updatedProperty.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Property with ID " + id + " not found").build();
        }
    }

//    @DELETE
//    @Path("/properties/{id}")
//    public Response deleteProperty(@PathParam("id") Long id) {
//        boolean deleted = adminService.deleteProperty(id);
//        if (deleted) {
//            return Response.ok("Property deleted successfully").build();
//        } else {
//            return Response.status(Response.Status.NOT_FOUND)
//                    .entity("Property with ID " + id + " not found").build();
//        }
//    }
//    @GET
//    @Path("/properties")
//    public Response getAllProperties() {
//        List<Property> properties = adminService.getAllProperties();
//        return Response.ok(properties).build();
//    }

    @GET
    @Path("/properties/vat/{vatNumber}")
    public Response getPropertiesByVatNumber(@PathParam("vatNumber") Long vatNumber) {
        List<Property> properties = adminService.searchPropertyByVatNumber(vatNumber);
        if (properties.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No properties found for VAT number: " + vatNumber).build();
        }
        return Response.ok(properties).build();
    }

    @GET
    @Path("/properties/id/{id}")
    public Response getPropertiesById(@PathParam("id") Long id) {
        Optional<Property> property = adminService.searchPropertyById(id);
        return property.map(value -> Response.ok(value).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).entity("Property not found").build());
    }

    @POST
    @Path("/createRepair")
    public Response createRepair(Repair repair) {
        Optional<Repair> createdRepair = adminService.createRepair(repair);
        if (createdRepair.isPresent()) {
            return Response.status(Response.Status.CREATED).entity(createdRepair.get()).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to create repair").build();
        }
    }

    @PUT
    @Path("/updateRepairs/{id}")
    public Response updateRepair(@PathParam("id") Long id, Repair repair) {
        if (repair.getRepairId() != id) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Repair ID in the path does not match the ID in the request body").build();
        }

        Optional<Repair> updatedRepair = adminService.updateRepair(repair);
        if (updatedRepair.isPresent()) {
            return Response.ok(updatedRepair.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Repair with ID " + id + " not found").build();
        }
    }

    @GET
    @Path("/repairs/{ownerId}")
    public Response getRepairsByUserId(@PathParam("ownerId") Long ownerId) {
        List<Repair> repair = adminService.getRepairsByOwnerId(ownerId);
        if (repair.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No repairs found for User id: " + ownerId).build();
        }
        return Response.ok(repair).build();
    }
    
    @GET
    @Path("/repairs/{date}")
    public Response getRepairsByDate(@PathParam("date") Date date) {
        List<Repair> repair = adminService.getRepairsByDate(date);
        if (repair.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No repairs found for Date: " + date).build();
        }
        return Response.ok(repair).build();
    }

//    @DELETE
//    @Path("/repairs/{id}")
//    public Response deleteRepair(@PathParam("id") Long id) {
//        boolean deleted = adminService.deleteRepair(id);
//        if (deleted) {
//            return Response.ok("Repair deleted successfully").build();
//        } else {
//            return Response.status(Response.Status.NOT_FOUND)
//                    .entity("Repair with ID " + id + " not found").build();
//        }
//    }
//
//    @GET
//    @Path("/repairs")
//    public Response getAllRepairs() {
//        List<Repair> repairs = adminService.getAllRepairs();
//        return Response.ok(repairs).build();
//    }
//    @GET
//    @Path("/repairs/pending")
//    public Response getPendingRepairs() {
//        List<Repair> pendingRepairs = adminService.getPendingRepairs();
//        return Response.ok(pendingRepairs).build();
//    }
}

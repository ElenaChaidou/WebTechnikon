package gr.webtechnikon.resources;

import gr.webtechnikon.enums.RepairStatus;
import gr.webtechnikon.model.Owner;
import gr.webtechnikon.model.Property;
import gr.webtechnikon.model.Repair;
import gr.webtechnikon.services.AdminService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Path("/admin")
@Slf4j
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AdminResource {

    @Inject
    private AdminService adminService;

    //works
    @Path("/active-repairs")
    @GET
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

    //works
    @Path("/createOwners")
    @POST
    public Response createOwner(Owner owner) {
        Optional<Owner> createdOwner = adminService.createOwner(owner);
        if (createdOwner.isPresent()) {
            return Response.status(Response.Status.CREATED).entity(createdOwner.get()).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to create owner").build();
        }
    }

    //works
    @Path("/updateOwner/{id}")
    @PUT

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

    //works
    @Path("/owner/{id}")
    @DELETE
    public Response deleteOwner(@PathParam("id") Long id) {
        boolean isDeleted = adminService.deleteOwner(id);
        if (isDeleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Owner not found").build();
        }
    }

    //works
    @Path("/owners/{id}")
    @GET
//    public Response getOwnerById(@PathParam("id") Long id) {
//        Optional<Owner> owner = adminService.searchOwnerById(id);
//        return owner.map(value -> Response.ok(value).build())
//                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).entity("Owner not found").build());
//    }
    public Response getOwnerById(@PathParam("id") Long id) {
        Optional<Owner> owner = adminService.searchOwnerById(id);
        if (owner.isPresent()) {
            return Response.ok(owner.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Owner not found").build();
        }
    }

    //works
    @Path("/owners/vat/{vatNumber}")
    @GET
    public Response getOwnerByVatNumber(@PathParam("vatNumber") Long vatNumber) {
        Optional<Owner> owner = adminService.searchOwnerByVatNumber(vatNumber);
        if (owner.isPresent()) {
            return Response.ok(owner.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Owner not found").build();
        }
    }

    //works
    @Path("/owners/email/{email}")
    @GET
    public Response getOwnerByEmail(@PathParam("email") String email) {
        Optional<Owner> owner = adminService.searchOwnerByEmail(email);
        return owner.map(value -> Response.ok(value).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).entity("Owner not found").build());
    }

    //works
    @Path("/{ownerId}/properties")
    @POST
    public Response createProperty(@PathParam("ownerId") Long ownerId, Property property) {

        Optional<Owner> ownerOpt = adminService.findOwnerById(ownerId);

        if (ownerOpt.isPresent()) {
            Owner owner = ownerOpt.get();

            Property newProperty = new Property(
                    property.getPropertyCode(),
                    property.getAddress(),
                    property.getYearOfConstruction(),
                    property.getPropertyType(),
                    owner
            );

            Optional<Property> createdProperty = adminService.createProperty(newProperty);
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
    @Path("/updateProperties/{id}")
    @PUT
    public Response updateProperty(@PathParam("propertyId") Long propertyId, Property property) {
        Optional<Property> updatedProperty = adminService.updateProperty(propertyId, property);
        if (updatedProperty.isPresent()) {
            return Response.ok(updatedProperty.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Property not found").build();
        }
    }

    @Path("/properties/{propertyId}")
    @DELETE
    public Response deleteProperty(@PathParam("propertyId") Long id) {
        boolean isDeleted = adminService.deleteProperty(id);
        if (isDeleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Property not found").build();
        }
    }
//    @GET
//    @Path("/properties")
//    public Response getAllProperties() {
//        List<Property> properties = adminService.getAllProperties();
//        return Response.ok(properties).build();
//    }

    //works
    @Path("/properties/vat/{vatNumber}")
    @GET
    public Response getPropertiesByVatNumber(@PathParam("vatNumber") Long vatNumber) {
        try {
            Optional<Owner> ownerOptional = adminService.searchOwnerByVatNumber(vatNumber);

            if (ownerOptional.isEmpty()) {
                log.warn("No owner found with VAT number: {}", vatNumber);
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No owner found with VAT number: " + vatNumber)
                        .build();
            }
            Owner owner = ownerOptional.get();
            Long ownerId = owner.getOwnerId();
            List<Property> properties = adminService.searchPropertyOwnerId(ownerId);
            if (properties.isEmpty()) {
                log.warn("No properties found for owner with VAT number: {}", vatNumber);
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No properties found for owner with VAT number: " + vatNumber)
                        .build();
            }
            return Response.ok(properties).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    //works
    @Path("/properties/id/{id}")
    @GET
    public Response getPropertiesById(@PathParam("id") Long id) {
        Optional<Property> property = adminService.searchPropertyById(id);
        return property.map(value -> Response.ok(value).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).entity("Property not found").build());
    }

    //works
    @Path("/createRepair")
    @POST
    public Response createRepair(Repair repair) {
        Optional<Repair> createdRepair = adminService.createRepair(repair);
        if (createdRepair.isPresent()) {
            return Response.status(Response.Status.CREATED).entity(createdRepair.get()).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to create repair").build();
        }
    }

    //works
    @Path("/updateRepairs/{id}")
    @PUT
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

    //works
    @Path("/repairsById/{ownerId}")
    @GET
    public Response getRepairsByUserId(@PathParam("ownerId") Long ownerId) {
        List<Repair> repairs = adminService.getRepairsByOwnerId(ownerId);
        if (repairs == null || repairs.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No repairs found for Owner ID: " + ownerId)
                    .build();
        }
        return Response.ok(repairs).build();
    }

    //works
    @Path("/repairs/{date}")
    @GET
    public Response getRepairsByDate(@PathParam("date") String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate = dateFormat.parse(date);

            List<Repair> repairs = adminService.getRepairsByDate(parsedDate);
            if (repairs.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No repairs found for Date: " + date)
                        .build();
            }
            return Response.ok(repairs).build();
        } catch (ParseException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid date format. Use yyyy-MM-dd.")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred: " + e.getMessage())
                    .build();
        }
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

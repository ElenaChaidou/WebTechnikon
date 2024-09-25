//package gr.webtechnikon.resources;
//
//import gr.webtechnikon.model.Owner;
//import gr.webtechnikon.services.AdminService;
//import gr.webtechnikon.services.OwnerService;
//import gr.webtechnikon.services.OwnerServiceInterface;
//import jakarta.inject.Inject;
//import jakarta.ws.rs.*;
//import jakarta.ws.rs.core.MediaType;
//import jakarta.ws.rs.core.Response;
//
//import java.util.List;
//import java.util.Optional;
//@Path("/admins")
//@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_JSON)
//public class AdminResource {
//
//    @Inject
//    AdminService adminService;
//
//    @GET
//    public List<Admin> getAllAdmins() {
//        return adminService.findAllAdmins();
//    }
//
//    @POST
//    public Response createAdmin(Admin admin) {
//        adminService.createAdmin(admin);
//        return Response.status(Response.Status.CREATED).entity(admin).build();
//    }
//
//    @PUT
//    @Path("/{id}")
//    public Response updateAdmin(@PathParam("id") Long id, Admin admin) {
//        adminService.updateAdmin(id, admin);
//        return Response.ok(admin).build();
//    }
//
//    @DELETE
//    @Path("/{id}")
//    public Response deleteAdmin(@PathParam("id") Long id) {
//        adminService.softDeleteAdmin(id);
//        return Response.noContent().build();
//    }
//}

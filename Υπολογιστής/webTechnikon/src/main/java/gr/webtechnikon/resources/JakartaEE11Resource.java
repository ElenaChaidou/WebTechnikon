package gr.webtechnikon.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("jen")
public class JakartaEE11Resource {
    
    @GET
    public Response ping(){
        return Response
                .ok("ping Jakarta EE")
                .build();
    }
}

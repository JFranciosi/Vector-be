package com.vector.resource;

import com.vector.dto.request.login.LoginRequest;
import com.vector.dto.request.register.RegisterRequest;
import com.vector.dto.response.auth.AuthResponse;
import com.vector.service.AuthService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.annotation.security.PermitAll;

@Path("/api/v1/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@PermitAll
public class AuthResource {

    private final AuthService authService;

    public AuthResource(AuthService authService) {
        this.authService = authService;
    }

    @POST
    @Path("/register")
    public Response register(RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @POST
    @Path("/login")
    @PermitAll
    public Response login(LoginRequest request) {
        AuthResponse response = authService.login(request);
        return Response.ok(response).build();
    }

    @POST
    @Path("/logout")
    public Response logout() {
        authService.logout();
        return Response.noContent().build();
    }
}


package com.vector.resource.health;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.sql.Connection;
import java.util.Map;
import javax.sql.DataSource;

@Path("/health/db")
public class HealthResource {

    @Inject
    DataSource ds;

    @GET
    public Response check() {
        try (Connection connection = ds.getConnection()) {

            if (connection.isValid(2)) {
                return Response.ok(Map.of("status", "UP", "database", "connected")).build();
            }

        } catch (Exception e) {
            return Response.serverError()
                    .entity(Map.of("status", "DOWN", "error", e.getMessage()))
                    .build();
        }

        return Response.serverError()
                .entity(Map.of("status", "DOWN", "database", "disconnected"))
                .build();
    }
}
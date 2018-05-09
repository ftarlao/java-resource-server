/*
 * Copyright (C) 2016 Authlete, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the
 * License.
 */
package com.authlete.jaxrs.server.api;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import com.authlete.common.api.AuthleteApiFactory;
import com.authlete.jaxrs.BaseResourceEndpoint;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * An endpoint that returns a web page information.
 *
 *
 * @author Fabiano Tarlao
 */
@Path("/api/app")
public class PageEndpoint extends BaseResourceEndpoint {

    /**
     * JSON generator.
     */
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    public Response get(
            @HeaderParam(HttpHeaders.AUTHORIZATION) String authorization,
            @QueryParam("access_token") String accessToken) {
        // Extract an access token from either the Authorization header or
        // the request parameters. The Authorization header takes precedence.
        // See RFC 6750 (Bearer Token Usage) about the standard ways to accept
        // an access token from a client application.
        String token = extractAccessToken(authorization, accessToken);

        return process(token);
    }

    private Response process(String accessToken) {
        // Validate the access token. Because this endpoint does not require
        // any scopes, here we use the simplest variant of validateAccessToken()
        // methods which does not take 'requiredScopes' argument. See the JavaDoc
        // of BaseResourceEndpoint (authlete-java-jaxrs) for details.
        //
        validateAccessToken(AuthleteApiFactory.getDefaultApi(), accessToken);

        // The access token presented by the client application is valid.
        // Return the requested resource.
        return getResource();
    }

    private Response getResource() {

        // Convert the data to JSON.
        String page = "<!DOCTYPE html>\n"
                + "<html>\n"
                + "    <head>\n"
                + "        <title>Protected page or Service</title>\n"
                + "        <meta charset=\"UTF-8\">\n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    </head>\n"
                + "    <body style=\"background-color:#E6E6FA\">\n"
                + "        <h1>WAITING</h1> \n"
                + "    </body>\n"
                + "</html>";

        // Create a response of "200 OK".
        return Response.ok(page, "text/html; charset=UTF-8").build();
    }

    
}

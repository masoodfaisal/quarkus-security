package com.redhat.hackfest;

import io.quarkus.security.identity.SecurityIdentity;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.metrics.annotation.Counted;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/helloworld")
public class HelloWorldResource {

    @Inject
    SecurityIdentity securityIdentity;


    @ConfigProperty(name = "a-private-key")
    String privateKey;

    @GET
    @RolesAllowed("user")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "hello " + securityIdentity.getPrincipal().getName() +
                ".\nThe pass fetched from database is " + privateKey;
    }

    @GET
    @Path("/getaccountinfo")
    @RolesAllowed("user")
    @Produces(MediaType.TEXT_PLAIN)
    public String getAccountInfo() {
        var accountNotFound = true;
        if (accountNotFound) {
            return accountNotFound();
        }
        return "account exists";

    }

    //a business function
    @Counted(name = "invalidaccount", description = "Account not found")
    public String accountNotFound() {
        return "Invalid Account";
    }
}




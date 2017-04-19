/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import controller.BillController;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Georgi
 */
@Path("v2")
public class GraphData {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GraphData
     */
    public GraphData() {
    }

    /**
     * Retrieves representation of an instance of webservice.GraphData
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public String getJson(@PathParam("id") int id) {
        BillController bc = new BillController();
        return bc.getMonthlyExpense(id);
    }

    /**
     * PUT method for updating or creating an instance of GraphData
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}

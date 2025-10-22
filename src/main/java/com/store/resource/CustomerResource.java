package com.store.resource;

import com.store.entity.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    @PersistenceContext(unitName = "storePU")
    private EntityManager em;

    @GET
    public List<Customer> getAllCustomers() {
        return em.createQuery("SELECT c FROM Customer c", Customer.class)
                .getResultList();
    }

    @GET
    @Path("/{id}")
    public Response getCustomerById(@PathParam("id") Long id) {
        Customer customer = em.find(Customer.class, id);
        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Customer not found with id: " + id)
                    .build();
        }
        return Response.ok(customer).build();
    }

    @POST
    @Transactional
    public Response createCustomer(Customer customer) {
        try {
            em.persist(customer);
            return Response.status(Response.Status.CREATED).entity(customer).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error creating customer: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateCustomer(@PathParam("id") Long id, Customer updatedCustomer) {
        Customer existingCustomer = em.find(Customer.class, id);
        if (existingCustomer == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Customer not found with id: " + id)
                    .build();
        }

        existingCustomer.setName(updatedCustomer.getName());
        existingCustomer.setEmail(updatedCustomer.getEmail());

        em.merge(existingCustomer);
        return Response.ok(existingCustomer).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteCustomer(@PathParam("id") Long id) {
        Customer customer = em.find(Customer.class, id);
        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Customer not found with id: " + id)
                    .build();
        }

        em.remove(customer);
        return Response.noContent().build();
    }
}
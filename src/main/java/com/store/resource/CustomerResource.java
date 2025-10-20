package com.store.resource;

import com.store.entity.Customer;
import com.store.service.CustomerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    @Inject
    private CustomerService customerService;

    @GET
    public List<Customer> getAll() {
        return customerService.getAllCustomers();
    }

    @GET
    @Path("/{id}")
    public Customer getById(@PathParam("id") Long id) {
        return customerService.getCustomerById(id);
    }

    @POST
    public void create(Customer customer) {
        customerService.createCustomer(customer);
    }

    @PUT
    public void update(Customer customer) {
        customerService.updateCustomer(customer);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        customerService.deleteCustomer(id);
    }
}

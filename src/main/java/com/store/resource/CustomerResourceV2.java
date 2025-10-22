package com.store.resource;

import com.store.dto.CustomerDTO;
import com.store.entity.Customer;
import com.store.mapper.CustomerMapper;
import com.store.service.CustomerService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/v2/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResourceV2 {

    @Inject
    CustomerService customerService;

    @Inject
    CustomerMapper customerMapper;

    @GET
    public Response getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers()
                .stream()
                .map(customerMapper::toDTO)
                .collect(Collectors.toList());
        return Response.ok(customers).build();
    }

    @GET
    @Path("/{id}")
    public Response getCustomerById(@PathParam("id") Long id) {
        return customerService.getCustomerById(id)
                .map(customerMapper::toDTO)
                .map(customerDTO -> Response.ok(customerDTO).build())
                .orElse(Response.status(Response.Status.NOT_FOUND)
                        .entity("Customer not found with id: " + id)
                        .build());
    }

    @POST
    public Response createCustomer(@Valid CustomerDTO customerDTO) {
        try {
            Customer customer = customerMapper.toEntity(customerDTO);
            Customer createdCustomer = customerService.createCustomer(customer);
            CustomerDTO createdDTO = customerMapper.toDTO(createdCustomer);
            return Response.status(Response.Status.CREATED).entity(createdDTO).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateCustomer(@PathParam("id") Long id, @Valid CustomerDTO customerDTO) {
        Customer customer = customerMapper.toEntity(customerDTO);
        return customerService.updateCustomer(id, customer)
                .map(customerMapper::toDTO)
                .map(updatedDTO -> Response.ok(updatedDTO).build())
                .orElse(Response.status(Response.Status.NOT_FOUND)
                        .entity("Customer not found with id: " + id)
                        .build());
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") Long id) {
        if (customerService.deleteCustomer(id)) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Customer not found with id: " + id)
                    .build();
        }
    }
}
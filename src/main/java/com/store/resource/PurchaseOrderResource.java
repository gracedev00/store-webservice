package com.store.resource;

import com.store.entity.PurchaseOrder;
import com.store.entity.OrderStatus;
import com.store.service.PurchaseOrderService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PurchaseOrderResource {

    @Inject
    PurchaseOrderService orderService;

    @GET
    public Response getAllOrders() {
        List<PurchaseOrder> orders = orderService.getAllOrders();
        return Response.ok(orders).build();
    }

    @GET
    @Path("/{id}")
    public Response getOrderById(@PathParam("id") Long id) {
        return orderService.getOrderById(id)
                .map(order -> Response.ok(order).build())
                .orElse(Response.status(Response.Status.NOT_FOUND)
                        .entity("Order not found with id: " + id)
                        .build());
    }

    @POST
    public Response createOrder(PurchaseOrder order) {
        try {
            PurchaseOrder createdOrder = orderService.createOrder(order);
            return Response.status(Response.Status.CREATED).entity(createdOrder).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}/status")
    public Response updateOrderStatus(@PathParam("id") Long id,
                                      @QueryParam("status") OrderStatus status) {
        return orderService.updateOrderStatus(id, status)
                .map(order -> Response.ok(order).build())
                .orElse(Response.status(Response.Status.NOT_FOUND)
                        .entity("Order not found with id: " + id)
                        .build());
    }

    @GET
    @Path("/customer/{customerId}")
    public Response getOrdersByCustomer(@PathParam("customerId") Long customerId) {
        List<PurchaseOrder> orders = orderService.getOrdersByCustomer(customerId);
        return Response.ok(orders).build();
    }

    @GET
    @Path("/status/{status}")
    public Response getOrdersByStatus(@PathParam("status") OrderStatus status) {
        List<PurchaseOrder> orders = orderService.getOrdersByStatus(status);
        return Response.ok(orders).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteOrder(@PathParam("id") Long id) {
        if (orderService.deleteOrder(id)) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Order not found with id: " + id)
                    .build();
        }
    }
}
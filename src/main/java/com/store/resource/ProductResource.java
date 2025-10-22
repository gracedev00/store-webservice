package com.store.resource;

import com.store.entity.Product;
import com.store.util.JPAUtil;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @GET
    public Response getAllProducts() {
        try {
            List<Product> products = JPAUtil.executeInTransactionWithResult(em -> {
                return em.createQuery("SELECT p FROM Product p", Product.class)
                        .getResultList();
            });
            return Response.ok(products).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    @POST
    public Response createProduct(Product product) {
        try {
            JPAUtil.executeInTransaction(em -> {
                em.persist(product);
            });
            return Response.status(Response.Status.CREATED).entity(product).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Error creating product: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getProductById(@PathParam("id") Long id) {
        try {
            Product product = JPAUtil.executeInTransactionWithResult(em -> {
                return em.find(Product.class, id);
            });

            if (product == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"Product not found with id: " + id + "\"}")
                        .build();
            }
            return Response.ok(product).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}")
                    .build();
        }
    }
}
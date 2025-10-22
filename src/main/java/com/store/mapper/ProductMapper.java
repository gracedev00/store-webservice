package com.store.mapper;

import com.store.dto.ProductDTO;
import com.store.entity.Product;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductMapper {

    public ProductDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }

        ProductDTO dto = new ProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice()
        );

        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
            dto.setCategoryName(product.getCategory().getName());
        }

        return dto;
    }

    public Product toEntity(ProductDTO productDTO) {
        if (productDTO == null) {
            return null;
        }

        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());

        // Note: La catégorie doit être gérée séparément via le service
        return product;
    }
}
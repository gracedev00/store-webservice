package com.store.service;

import com.store.entity.Product;
import com.store.repository.ProductRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProductService {

    @Inject
    ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        // Validation métier
        if (product.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }

        return productRepository.save(product);
    }

    public Optional<Product> updateProduct(Long id, Product productDetails) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setName(productDetails.getName());
                    existingProduct.setPrice(productDetails.getPrice());
                    return productRepository.save(existingProduct);
                });
    }

    public boolean deleteProduct(Long id) {
        if (productRepository.findById(id).isPresent()) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Product> searchProductsByName(String name) {
        return productRepository.findByName(name);
    }
}
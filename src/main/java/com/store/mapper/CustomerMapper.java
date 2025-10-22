package com.store.mapper;

import com.store.dto.CustomerDTO;
import com.store.entity.Customer;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CustomerMapper {

    public CustomerDTO toDTO(Customer customer) {
        if (customer == null) {
            return null;
        }
        return new CustomerDTO(
                customer.getId(),
                customer.getName(),
                customer.getEmail()
        );
    }

    public Customer toEntity(CustomerDTO customerDTO) {
        if (customerDTO == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setId(customerDTO.getId());
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        return customer;
    }
}
package com.store.mapper;

import com.store.dto.CustomerDTO;
import com.store.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {

    private CustomerMapper customerMapper;

    @BeforeEach
    void setUp() {
        customerMapper = new CustomerMapper();
    }

    @Test
    void toDTO_WithValidCustomer_ShouldReturnCustomerDTO() {
        // Arrange
        Customer customer = new Customer("John Doe", "john.doe@email.com");
        customer.setId(1L);

        // Act
        CustomerDTO dto = customerMapper.toDTO(customer);

        // Assert
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("John Doe", dto.getName());
        assertEquals("john.doe@email.com", dto.getEmail());
    }

    @Test
    void toDTO_WithNullCustomer_ShouldReturnNull() {
        // Act
        CustomerDTO dto = customerMapper.toDTO(null);

        // Assert
        assertNull(dto);
    }

    @Test
    void toEntity_WithValidCustomerDTO_ShouldReturnCustomer() {
        // Arrange
        CustomerDTO dto = new CustomerDTO(1L, "John Doe", "john.doe@email.com");

        // Act
        Customer customer = customerMapper.toEntity(dto);

        // Assert
        assertNotNull(customer);
        assertEquals(1L, customer.getId());
        assertEquals("John Doe", customer.getName());
        assertEquals("john.doe@email.com", customer.getEmail());
    }

    @Test
    void toEntity_WithNullCustomerDTO_ShouldReturnNull() {
        // Act
        Customer customer = customerMapper.toEntity(null);

        // Assert
        assertNull(customer);
    }
}
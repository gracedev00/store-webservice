package com.store.service;

import com.store.entity.Customer;
import com.store.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer1;
    private Customer customer2;

    @BeforeEach
    void setUp() {
        customer1 = new Customer("John Doe", "john.doe@email.com");
        customer1.setId(1L);

        customer2 = new Customer("Jane Smith", "jane.smith@email.com");
        customer2.setId(2L);
    }

    @Test
    void getAllCustomers_ShouldReturnAllCustomers() {
        // Arrange
        List<Customer> expectedCustomers = Arrays.asList(customer1, customer2);
        when(customerRepository.findAll()).thenReturn(expectedCustomers);

        // Act
        List<Customer> actualCustomers = customerService.getAllCustomers();

        // Assert
        assertEquals(2, actualCustomers.size());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void getCustomerById_WithValidId_ShouldReturnCustomer() {
        // Arrange
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer1));

        // Act
        Optional<Customer> result = customerService.getCustomerById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    void getCustomerById_WithInvalidId_ShouldReturnEmpty() {
        // Arrange
        when(customerRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Optional<Customer> result = customerService.getCustomerById(99L);

        // Assert
        assertFalse(result.isPresent());
        verify(customerRepository, times(1)).findById(99L);
    }

    @Test
    void createCustomer_WithValidData_ShouldReturnCreatedCustomer() {
        // Arrange
        Customer newCustomer = new Customer("New Customer", "new@email.com");
        when(customerRepository.findByEmail("new@email.com")).thenReturn(Optional.empty());
        when(customerRepository.save(any(Customer.class))).thenReturn(newCustomer);

        // Act
        Customer result = customerService.createCustomer(newCustomer);

        // Assert
        assertNotNull(result);
        assertEquals("New Customer", result.getName());
        verify(customerRepository, times(1)).findByEmail("new@email.com");
        verify(customerRepository, times(1)).save(newCustomer);
    }

    @Test
    void createCustomer_WithDuplicateEmail_ShouldThrowException() {
        // Arrange
        Customer duplicateCustomer = new Customer("Duplicate", "john.doe@email.com");
        when(customerRepository.findByEmail("john.doe@email.com")).thenReturn(Optional.of(customer1));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> customerService.createCustomer(duplicateCustomer));

        assertEquals("Email already exists: john.doe@email.com", exception.getMessage());
        verify(customerRepository, times(1)).findByEmail("john.doe@email.com");
        verify(customerRepository, never()).save(any());
    }

    @Test
    void deleteCustomer_WithValidId_ShouldReturnTrue() {
        // Arrange
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer1));
        doNothing().when(customerRepository).deleteById(1L);

        // Act
        boolean result = customerService.deleteCustomer(1L);

        // Assert
        assertTrue(result);
        verify(customerRepository, times(1)).findById(1L);
        verify(customerRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteCustomer_WithInvalidId_ShouldReturnFalse() {
        // Arrange
        when(customerRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        boolean result = customerService.deleteCustomer(99L);

        // Assert
        assertFalse(result);
        verify(customerRepository, times(1)).findById(99L);
        verify(customerRepository, never()).deleteById(any());
    }

    @Test
    void updateCustomer_WithValidData_ShouldReturnUpdatedCustomer() {
        // Arrange
        Customer updatedCustomer = new Customer("Updated Name", "updated@email.com");
        updatedCustomer.setId(1L);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer1));
        when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);

        // Act
        Optional<Customer> result = customerService.updateCustomer(1L, updatedCustomer);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Updated Name", result.get().getName());
        verify(customerRepository, times(1)).findById(1L);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }
}
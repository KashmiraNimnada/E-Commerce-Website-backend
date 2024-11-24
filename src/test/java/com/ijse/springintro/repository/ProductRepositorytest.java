package com.ijse.springintro.repository;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ijse.springintro.entity.Product;

@ExtendWith(MockitoExtension.class)
public class ProductRepositorytest {
    
    @Mock
    ProductRepository productRepository;

    @Test
    void saveShouldSave() {

        Product product = new Product();
        product.setId(1L);
        product.setName("Kohomba saban");
        product.setDescription("50g soap");
        product.setPrice(100.0);

        when(productRepository.save(product)).thenReturn(product);

        Product createdProduct = productRepository.save(product);

        Assertions.assertEquals(product, createdProduct);
        Assertions.assertTrue(createdProduct.getName()=="Kohomba saban");
    }

    @Test
    void findByIdShouldfindById() {

        Product product = new Product();
        product.setId(1L);
        product.setName("Kohomba saban");
        
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productRepository.findById(1L).orElse(null);

        Assertions.assertEquals(product, result);
        Assertions.assertTrue(result.getName()=="Kohomba saban");
    }

    @Test
    void findAllShouldFindAll() {

        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Shampoo");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("basmathi rice");

        List<Product> products = Arrays.asList(product1,product2);
        
        when(productRepository.findAll()).thenReturn(products);

        List<Product> productList = productRepository.findAll();

        Assertions.assertEquals(products, productList);
        Assertions.assertTrue(productList.get(0).getName()=="Shampoo");
    }

    @Test
    void deleteByIdShouldDeleteById() {

        Product product = new Product();
        product.setId(1L);
        product.setName("Saban");

        when(productRepository.save(product)).thenReturn(product);
        productRepository.save(product);
        doNothing().when(productRepository).deleteById(1L);

        productRepository.deleteById(1L);
    }

}

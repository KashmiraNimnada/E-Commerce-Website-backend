package com.ijse.springintro.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import com.ijse.springintro.entity.Category;
import com.ijse.springintro.entity.Product;
import com.ijse.springintro.repository.ProductRepository;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    
    @Mock
    ProductRepository productRepository;
    
    @InjectMocks
    ProductServiceImpl productService;

    @Test
    void createProductShouldCreateProductSuccessfully() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Saban");
        product.setPrice(1000.0);
        product.setDescription("50g saban");
        Category category = new Category();
        category.setId(1L);
        category.setName("Washing items");
        product.setCategory(category);
        Mockito.when(productRepository.save(product)).thenReturn(product);
        Product addedProduct = productService.createProduct(product);

        Assertions.assertNotNull(addedProduct);
        Assertions.assertEquals(product.getName(), addedProduct.getName());
        Assertions.assertTrue(addedProduct.getName()=="Saban");
        Assertions.assertTrue(addedProduct.getDescription()=="50g saban");
        Assertions.assertEquals(product.getCategory(), addedProduct.getCategory());
        Assertions.assertTrue(addedProduct.getId()==1L);
    }

    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        sampleProduct = new Product();
        sampleProduct.setId(1L);
        sampleProduct.setName("atlas book");
        sampleProduct.setDescription("80 pages book");
        Category category = new Category();
        category.setId(1L);
        category.setName("Books");
        sampleProduct.setCategory(category);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProductByIdShouldGetProductByIdSuccessfully() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));
        Product result = productService.getProductById(1L);
        Assertions.assertEquals(sampleProduct, result);
        Assertions.assertTrue(result.getDescription()=="80 pages book");
        Assertions.assertTrue(result.getName()=="atlas book");
        Assertions.assertTrue(result.getCategory().getId()==1L);
    }

    @Test
    void getAllProductsShouldGetAllProducts() {

        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Banana");
        product1.setDescription("Banana 1kg");
        
        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("iPhone");
        product2.setDescription("iPhone 12");

        List<Product> mockProducts = Arrays.asList(product1,product2);

        when(productRepository.findAll()).thenReturn(mockProducts);

        List<Product> results = productService.getAllProducts();

        Assertions.assertEquals("iPhone", results.get(1).getName());
        Assertions.assertEquals("iPhone 12", results.get(1).getDescription());
        Assertions.assertEquals("Banana", results.get(0).getName());
        Assertions.assertEquals("Banana 1kg", results.get(0).getDescription());
    }

    @Test
    void updateProductShouldUpdateProduct() {

        Product existingProduct = new Product();

        existingProduct.setId(1L);
        existingProduct.setName("Polo shirt");
        Category category = new Category();
        category.setId(1L);
        category.setName("Clothes");
        existingProduct.setCategory(category);
        existingProduct.setDescription("Polo 16 shirt");
        existingProduct.setPrice(5000.0);

        Product updatedProduct = new Product();
        
        updatedProduct.setName("Denim shirt");
        updatedProduct.setDescription("Denim 16 shirt");
        updatedProduct.setPrice(6000.0);
        updatedProduct.setCategory(category);

        when(productRepository.findById(existingProduct.getId())).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        Product result = productService.updateProduct(existingProduct.getId(),updatedProduct);

        Assertions.assertTrue(updatedProduct.getName()=="Denim shirt");
        Assertions.assertTrue(result.getName()=="Denim shirt");        
        Assertions.assertTrue(result.getPrice()==6000.0);
    }

    @Test
    void deleteProductShouldDeleteProduct() {

        Product product = new Product();
        product.setId(1L);
        product.setName("Ice cream");
        product.setDescription("Ice cream 1L");
        product.setPrice(500.0);
        Category category = new Category();
        category.setId(1L);
        category.setName("Food");
        product.setCategory(category);

        when(productRepository.save(product)).thenReturn(product);
        productRepository.save(product);
        doNothing().when(productRepository).deleteById(product.getId());

        productService.deleteProduct(product.getId());

    }

}

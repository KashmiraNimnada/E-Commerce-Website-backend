package com.ijse.springintro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ijse.springintro.dto.ProductReqDto;
import com.ijse.springintro.entity.Category;
import com.ijse.springintro.entity.Product;
import com.ijse.springintro.service.CategoryService;
import com.ijse.springintro.service.ProductService;

@RestController
@CrossOrigin (origins = "*")
public class ProductController {
    
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        System.out.println("Get products");
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.status(200).body(products);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody ProductReqDto productReqDto){
        Product product = new Product();
        product.setName(productReqDto.getName());
        product.setPrice(productReqDto.getPrice());
        product.setDescription(productReqDto.getDescription());
        Category category = categoryService.getCategoryById(productReqDto.getCategoryId());
        product.setCategory(category);

        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.status(201).body(createdProduct);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,@RequestBody ProductReqDto productReqDto){
        Product product = new Product();
        product.setName(productReqDto.getName());
        product.setPrice(productReqDto.getPrice());
        product.setDescription(productReqDto.getDescription());
        Category category = categoryService.getCategoryById(productReqDto.getCategoryId());
        product.setCategory(category);
        Product updatedProduct = productService.updateProduct(id, product);
        
        if(updatedProduct==null){
            return ResponseEntity.status(404).body(null);
        }else{
            return ResponseEntity.status(200).body(updatedProduct);
        }
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        System.out.println("Delete called");
        productService.deleteProduct(id);
        System.out.println("Done deleting");
        return ResponseEntity.status(200).body("Product Deleted");
    }

}

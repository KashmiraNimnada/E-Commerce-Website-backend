package com.ijse.springintro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ijse.springintro.entity.Product;
import com.ijse.springintro.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{
    
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(Product product){
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long id){
        return productRepository.findById(id).orElse(null);              
    }

    @Override
    public Product updateProduct(Long id,Product product){
        Product getProduct = productRepository.findById(id).orElse(null);
        if(getProduct==null){
            return null;
        }else{
            getProduct.setName(product.getName());
            getProduct.setPrice(product.getPrice());
            getProduct.setDescription(product.getDescription());
            getProduct.setCategory(product.getCategory());
            return productRepository.save(getProduct);
        }
    }

    @Override
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

}

package com.ijse.springintro.repository;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ijse.springintro.entity.Category;

@ExtendWith(MockitoExtension.class)
public class CategoryRepositroyTest {
    
    @Mock
    CategoryRepository categoryRepository;

    @Test
    void saveShouldSave() {

        Category category = new Category();
        category.setId(1L);
        category.setName("Clothes");

        when(categoryRepository.save(category)).thenReturn(category);

        Category createdCategory = categoryRepository.save(category);
        Assertions.assertEquals(category, createdCategory);
        Assertions.assertTrue(createdCategory.getName()=="Clothes");
    }
    
    @Test
    void findByIdShouldfindById() {

        Category category = new Category();
        category.setId(1L);
        category.setName("Clothes");
        
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Category gotCategory = categoryRepository.findById(1L).orElse(null);
        Assertions.assertEquals(category, gotCategory);
        Assertions.assertTrue(gotCategory.getName()=="Clothes");
    }   

    @Test
    void findAllShouldFindAll() {

        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Clothes");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Books");

        List<Category> categories = Arrays.asList(category1,category2);
        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> categoryList = categoryRepository.findAll();
        Assertions.assertEquals(categories, categoryList);
        Assertions.assertTrue(categoryList.get(0).getName()=="Clothes");
    }

}

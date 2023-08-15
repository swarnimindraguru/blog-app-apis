package com.swarnim.blog.controllers;

import com.swarnim.blog.payloads.ApiResponse;
import com.swarnim.blog.payloads.CategoryDto;
import com.swarnim.blog.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

// CREATE
    @PostMapping("/createCategory")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);
    }

    // UPDATE
    @PutMapping("/updateCategory/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer catId){
        CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto, catId);
        return ResponseEntity.ok(updateCategory);
    }

    // DELETE
    @DeleteMapping("/deleteCategory/{catId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId){
        this.categoryService.deleteCategory(catId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted Successfully !!", true), HttpStatus.OK);
    }
    // GET
    @GetMapping("/getCategory/{catId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer catId){
        CategoryDto categoryDto = this.categoryService.getCategory(catId);
        return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
    }

    // GET ALL
    @GetMapping("/getAllCategories")
    public ResponseEntity<List<CategoryDto>> getCategories(){
       List<CategoryDto> categories=  this.categoryService.getCategories();
       return ResponseEntity.ok(categories);
    }
}

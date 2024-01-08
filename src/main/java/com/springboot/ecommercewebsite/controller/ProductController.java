package com.springboot.ecommercewebsite.controller;

import com.springboot.ecommercewebsite.exception.ProductException;
import com.springboot.ecommercewebsite.model.Product;
import com.springboot.ecommercewebsite.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> findProductByCategoryHandler(
            @RequestParam String category, @RequestParam List<String> color,
            @RequestParam List<String> memories, @RequestParam Integer minPrice,
            @RequestParam Integer maxPrice, @RequestParam Integer minDiscount,
            @RequestParam String sort, @RequestParam String stock,
            @RequestParam Integer pageNumber, @RequestParam Integer pageSize
            ){
        Page<Product> res = productService.getAllProduct(category,color,memories,minPrice,
                maxPrice,minDiscount,sort,stock,pageNumber,pageSize);

        System.out.println("Complete find all products");

        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @GetMapping("/products/id/{productId}")
    public ResponseEntity<Product> findProductByIdHandler(@PathVariable Long productId) throws ProductException{
        Product product = productService.findProductById(productId);

        return new ResponseEntity<Product>(product, HttpStatus.ACCEPTED);
    }

    @PostMapping("/products/search")
    public ResponseEntity<Page<Product>> searchProductHandler(@RequestBody Product product) throws ProductException{
//        List<Product> products= productService.searchProduct(productName);
//        List<Product> products= productService.findProductByName(productName);
        System.out.println(product + "======");
        Page<Product> res = productService.findProductByName(product.getTitle());
        System.out.println(product.getTitle());

        return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
    }
}

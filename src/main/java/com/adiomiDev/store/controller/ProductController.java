package com.adiomiDev.store.controller;


import com.adiomiDev.store.dto.CreateProductDto;
import com.adiomiDev.store.dto.ProductDto;
import com.adiomiDev.store.entity.Product;
import com.adiomiDev.store.exception.NotFoundException;
import com.adiomiDev.store.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.List;


@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() throws NotFoundException {
        return productService.findAll();

    }


    @GetMapping("/{id}")
    public Product getProductById(@PathVariable("id") long id) throws NotFoundException {
        return productService.findById(id);

    }


    @GetMapping("/sortAsc/{field}")
    public List<Product> getProductWithSort(@PathVariable String field) throws NotFoundException {
        return productService.sortProductAsc(field);
    }

    @GetMapping("/sortDesc/{field}")
    public List<Product> getProductWithSortDesc(@PathVariable String field) throws NotFoundException {
        return productService.sortProductDesc(field);

    }

    @GetMapping("/pagination/{offSet}/{pageSize}")
    public Page<Product> getProductWithPagination(@PathVariable int offSet, @PathVariable int pageSize) throws NotFoundException {
        return productService.sortProductPagination(offSet, pageSize);

    }

    @PostMapping
    public ProductDto addProduct(@RequestBody  CreateProductDto product) throws ValidationException {
        return productService.create(product);
    }

    @PutMapping("/{id}")
    public ProductDto updateProduct(@PathVariable("id") long id, @RequestBody CreateProductDto product) throws ValidationException {
        return productService.update(id, product);
    }

    @DeleteMapping("/{id}")
    public ProductDto deleteProduct(@PathVariable("id") long id) throws ValidationException {
        return productService.delete(id);
    }


}

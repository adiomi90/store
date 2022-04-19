package com.adiomiDev.store.controller;


import com.adiomiDev.store.dto.ProductDto;
import com.adiomiDev.store.entity.Product;
import com.adiomiDev.store.exception.ApiException;

import com.adiomiDev.store.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.xml.bind.ValidationException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllProducts() throws ValidationException {
        try {
            List<Product> products = productService.findAll();
            return new ResponseEntity<>(products,HttpStatus.OK);
        } catch (Exception e) {
            ApiException exception = new ApiException(e.getMessage(),HttpStatus.BAD_REQUEST,ZonedDateTime.now());
            return new ResponseEntity<>(exception,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable("id") long id) {
        try {
             return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            ApiException exception = new ApiException(e.getMessage(), HttpStatus.NOT_FOUND, ZonedDateTime.now());
            return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/sortAsc/{field}")
    public ResponseEntity<Object> getProductWithSort(@PathVariable String field) {
        try {
            List<Product> products = productService.sortProductAsc(field);
            return ResponseEntity.status(HttpStatus.OK).body(products);
        } catch (Exception e) {
            ApiException exception = new ApiException(e.getMessage(),HttpStatus.BAD_REQUEST,ZonedDateTime.now());
            return new ResponseEntity<>(exception,HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/sortDesc/{field}")
    public ResponseEntity<Object> getProductWithSortDesc(@PathVariable String field) {
        try {
            List<Product> products = productService.sortProductDesc(field);
            return ResponseEntity.status(HttpStatus.OK).body(products);

        } catch (Exception e) {
            ApiException exception = new ApiException(e.getMessage(),HttpStatus.BAD_REQUEST,ZonedDateTime.now());
            return new ResponseEntity<>(exception,HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/pagination/{offSet}/{pageSize}")
    public ResponseEntity<Object> getProductWithPagination(@PathVariable int offSet, @PathVariable int pageSize) {
        try {
            Page<Product> products = productService.sortProductPagination(offSet, pageSize);
            return ResponseEntity.status(HttpStatus.OK).body(products);

        } catch (Exception e) {
            ApiException exception = new ApiException(e.getMessage(),HttpStatus.BAD_REQUEST,ZonedDateTime.now());
            return new ResponseEntity<>(exception,HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping
    public ResponseEntity<Object> addProduct(@RequestBody Product product) {
        try {
            var addProduct = productService.save(product);
            ProductDto productDto = new ProductDto();
            productDto.setId(productDto.getId());
            return new ResponseEntity<>(addProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            ApiException exception = new ApiException(e.getMessage(),HttpStatus.BAD_REQUEST, ZonedDateTime.now());
            return new ResponseEntity<>(exception,HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable("id") long id, @RequestBody Product product) throws ValidationException {
        try {
               var updatedProduct = productService.update(id, product);
                return new ResponseEntity<>(updatedProduct, HttpStatus.OK);

        } catch (Exception e) {
            ApiException exception = new ApiException(e.getMessage(), HttpStatus.NOT_FOUND, ZonedDateTime.now());
            return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable("id") long id) {
        try {
            productService.delete(id);
            ProductDto productDto = new ProductDto();
            productDto.setId(id);
            return new ResponseEntity<>(productDto, HttpStatus.OK);
        } catch (Exception e) {
            ApiException exception = new ApiException(e.getMessage(),HttpStatus.BAD_REQUEST, ZonedDateTime.now());
            return new ResponseEntity<>(exception,HttpStatus.BAD_REQUEST);
        }
    }


}

package com.adiomiDev.store.controller;


import com.adiomiDev.store.dto.ProductDto;
import com.adiomiDev.store.entity.Product;
import com.adiomiDev.store.exception.CustomControllerAdvice;
import com.adiomiDev.store.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> getAllProducts() {
        try {
            List<Product> products = productService.findAll();
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (ValidationException e) {
            return new CustomControllerAdvice().handleProductNotFoundException(e);
        }
        catch (Exception e) {
            return new CustomControllerAdvice().handleExceptions(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") long id) {
        try {
            return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
        } catch (ValidationException e) {
            return new CustomControllerAdvice().handleProductNotFoundException(e);
        } catch (Exception e) {
            return new CustomControllerAdvice().handleExceptions(e);
        }
    }


    @GetMapping("/sortAsc/{field}")
    public ResponseEntity<?> getProductWithSort(@PathVariable String field) {
        try {
            List<Product> products = productService.sortProductAsc(field);
            return ResponseEntity.status(HttpStatus.OK).body(products);
        }catch (ValidationException e){
            return new CustomControllerAdvice().handleProductNotFoundException(e);
        } catch (Exception e) {
            return new CustomControllerAdvice().handleExceptions(e);
        }

    }

    @GetMapping("/sortDesc/{field}")
    public ResponseEntity<?> getProductWithSortDesc(@PathVariable String field) {
        try {
            List<Product> products = productService.sortProductDesc(field);
            return ResponseEntity.status(HttpStatus.OK).body(products);
        }catch(ValidationException e){
            return  new CustomControllerAdvice().handleProductNotFoundException(e);
        } catch (Exception e) {
            return new CustomControllerAdvice().handleExceptions(e);
        }

    }

    @GetMapping("/pagination/{offSet}/{pageSize}")
    public ResponseEntity<?> getProductWithPagination(@PathVariable int offSet, @PathVariable int pageSize) {
        try {
            Page<Product> products = productService.sortProductPagination(offSet, pageSize);
            return ResponseEntity.status(HttpStatus.OK).body(products);
        }catch(ValidationException e){
            return new CustomControllerAdvice().handleProductNotFoundException(e);
        } catch (Exception e) {
            return new CustomControllerAdvice().handleExceptions(e);
        }

    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        try {
            var addProduct = productService.create(product);
            ProductDto productDto = new ProductDto();
            productDto.setId(productDto.getId());
            return new ResponseEntity<>(addProduct, HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new CustomControllerAdvice().handleProductConstraintException(e);
        } catch (Exception e) {
            return new CustomControllerAdvice().handleExceptions(e);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") long id, @RequestBody Product product) {
        try {
            var updatedProduct = productService.update(id, product);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);

        } catch (ValidationException e) {
            return new CustomControllerAdvice().handleProductNotFoundException(e);
        } catch (Exception e) {
            return new CustomControllerAdvice().handleExceptions(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") long id) {
        try {
            productService.delete(id);
            ProductDto productDto = new ProductDto();
            productDto.setId(id);
            return new ResponseEntity<>(productDto, HttpStatus.OK);
        } catch (ValidationException e) {
            return new CustomControllerAdvice().handleProductNotFoundException(e);
        } catch (Exception e) {
            return new CustomControllerAdvice().handleExceptions(e);
        }
    }


}

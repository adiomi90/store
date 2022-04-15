package com.adiomiDev.store.controller;


import com.adiomiDev.store.dto.ProductDto;
import com.adiomiDev.store.entity.Product;
import com.adiomiDev.store.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            List<Product> products = productService.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(products);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") long id) {
        try {
            Product product = productService.findById(id).get();
            return ResponseEntity.status(HttpStatus.OK).body(product);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/sortAsc/{field}")
    public ResponseEntity<List<Product>> getProductWithSort(@PathVariable String field) {
        try {
            List<Product> products = productService.sortProductAsc(field);
            return ResponseEntity.status(HttpStatus.OK).body(products);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/sortDesc/{field}")
    public ResponseEntity<List<Product>> getProductWithSortDesc(@PathVariable String field) {
        try {
            List<Product> products = productService.sortProductDesc(field);
            return ResponseEntity.status(HttpStatus.OK).body(products);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/pagination/{offSet}/{pageSize}")
    public ResponseEntity<Page<Product>> getProductWithPagination(@PathVariable int offSet, @PathVariable int pageSize) {
        try {
            Page<Product> products = productService.sortProductPagination(offSet, pageSize);
            return ResponseEntity.status(HttpStatus.OK).body(products);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping
    public ResponseEntity<? extends Object> addProduct(@RequestBody Product product) {
        try {
            var addProduct = productService.save(product);
            ProductDto productDto = new ProductDto();
            productDto.setId(productDto.getId());
            return new ResponseEntity<>(addProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") long id, @RequestBody Product product) {
        try {
            var updatedProduct = productService.update(id, product);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable("id") long id) {
        try {
            productService.delete(id);
            ProductDto productDto = new ProductDto();
            productDto.setId(id);
            return new ResponseEntity<>(productDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}

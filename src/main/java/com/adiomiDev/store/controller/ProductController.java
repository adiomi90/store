package com.adiomiDev.store.controller;


import com.adiomiDev.store.dto.ProductDto;
import com.adiomiDev.store.entity.Product;
import com.adiomiDev.store.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;
    private HttpHeaders header = new HttpHeaders();

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            List<Product> products = productService.findAll();
            header.add("desc", "Store Application");
            if (products.isEmpty()) {
                return new ResponseEntity<>(null, header, HttpStatus.BAD_REQUEST);
            }
            return ResponseEntity.status(HttpStatus.OK).headers(header).body(products);

        } catch (Exception e) {
            return new ResponseEntity<>(null, header, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") long id) {
        try {
            Optional<Product> product = productService.findById(id);
            header.add("desc", "Store Application");
            if (product.isEmpty()) {
                return new ResponseEntity<>(null, header, HttpStatus.BAD_REQUEST);
            }
            return ResponseEntity.status(HttpStatus.OK).headers(header).body(product.get());
        } catch (Exception e) {
            return new ResponseEntity<>(null, header, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/sortAsc/{field}")
    public ResponseEntity<List<Product>> getProductWithSort(@PathVariable String field) {
        try {
            List<Product> products = productService.findProductWithSort(field);
            header.add("desc", "Store Application");
            if (products.isEmpty()) {
                return new ResponseEntity<>(null, header, HttpStatus.BAD_REQUEST);
            }
            return ResponseEntity.status(HttpStatus.OK).headers(header).body(products);

        } catch (Exception e) {
            return new ResponseEntity<>(null, header, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/sortDesc/{field}")
    public ResponseEntity<List<Product>> getProductWithSortDesc(@PathVariable String field) {
        try {
            List<Product> products = productService.findProductWithSortDesc(field);
            header.add("desc", "Store Application");
            if (products.isEmpty()) {
                return new ResponseEntity<>(null, header, HttpStatus.BAD_REQUEST);
            }
            return ResponseEntity.status(HttpStatus.OK).headers(header).body(products);

        } catch (Exception e) {
            return new ResponseEntity<>(null, header, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/pagination/{offSet}/{pageSize}")
    public ResponseEntity<Page<Product>> getProductWithPagination(@PathVariable int offSet, @PathVariable int pageSize) {
        try {
            Page<Product> products = productService.findProductWithPagination(offSet, pageSize);
            header.add("desc", "Store Application");
            if (products.isEmpty()) {
                return new ResponseEntity<>(null, header, HttpStatus.BAD_REQUEST);
            }
            return ResponseEntity.status(HttpStatus.OK).headers(header).body(products);

        } catch (Exception e) {
            return new ResponseEntity<>(null, header, HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestBody Product product) {
        try {
            ProductDto productSaved = productService.save(product);
            header.add("desc", "Store Application");
            if (productSaved.getProductId() > 0) {
                ProductDto addProduct = productService.save(product);
                return new ResponseEntity<>(addProduct, header, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(null, header, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, header, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") long id, @RequestBody Product product) {
        try {
            var updatedProduct = productService.update(id, product);
            header.add("desc", "Store Application");
            if (updatedProduct.isEmpty()) {
                return new ResponseEntity<>(null, header, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(updatedProduct.get(), header, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable("id") long id) {
        try {
            header.add("desc", "Store Application");
            ProductDto deleteProduct = productService.delete(id);
            return new ResponseEntity<>(deleteProduct, header, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, header, HttpStatus.BAD_REQUEST);
        }
    }


}

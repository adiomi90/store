package com.adiomiDev.store.services;

import com.adiomiDev.store.dao.ProductDao;
import com.adiomiDev.store.dto.CreateProductDto;
import com.adiomiDev.store.dto.ProductDto;
import com.adiomiDev.store.entity.Product;
import com.adiomiDev.store.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductDao productDao;


    @Autowired
    public ProductService(ProductDao productDao) {
        this.productDao = productDao;

    }


    public List<Product> findAll() throws NotFoundException {
        List<Product> products = (List<Product>) productDao.findAll();
        if (products.isEmpty()) {
            throw new NotFoundException("The products list is empty");
        }

        return products;
    }


    public Product findById(long id) throws NotFoundException {
        Optional<Product> product = productDao.findById(id);
        if (product.isEmpty()) {
            throw new NotFoundException("product with id " + id + " does not exist");
        }

        return product.get();
    }


    public ProductDto create(CreateProductDto product) throws ValidationException {
        ProductDto productDto = new ProductDto();
        if (product.getPrice() <= 0.99)
            throw new ValidationException("price must be more than 0.99");
        if (product.getQuantity() < 0.0)
            throw new ValidationException("quantity must be a positive number");
        if (product.getName().isEmpty() || product.getName().isBlank())
            throw new ValidationException("product name cannot be empty");

        Product newProduct = new Product();
        newProduct.setName(product.getName());
        newProduct.setPrice(product.getPrice());
        newProduct.setQuantity(product.getQuantity());

        productDao.save(newProduct);
        productDto.setId(newProduct.getId());


        return productDto;
    }


    public ProductDto update(long id, CreateProductDto product) throws ValidationException {
        ProductDto productDto = new ProductDto();
        Optional<Product> productUpdate = productDao.findById(id);
        if (productUpdate.isEmpty()) {
            throw new NotFoundException("product with " + id + " does not exist");
        }

        productUpdate.get().setName(product.getName());
        productUpdate.get().setQuantity(product.getQuantity());
        productUpdate.get().setPrice(product.getPrice());

        if (productUpdate.get().getQuantity() < 0.0) {
            throw new ValidationException("quantity must be a positive number");
        } else if (productUpdate.get().getPrice() <= 0.99) {
            throw new ValidationException("price must be greater than 0.99");
        } else {
            productDao.save(productUpdate.get());
            productDto.setId(productUpdate.get().getId());

        }
        return productDto;
    }


    public ProductDto delete(long id) throws ValidationException {
        ProductDto productDto = new ProductDto();
        Optional<Product> product = productDao.findById(id);
        if (product.isEmpty()) {
            throw new ValidationException("product with id " + id + " does not exist");
        }
        productDto.setId(productDao.findById(id).get().getId());
        productDao.deleteById(id);
        return productDto;
    }


    public List<Product> sortProductAsc(String field) throws NotFoundException {
        List<Product> products = (List<Product>) productDao.findAll(Sort.by(Sort.Direction.ASC, field));
        if (products.isEmpty()) {
            throw new NotFoundException("The product list is empty");
        }
        return products;
    }


    public List<Product> sortProductDesc(String field) throws NotFoundException {
        List<Product> products = (List<Product>) productDao.findAll(Sort.by(Sort.Direction.DESC, field));
        if (products.isEmpty()) {
            throw new NotFoundException("The product list is empty");
        }
        return products;
    }


    public Page<Product> sortProductPagination(int offset, int pageSize) throws NotFoundException {
        Page<Product> products = productDao.findAll(PageRequest.of(offset, pageSize));
        if (products.isEmpty()) {
            throw new NotFoundException("The product list is empty");
        }
        return products;
    }
}

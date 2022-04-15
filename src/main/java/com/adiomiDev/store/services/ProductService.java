package com.adiomiDev.store.services;

import com.adiomiDev.store.dao.ProductDao;
import com.adiomiDev.store.dto.ProductDto;
import com.adiomiDev.store.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductDao productDao;


    @Autowired
    public ProductService(ProductDao productDao) {
        this.productDao = productDao;

    }


    public List<Product> findAll() {
        return (List<Product>) productDao.findAll();
    }


    public Optional<Product> findById(long id) {
        return productDao.findById(id);
    }


    public ProductDto save(Product product) {
        ProductDto productDto = new ProductDto();
        if (product.getPrice() <= 0)
            throw new IllegalStateException("price must be more than 0");
        else if (product.getQuantity() <= 0)
            throw new IllegalStateException("quantity must be more than 0");
        else {
            Product save = productDao.save(product);
            productDto.setId(save.getId());
        }

        return productDto;
    }


    public ProductDto update(long id, Product product) {
        ProductDto productDto = new ProductDto();
        Product productUpdate = productDao.findById(id).get();

        productUpdate.setName(product.getName());
        productUpdate.setQuantity(product.getQuantity());
        productUpdate.setPrice(product.getPrice());

        if (productUpdate.getQuantity() <= 0) {
            throw new IllegalStateException("quantity must be more than 0");
        } else if (productUpdate.getPrice() <= 0) {
            throw new IllegalStateException("price must be more than 0");
        } else {
            productDao.save(productUpdate);
            productDto.setId(productUpdate.getId());

        }
        return productDto;
    }


    public ProductDto delete(long id) {
        ProductDto productDto = new ProductDto();
        productDto.setId(productDao.findById(id).get().getId());
        productDao.deleteById(id);
        return productDto;
    }


    public List<Product> sortProductAsc(String field) {
        return (List<Product>) productDao.findAll(Sort.by(Sort.Direction.ASC, field));
    }


    public List<Product> sortProductDesc(String field) {
        return (List<Product>) productDao.findAll(Sort.by(Sort.Direction.DESC, field));
    }


    public Page<Product> sortProductPagination(int offset, int pageSize) {
        Page<Product> products = productDao.findAll(PageRequest.of(offset, pageSize));
        return products;
    }
}

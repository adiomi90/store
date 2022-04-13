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
public class ProductService implements IProduct {

    private ProductDao productDAO;
    private ProductDto productDto;

    @Autowired
    public ProductService(ProductDao productDAO, ProductDto productDto) {
        this.productDAO = productDAO;
        this.productDto = productDto;
    }

    @Override
    public List<Product> findAll() {
        return (List<Product>) productDAO.findAll();
    }

    @Override
    public Optional<Product> findById(long id) {
        return productDAO.findById(id);
    }

    @Override
    public ProductDto save(Product product) {
        if (product.getPrice() < 0)
            return null;
        else if (product.getQuantity() < 0)
            return null;
        else {
            Product save = productDAO.save(product);
            productDto.setProductId(save.getProductId());
        }

        return productDto;
    }

    @Override
    public Optional<ProductDto> update(long id, Product product) {
        Product updateProduct = productDAO.findById(id).get();
        updateProduct.setProductName(product.getProductName());
        updateProduct.setQuantity(product.getQuantity());
        updateProduct.setPrice(product.getPrice());

        if (updateProduct.getQuantity() < 0) {
            return null;
        } else if (updateProduct.getPrice() < 0) {
            return null;
        } else {
            productDAO.save(updateProduct);
            productDto.setProductId(updateProduct.getProductId());

        }


        return Optional.ofNullable(productDto);
    }

    @Override
    public ProductDto delete(long id) {
        productDto.setProductId(productDAO.findById(id).get().getProductId());
        productDAO.deleteById(id);
        return productDto;
    }

    @Override
    public List<Product> findProductWithSort(String field) {
        return (List<Product>) productDAO.findAll(Sort.by(Sort.Direction.ASC, field));
    }

    @Override
    public List<Product> findProductWithSortDesc(String field) {
        return (List<Product>) productDAO.findAll(Sort.by(Sort.Direction.DESC, field));
    }

    @Override
    public Page<Product> findProductWithPagination(int offset, int pageSize) {
        Page<Product> products = productDAO.findAll(PageRequest.of(offset, pageSize));
        return products;
    }
}

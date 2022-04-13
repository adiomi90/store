package com.adiomiDev.store.services;

import com.adiomiDev.store.dto.ProductDto;
import com.adiomiDev.store.entity.Product;
import org.springframework.data.domain.Page;



import java.util.List;
import java.util.Optional;

public interface IProduct {
    List<Product> findAll();

    Optional<Product> findById(long id);

    ProductDto save(Product product);

    Optional<ProductDto> update(long id, Product product);

    ProductDto delete(long id);

    List<Product> findProductWithSort(String field);

    List<Product> findProductWithSortDesc(String field);

    Page<Product> findProductWithPagination(int offset, int pageSize);
}

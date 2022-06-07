package com.adiomiDev.store.dao;

import com.adiomiDev.store.dto.CreateProductDto;
import com.adiomiDev.store.entity.Product;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

}

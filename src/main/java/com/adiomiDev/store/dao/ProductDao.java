package com.adiomiDev.store.dao;

import com.adiomiDev.store.entity.Product;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface ProductDao extends PagingAndSortingRepository<Product, Long> {

}

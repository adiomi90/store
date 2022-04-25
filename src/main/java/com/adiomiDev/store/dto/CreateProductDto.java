package com.adiomiDev.store.dto;


import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class CreateProductDto {
    private String name;
    private int quantity;
    private double price;
}
